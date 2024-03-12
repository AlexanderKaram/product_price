package com.example.product_price.model.entity;

import com.example.product_price.model.dto.PriceDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@Entity
@Table(name = "price")
public class Price  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    private PriceMetaData metaData;

    @Column(name = "value")
    private int value;

    @Column(name = "begin_date")
    private LocalDateTime beginDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;


    public static Price priceFromDto(PriceDto src){
        return Price.builder()
                .beginDate(src.getStartDate())
                .endDate(src.getEndDate())
                .value(Integer.parseInt(src.getValue()))
                .metaData(PriceMetaData.builder()
                        .depart(Integer.parseInt(src.getDepart()))
                        .number(Integer.parseInt(src.getNumber()))
                        .productCode(src.getProductCode())
                        .build())
                .build();
    }
}

