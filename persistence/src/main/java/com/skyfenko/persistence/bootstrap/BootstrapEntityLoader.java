package com.skyfenko.persistence.bootstrap;

import com.skyfenko.core.domain.impl.Stock;
import com.skyfenko.persistence.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Generate {@link BootstrapEntityLoader#STOCKS_TO_GENERATE} stocks and insert to DB
 *
 * @author Stanislav Kyfenko
 */
@Component
@Slf4j
public class BootstrapEntityLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final int STOCKS_TO_GENERATE = 100;

    private final StockRepository stockRepository;

    @Autowired
    public BootstrapEntityLoader(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadStocks();
    }

    private void loadStocks() {
        log.debug("generate and load {} stocks to in-memory DB", STOCKS_TO_GENERATE);

        List<Stock> stocksToLoad = IntStream.range(0, STOCKS_TO_GENERATE).mapToObj(this::createStock).collect(Collectors.toList());

        stockRepository.save(stocksToLoad);
        log.debug("{} stocks were successfully loaded into DB", STOCKS_TO_GENERATE);
    }

    private Stock createStock(int value) {
        return Stock.builder()
                .name("stockName" + value)
                .currentPrice(ThreadLocalRandom.current().nextDouble(1000))
                .build();
    }

}



