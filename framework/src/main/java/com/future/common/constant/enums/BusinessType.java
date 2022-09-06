package com.future.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * 业务操作类型
 *
 * @author JonZhang
 */
public enum BusinessType {

    OTHER(0),// 其它
    INSERT(1),// 新增
    UPDATE(2),// 修改
    DELETE(3),// 删除
    GRANT(4),// 授权
    EXPORT(5),// 导出
    IMPORT(6),// 导入
    FORCE(7),// 强退
    GENCODE(8),// 生成代码
    CLEAN(9)// 清空数据
    ;

    @JsonValue
    private final Integer value;

    BusinessType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Converter(autoApply = true)
    public static class BusinessTypeConverter implements AttributeConverter<BusinessType, Integer> {

        @Override
        public Integer convertToDatabaseColumn(BusinessType attribute) {
            return attribute == null ? OTHER.getValue() : attribute.getValue();
        }

        @Override
        public BusinessType convertToEntityAttribute(Integer dbData) {
            return Stream.of(BusinessType.values())
                .filter(i -> i.getValue().equals(dbData))
                .findFirst()
                .orElse(BusinessType.OTHER);
        }
    }

}
