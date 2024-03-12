package com.example.product_price.repository;

import com.example.product_price.model.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findAllByMetaDataProductCodeIn(Set<String> codeSet);
}

