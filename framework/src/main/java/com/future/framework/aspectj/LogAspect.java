package com.future.framework.aspectj;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.future.common.annotation.Log;
import com.future.common.constant.enums.OperatingState;
import com.future.common.core.domain.model.LoginUser;
import com.future.common.utils.IpUtils;
import com.future.common.utils.JsonUtils;
import com.future.common.utils.SecurityUtils;
import com.future.common.utils.StringUtils;
import com.future.common.utils.spring.SpringUtils;
import com.future.system.domain.entity.OperationLog;
import com.future.system.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author JonZhang
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 排除敏感属性字段
     */
    private static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};
    private static final ObjectWriter OBJECT_WRITER = createObjectWriter();

    @Resource(type = OperationLogService.class)
    private OperationLogService operationLogService;

    private static ObjectWriter createObjectWriter() {
        SimpleFilterProvider filterProvider =
            new SimpleFilterProvider()
                .addFilter("except", SimpleBeanPropertyFilter.serializeAllExcept(EXCLUDE_PROPERTIES));
        return JsonUtils.getObjectMapper().writer(filterProvider);
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(alog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log alog, Object jsonResult) {
        handleLog(joinPoint, alog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(alog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log alog, Exception e) {
        handleLog(joinPoint, alog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log alog, final Exception e, Object jsonResult) {
        try {
            LoginUser loginUser = SecurityUtils.getCurrentUser();
            OperationLog operationLog = new OperationLog();
            operationLog.setState(OperatingState.SUCCESS);
            HttpServletRequest request = SpringUtils.getRequest();
            operationLog.setIp(IpUtils.getIpAddr(request));
            operationLog.setUrl(request.getRequestURI());
            if (loginUser != null) {
                operationLog.setUserId(loginUser.getUserId());
            }
            if (e != null) {
                operationLog.setState(OperatingState.ERROR);
                operationLog.setErrorMessage(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operationLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operationLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, alog, operationLog, request, jsonResult);
            operationLogService.asyncAddOperationLog(operationLog);
        } catch (Exception ex) {
            log.error("operation log aspect error ", ex);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, Log log, OperationLog operationLog,
                                                HttpServletRequest request,
                                                Object jsonResult) {
        operationLog.setBusinessType(log.businessType());
        operationLog.setTitle(log.title());
        operationLog.setOperatorType(log.operatorType());
        if (log.saveRequestData()) {
            operationLog.setQueryParam(getRequestParams(request, joinPoint));
        }
        if (log.saveResponseData()) {
            operationLog.setResponse(StringUtils.substring(JsonUtils.toJsonString(jsonResult), 0, 2000));
        }
    }

    private String getRequestParams(HttpServletRequest request, JoinPoint joinPoint) {
        String requestMethod = request.getMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            return argsArrayToString(joinPoint.getArgs());
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) SpringUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            return StringUtils.substring(paramsMap.toString(), 0, 2000);
        }
    }

    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (o != null && !isFilterObject(o)) {
                    try {
                        String jsonObj = OBJECT_WRITER.writeValueAsString(o);
                        params.append(jsonObj).append(" ");
                    } catch (Exception ignore) {
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
            || o instanceof BindingResult;
    }

}
