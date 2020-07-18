package com.mateusz.jakuszko.roomforyoufront.encoder;

import com.vaadin.flow.templatemodel.ModelEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class LocalDateStringEncoder implements ModelEncoder<LocalDate, String> {
    @Override
    public String encode(LocalDate date) {
        return Optional.ofNullable(date).map(Object::toString)
                .orElse(null);
//        return date.getYear() + "-" + date.getMonth() + "-" + date.getDayOfMonth();
    }

    @Override
    public LocalDate decode(String dateString) {
        String[] splitDate = dateString.split("-");
        return LocalDate.of(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]));
    }
}
