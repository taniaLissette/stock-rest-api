package com.skyfenko.service.stock;

import com.skyfenko.core.exception.impl.InvalidStockException;
import com.skyfenko.service.config.ServiceConfig;
import com.skyfenko.service.dto.impl.StockDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * StockServiceTest
 *
 * @author Stanislav Kyfenko
 */
@ContextConfiguration(classes = ServiceConfig.class)
@RunWith(SpringRunner.class)
public class StockServiceTest {

    @Autowired
    private StockService service;

    @Before
    public void setUp() throws Exception {
        for (StockDTO stockDTO : service.findAll()) {
            service.delete(stockDTO.getId());
        }
    }

    @Test
    public void findAll() throws Exception {
        for (int i = 0; i < 5; i++) {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setCurrentPrice(11.12d);
            stockDTO.setName("asdasd");

            service.save(stockDTO);
        }

        Assert.assertEquals(5, service.findAll().size());
    }

    @Test
    public void findAllWithNoDataInDB() throws Exception {
        Assert.assertEquals(0, service.findAll().size());
    }

    @Test(expected = InvalidStockException.class)
    public void findByIdNull() throws Exception {
        service.findById(null);
    }

    @Test(expected = InvalidStockException.class)
    public void deleteNull() throws Exception {
        service.delete(null);
    }

    @Test(expected = InvalidStockException.class)
    public void saveNull() throws Exception {
        service.save(null);
    }

    @Test
    public void saveAndFindById() throws Exception {
        StockDTO saved = createStock();

        Assert.assertEquals(saved, service.findById(saved.getId()));
    }

    @Test
    public void update() throws Exception {
        StockDTO stockDTO = createStock();
        stockDTO.setCurrentPrice(10.10d);
        StockDTO updated = service.update(stockDTO);

        Assert.assertEquals(stockDTO.getCurrentPrice(), updated.getCurrentPrice());
    }

    @Test
    public void delete() throws Exception {
        StockDTO stock = createStock();

        service.delete(stock.getId());

        Assert.assertNull(service.findById(stock.getId()));
    }

    private StockDTO createStock() {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setCurrentPrice(11.12d);
        stockDTO.setName("asdasd");

        return service.save(stockDTO);
    }

}