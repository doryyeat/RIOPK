package com.app.ordering.converter;

import com.app.ordering.Ordering;
import com.app.ordering.OrderingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderingToOrderingDtoConverter implements Converter<Ordering, OrderingDto> {
    @Override
    public OrderingDto convert(Ordering source) {
        return new OrderingDto(
                source.getId(),

                source.getDateStart(),
                source.getDateStartFormat(),

                source.getDateEnd(),
                source.getDateEndFormat(),

                source.getTerm(),
                source.getCode(),

                source.getType().name(),
                source.getType().getName(),

                source.getStatus().name(),
                source.getStatus().getName(),

                source.getCert().getId(),
                source.getCert().getName(),

                source.getOwner().getId(),
                source.getOwner().getUsername(),

                source.isActivate()
        );
    }
}
