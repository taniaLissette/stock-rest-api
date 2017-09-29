package com.skyfenko.service.stock.impl;

import com.skyfenko.core.domain.impl.Stock;
import com.skyfenko.core.exception.impl.InvalidStockException;
import com.skyfenko.persistence.repository.StockRepository;
import com.skyfenko.service.conversion.ConversionService;
import com.skyfenko.service.dto.impl.StockDTO;
import com.skyfenko.service.stock.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    private final ConversionService<Stock, StockDTO> conversionService;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository, ConversionService<Stock, StockDTO> conversionService) {
        this.stockRepository = stockRepository;
        this.conversionService = conversionService;
    }

    @Override
    public List<StockDTO> findAll() {
        log.debug("try to find all stocks");
        Iterable<Stock> stocks = stockRepository.findAll();

        List<StockDTO> stockDTOS = StreamSupport.stream(stocks.spliterator(), false)
                .map(stock -> conversionService.convert(stock, StockDTO.class))
                .collect(Collectors.toList());

        log.debug("{} stocks were found", stockDTOS.size());

        return stockDTOS;
    }

    @Override
    public StockDTO findById(Long id) {
        if (Objects.isNull(id)) {
            throw new InvalidStockException("Id is null");
        }
        Stock stock = stockRepository.findOne(id);
        return conversionService.convert(stock, StockDTO.class);
    }

    @Override
    public StockDTO save(StockDTO stockDTO) {
        if (Objects.isNull(stockDTO)) {
            throw new InvalidStockException("Stock is null and cannot be saved");
        }

        log.debug("try to save stock {}", stockDTO);

        Stock stock = conversionService.convert(stockDTO, Stock.class);
        Stock savedStock = stockRepository.save(stock);
        log.debug("stock was successfully saved {}", savedStock);
        return conversionService.convert(savedStock, StockDTO.class);
    }

    @Override
    public StockDTO update(StockDTO stock) {
        log.debug("try to update stock {}", stock);
        return save(stock);
    }

    @Override
    public void delete(Long id) {
        if (Objects.isNull(id)) {
            throw new InvalidStockException("Id is null");
        }
        log.debug("try to delete stock by id {}", id);
        stockRepository.delete(id);
    }
}
