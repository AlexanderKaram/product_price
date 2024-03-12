package com.example.product_price.controller;

import com.example.product_price.model.dto.PriceDto;
import com.example.product_price.service.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping()
public class TestController {

    private PriceService service;

    @PatchMapping("/test/v0/newPrice")
    public ResponseEntity<String> updatePrice(@RequestBody List<PriceDto> body) {
        service.update(body);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    @PostMapping("/test/v0/newPrice")
    public ResponseEntity<String> savePrice(@RequestBody List<PriceDto> body) {
        service.save(body);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}

