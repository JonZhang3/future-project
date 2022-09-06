package com.future.framework.config;

import com.future.common.constant.enums.State;
import com.future.common.constant.enums.UserState;
import com.future.framework.jpa.BaseJpaRepositoryFactoryBean;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.TimeZone;

/**
 * 程序配置
 *
 * @author JonZhang
 */
@Configuration
@EnableAsync
@EnableJpaAuditing // 开启审计功能
@EnableJpaRepositories(basePackages = "com.future", repositoryFactoryBeanClass = BaseJpaRepositoryFactoryBean.class)
@EnableAspectJAutoProxy(exposeProxy = true) // 表示通过aop框架暴露该代理对象,AopContext能够访问
public class ApplicationConfig implements WebMvcConfigurer {

    /**
     * 时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }

    @Resource(type = FrameworkConfig.class)
    private FrameworkConfig frameworkConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /* swagger配置 */
        registry.addResourceHandler("/swagger-ui/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }

    /**
     * 在这里添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    /**
     * 跨域的相关配置
     */
    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        if (frameworkConfig.isCors()) {
            registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*");
        }
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new State.StateQueryConverter());
        registry.addConverter(new UserState.UserStateQueryConverter());
    }
}
