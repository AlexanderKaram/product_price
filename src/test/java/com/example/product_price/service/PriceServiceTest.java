package com.example.product_price.service;

import com.example.product_price.model.entity.Price;
import com.example.product_price.model.entity.PriceMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceServiceTest {

    private PriceService priceService;


    @BeforeEach
    void prepare() {
        this.priceService = new PriceService();

    }


    @Test
    public void testUnionOfDate_MergePriceList() {

        List<Price> existingPrices = new ArrayList<>();
        existingPrices.add(new Price(1L, new PriceMetaData("122856", 1, 1),
                11000, LocalDateTime.of(2013, 1, 1, 0, 0), LocalDateTime.of(2013, 1, 31, 23, 59)));

        List<Price> newPrices = new ArrayList<>();
        newPrices.add(new Price(2L, new PriceMetaData("122856", 1, 1),
                11000, LocalDateTime.of(2013, 1, 20, 0, 0), LocalDateTime.of(2013, 2, 20, 23, 59)));

        List<Price> mergedPrices = priceService.mergePriceList(newPrices, existingPrices);

        assertEquals(1, mergedPrices.size());

    }

    @Test
    public void testBreakingOfDate_MergePriceList() {

        List<Price> existingPrices = new ArrayList<>();
        existingPrices.add(new Price(1L, new PriceMetaData("6654", 1, 2), 99000, LocalDateTime.of(2013, 1, 1, 0, 0), LocalDateTime.of(2013, 1, 31, 23, 59)));

        List<Price> newPrices = new ArrayList<>();
        newPrices.add(new Price(2L, new PriceMetaData("6654", 1, 2), 92000, LocalDateTime.of(2013, 1, 12, 0, 0), LocalDateTime.of(2013, 1, 13, 23, 59)));

        List<Price> mergedPrices = priceService.mergePriceList(newPrices, existingPrices);

        assertEquals(3, mergedPrices.size());

    }
}