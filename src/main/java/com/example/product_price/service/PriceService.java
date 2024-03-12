package com.example.product_price.service;

import com.example.product_price.model.dto.PriceDto;
import com.example.product_price.model.entity.Price;
import com.example.product_price.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Работает с информацией о ценах
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PriceService {


    private final PriceRepository repository;


    /**
     * Обновление цен
     */
    public void update(List<PriceDto> newPriceList) {
        var newPrice = newPriceList.stream().map(Price::priceFromDto).collect(Collectors.toList());
        var oldPrice = repository.findAllByMetaDataProductCodeIn(newPriceList.stream()
                .map(PriceDto::getProductCode)
                .collect(Collectors.toSet()));
        repository.saveAll(mergePriceList(newPrice, oldPrice));
    }

    /**
     * Загрузка цен в БД (чтобы не вручную insert делать).
     */

    public void save(List<PriceDto> priceList) {
        repository.saveAll(priceList.stream().map(Price::priceFromDto).collect(Collectors.toList()));
    }

    /**
     * Целевой метод.
     * <p>
     * Правила объединения цен:
     * </p>
     * <p>
     * −	если товар еще не имеет цен, или имеющиеся цены не пересекаются в периодах действия с новыми, то
     * новые цены просто добавляются к товару;
     * </p>
     * <p>
     * −	если имеющаяся цена пересекается в периоде действия с новой ценой, то:
     * </p>
     * <p>
     * ●	если значения цен одинаковы, период действия имеющейся цены увеличивается
     * согласно периоду новой цены;
     * </p>
     * ●	если значения цен отличаются, добавляется новая цена,
     * а период действия старой цены уменьшается согласно периоду новой цены.
     */

    public List<Price> mergePriceList(List<Price> newPrices, List<Price> existingPrices) {
        // Создаем новый список для объединенных цен, начинаем с имеющихся цен
        List<Price> mergedPrices = new ArrayList<>(existingPrices);
        // Обходим все новые цены
        for (Price newPrice : newPrices) {
            boolean priceMerged = false;
            // Проверяем каждую новую цену с имеющимися
            for (Price existingPrice : existingPrices) {
                // Если номер и отдел цены совпадают
                if (newPrice.getMetaData().equals(existingPrice.getMetaData())) {
                    // Проверяем пересечение периодов действия цен
                    if (newPrice.getBeginDate().isAfter(existingPrice.getBeginDate())
                        && newPrice.getEndDate().isAfter(existingPrice.getEndDate())) {
                        // Если значения цен одинаковы, увеличиваем период действия существующей цены
                        if (newPrice.getValue() == existingPrice.getValue()) {
                            existingPrice.setEndDate(newPrice.getEndDate());
                        }
                    }

                    if (newPrice.getValue() != existingPrice.getValue()) {
                        // Если значения цен отличаются, добавляем новую цену и корректируем период действия существующей цены
                        if (newPrice.getEndDate().isAfter(existingPrice.getEndDate()) &&
                            newPrice.getBeginDate().isBefore(existingPrice.getEndDate())) {
                            Price newPriceCopy = new Price(newPrice.getId(), existingPrice.getMetaData(),
                                    newPrice.getValue(), newPrice.getBeginDate(),
                                    newPrice.getEndDate());
                            existingPrice.setEndDate(newPrice.getBeginDate().minusSeconds(1));
                            mergedPrices.add(newPriceCopy);

                        } else if (newPrice.getBeginDate().isAfter(existingPrice.getBeginDate()) &&
                                   newPrice.getEndDate().isBefore(existingPrice.getEndDate())) {
                            Price newPriceCopy = new Price(newPrice.getId(), existingPrice.getMetaData(),
                                    existingPrice.getValue(), newPrice.getEndDate().plusSeconds(1),
                                    existingPrice.getEndDate());
                            existingPrice.setEndDate(newPrice.getBeginDate().minusSeconds(1));
                            mergedPrices.add(newPrice);
                            mergedPrices.add(newPriceCopy);
                        }
                    }
                }
                priceMerged = true;
            }
            // Если цена не была объединена, добавляем ее в список объединенных цен
            if (!priceMerged) {
                mergedPrices.add(newPrice);
            }
        }
        return mergedPrices;
    }
}

