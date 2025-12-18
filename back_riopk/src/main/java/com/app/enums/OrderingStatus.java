package com.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderingStatus {
    NOT_USED("Не использован"),
    USED("Использован"),
    ;

    private final String name;
}

