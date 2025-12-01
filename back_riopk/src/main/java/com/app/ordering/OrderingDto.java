package com.app.ordering;

public record OrderingDto(
        Long id,

        String dateStart,
        String dateStartFormat,

        String dateEnd,
        String dateEndFormat,

        int term,
        String code,

        String type,
        String typeName,

        String status,
        String statusName,

        Long certId,
        String certName,

        Long ownerId,
        String ownerUsername,

        boolean activate
) {
}
