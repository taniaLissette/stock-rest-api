package com.skyfenko.service.conversion;

import com.skyfenko.core.domain.impl.Stock;
import com.skyfenko.service.config.ServiceConfig;
import com.skyfenko.service.dto.impl.StockDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * ConversionServiceTest
 *
 * @author Stanislav Kyfenko
 * since 9/29/17.
 */
@ContextConfiguration(classes = ServiceConfig.class)
@RunWith(SpringRunner.class)
public class ConversionServiceTest {

    @Autowired
    private ConversionService<Stock, StockDTO> conversionService;

    @Test
    public void convertNull() throws Exception {
        Assert.assertNull(conversionService.convert(null, StockDTO.class));
    }

    @Test
    public void convertFromDomain() throws Exception {
        Stock stock = new Stock();

        String name = "123";
        double currentPrice = 12.34d;
        long lastUpdate = System.currentTimeMillis();

        stock.setName(name);
        stock.setCurrentPrice(currentPrice);
        stock.setLastUpdate(lastUpdate);

        StockDTO converted = conversionService.convert(stock, StockDTO.class);

        Assert.assertEquals(name, converted.getName());
        Assert.assertEquals(currentPrice, converted.getCurrentPrice(), 0d);
        Assert.assertEquals(lastUpdate, converted.getLastUpdate(), 0d);
    }

    @Test
    public void convertFromDTO() throws Exception {
        StockDTO stock = new StockDTO();

        String name = "123";
        double currentPrice = 12.34d;
        long lastUpdate = System.currentTimeMillis();

        stock.setName(name);
        stock.setCurrentPrice(currentPrice);
        stock.setLastUpdate(lastUpdate);

        Stock converted = conversionService.convert(stock, Stock.class);

        Assert.assertEquals(name, converted.getName());
        Assert.assertEquals(currentPrice, converted.getCurrentPrice(), 0d);
        Assert.assertEquals(lastUpdate, converted.getLastUpdate(), 0d);
    }

}