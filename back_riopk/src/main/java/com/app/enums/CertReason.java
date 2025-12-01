package com.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum CertReason{
    CR1("23 февраля"),
    CR2("8 марта"),
    CR3("14 февраля"),
    CR4("Свадьба"),
    CR5("Новый год"),
    CR6("Юбилей"),
    CR7("Корпоратив"),
    CR8("1 сентября"),
    ;

    private final String name;
}

