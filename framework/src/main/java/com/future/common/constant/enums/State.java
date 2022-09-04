package com.future.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.jetbrains.annotations.NotNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * 状态枚举
 *
 * @author JonZhang
 */
public enum State {

    VALID(1),// 有效的
    INVALID(0),// 无效的/停用
    DELETED(-1);

    private final Integer value;

    State(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    @Converter(autoApply = true)
    public static class StateConverter implements AttributeConverter<State, Integer> {

        @Override
        public Integer convertToDatabaseColumn(State attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public State convertToEntityAttribute(Integer dbData) {
            if (dbData == null) {
                return null;
            }
            return Stream.of(State.values())
                .filter(s -> s.getValue().equals(dbData))
                .findFirst()
                .orElse(INVALID);
        }
    }

    public static class StateQueryConverter implements org.springframework.core.convert.converter.Converter<Integer, State> {

        @Override
        public State convert(@NotNull Integer source) {
            return Stream.of(State.values())
                .filter(s -> s.getValue().equals(source))
                .findFirst()
                .orElse(null);
        }
    }

}
