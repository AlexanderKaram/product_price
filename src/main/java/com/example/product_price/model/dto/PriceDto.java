package com.example.product_price.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PriceDto {

    private String productCode;

    private String number;

    private String depart;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String value;

}

