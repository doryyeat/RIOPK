package com.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderingType {
    MYSELF("Для себя"),
    GIFT("В подарок"),
    ;

    private final String name;
}

