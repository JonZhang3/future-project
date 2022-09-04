package com.future.common.constant.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

public enum ResourceType {

    ROUTE(0),// 路由菜单
    MENU(1),// 菜单
    BUTTON(2);// 按钮

    private final Integer value;

    ResourceType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Converter(autoApply = true)
    public static class ResourceTypeConverter implements AttributeConverter<ResourceType, Integer> {

        @Override
        public Integer convertToDatabaseColumn(ResourceType attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public ResourceType convertToEntityAttribute(Integer dbData) {
            if (dbData == null) {
                return null;
            }
            return Stream.of(ResourceType.values())
                .filter(i -> i.getValue().equals(dbData))
                .findFirst()
                .orElse(null);
        }
    }

}
