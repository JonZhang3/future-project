package com.future.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

public enum UserType {

    ADMIN(0),// 管理员
    NORMAL(1)// 普通用户
    ;

    @JsonValue
    private final Integer value;

    UserType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Converter(autoApply = true)
    public static class UserTypeConverter implements AttributeConverter<UserType, Integer> {

        @Override
        public Integer convertToDatabaseColumn(UserType attribute) {
            return attribute == null ? NORMAL.getValue() : attribute.getValue();
        }

        @Override
        public UserType convertToEntityAttribute(Integer dbData) {
            return Stream.of(UserType.values())
                .filter(i -> i.getValue().equals(dbData))
                .findFirst()
                .orElse(NORMAL);
        }
    }

}
