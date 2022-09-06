package com.future.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

public enum OperatingState {

    SUCCESS(1),// 操作成功
    ERROR(0);// 操作失败

    @JsonValue
    private final Integer value;

    OperatingState(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Converter(autoApply = true)
    public static class OperatingStateConverter implements AttributeConverter<OperatingState, Integer> {

        @Override
        public Integer convertToDatabaseColumn(OperatingState attribute) {
            return attribute == null ? SUCCESS.getValue() : attribute.getValue();
        }

        @Override
        public OperatingState convertToEntityAttribute(Integer dbData) {
            return Stream.of(OperatingState.values())
                .filter(i -> i.getValue().equals(dbData))
                .findFirst()
                .orElse(SUCCESS);
        }
    }

}
