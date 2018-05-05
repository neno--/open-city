package com.github.nenomm.oc.core;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IndentifierConverter implements Converter<String, EntityIdentifier> {

    @Override
    public EntityIdentifier convert(String source) {
        return EntityIdentifier.fromString(source);
    }
}
