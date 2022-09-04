package com.future.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.jetbrains.annotations.NotNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * 用户状态枚举
 *
 * @author JonZhang
 */
public enum UserState {

    VALID(1),// 有效的
    LOCKED(0),// 锁定的
    DELETED(-1),// 无效的
    ;

    private final Integer value;

    UserState(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    @Converter(autoApply = true)
    public static class UserStateConverter implements AttributeConverter<UserState, Integer> {

        @Override
        public Integer convertToDatabaseColumn(UserState attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public UserState convertToEntityAttribute(Integer dbData) {
            if (dbData == null) {
                return null;
            }
            return Stream.of(UserState.values())
                .filter(s -> s.getValue().equals(dbData))
                .findFirst()
                .orElse(LOCKED);
        }
    }

    public static class UserStateQueryConverter
        implements org.springframework.core.convert.converter.Converter<Integer, UserState> {

        @Override
        public UserState convert(@NotNull Integer source) {
            return Stream.of(UserState.values())
                .filter(i -> i.getValue().equals(source))
                .findFirst()
                .orElse(null);
        }
    }

}
