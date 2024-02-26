package com.haredev.library.infrastructure.mapper;

import com.haredev.library.user.domain.api.Authority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class AuthoritiesToStringConverter implements AttributeConverter<Set<Authority>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Authority> objects) {
        return objects.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Authority> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(","))
                .map(Authority::valueOf)
                .collect(Collectors.toSet());
    }

}