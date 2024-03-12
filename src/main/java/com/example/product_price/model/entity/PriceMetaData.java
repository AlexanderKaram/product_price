package com.example.product_price.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@Embeddable
public class PriceMetaData {

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "number")
    private int number;

    @Column(name = "depart")
    private int depart;
}

