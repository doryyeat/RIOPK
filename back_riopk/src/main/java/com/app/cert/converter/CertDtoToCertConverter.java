package com.app.cert.converter;

import com.app.cert.Cert;
import com.app.cert.CertDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CertDtoToCertConverter implements Converter<CertDto, Cert> {
    @Override
    public Cert convert(CertDto source) {
        return new Cert(
                source.name(),
                source.address(),
                source.price(),
                source.term(),
                source.description()
        );
    }
}
