package com.mateusz.jakuszko.roomforyoufront.encoder;

import com.vaadin.flow.templatemodel.ModelEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LongToStringEncoder implements ModelEncoder<Long, String> {

    @Override
    public String encode(Long modelValue) {
        return Optional.ofNullable(modelValue).map(Object::toString)
                .orElse(null);
    }

    @Override
    public Long decode(String presentationValue) {
        return Optional.ofNullable(presentationValue).map(Long::valueOf)
                .orElse(null);
    }
}
