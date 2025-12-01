package com.app.cert.converter;

import com.app.cert.Cert;
import com.app.cert.CertDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CertToCertDtoConverter implements Converter<Cert, CertDto> {
    @Override
    public CertDto convert(Cert source) {
        return new CertDto(
                source.getId(),

                source.getName(),
                source.getAddress(),
                source.getPrice(),
                source.getTerm(),
                source.getViews(),

                source.getDescription(),

                source.getImg(),

                source.getFile(),

                source.getReason().name(),
                source.getReason().getName(),

                source.getCategory().getId(),
                source.getCategory().getName(),

                source.getOrderingsSize(),

                source.getCtr()
        );
    }
}
