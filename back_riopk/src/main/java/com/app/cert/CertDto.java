package com.app.cert;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CertDto(
        Long id,

        @Size(min = 1, max = 255, message = "name is required length 1-255")
        @NotEmpty(message = "name is required")
        String name,
        @Size(min = 1, max = 255, message = "address is required length 1-255")
        @NotEmpty(message = "address is required")
        String address,
        @Min(value = 0, message = "price is required min 0")
        @Max(value = 1000000, message = "price is required max 1000000")
        float price,
        @Min(value = 0, message = "term is required min 0")
        @Max(value = 100, message = "term is required max 100")
        int term,
        int views,

        @Size(min = 1, max = 5000, message = "description is required length 1-5000")
        @NotEmpty(message = "description is required")
        String description,

        String img,
        String file,

        String reason,
        String reasonName,

        Long categoryId,
        String categoryName,

        Long ownerId,

        int orderingsSize,

        float ctr
) {
}
