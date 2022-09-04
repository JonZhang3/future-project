package com.future.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

public enum Sex {

    UNKNOW(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private final Integer value;
    private final String name;

    Sex(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Converter(autoApply = true)
    public static class SexConverter implements AttributeConverter<Sex, Integer> {

        @Override
        public Integer convertToDatabaseColumn(Sex attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public Sex convertToEntityAttribute(Integer dbData) {
            if (dbData == null) {
                return null;
            }
            return Stream.of(Sex.values())
                .filter(s -> s.getValue().equals(dbData))
                .findFirst()
                .orElse(null);
        }
    }

}
