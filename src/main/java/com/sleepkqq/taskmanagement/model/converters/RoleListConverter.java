package com.sleepkqq.taskmanagement.model.converters;

import com.sleepkqq.taskmanagement.model.enums.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class RoleListConverter implements AttributeConverter<List<Role>, String> {

    private static final String SPLIT_CHAR = ", ";

    @Override
    public String convertToDatabaseColumn(List<Role> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Role::name)
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<Role> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(SPLIT_CHAR))
                .map(Role::valueOf)
                .collect(Collectors.toList());
    }

}