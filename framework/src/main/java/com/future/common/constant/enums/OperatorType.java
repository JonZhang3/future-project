package com.future.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * 操作人类别
 *
 * @author JonZhang
 */
public enum OperatorType {

    OTHER(0),// 其它
    MANAGE(1),// 后台用户
    MOBILE(2)// 手机端用户
    ;

    @JsonValue
    private final Integer value;

    OperatorType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Converter(autoApply = true)
    public static class OperatorTypeConverter implements AttributeConverter<OperatorType, Integer> {

        @Override
        public Integer convertToDatabaseColumn(OperatorType attribute) {
            return attribute == null ? OTHER.getValue() : attribute.getValue();
        }

        @Override
        public OperatorType convertToEntityAttribute(Integer dbData) {
            return Stream.of(OperatorType.values())
                .filter(i -> i.getValue().equals(dbData))
                .findFirst()
                .orElse(OTHER);
        }
    }

}
