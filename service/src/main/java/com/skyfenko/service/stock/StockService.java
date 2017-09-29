package com.skyfenko.service.stock;

import com.skyfenko.service.Service;
import com.skyfenko.service.dto.impl.StockDTO;

import java.util.List;

/**
 * Service to manage CRUD operations of stocks
 *
 * @author Stanislav Kyfenko
 */
public interface StockService extends Service<StockDTO> {

    /**
     * Find all the stacks from DB
     *
     * @return list of stacks
     */
    List<StockDTO> findAll();

    /**
     * Find stock by id
     *
     * @param id stock id
     * @return {@link StockDTO}
     */
    StockDTO findById(Long id);

    /**
     * Save stock and return saved stock
     *
     * @param stock {@link StockDTO}
     * @return saved {@link StockDTO}
     */
    StockDTO save(StockDTO stock);

    /**
     * Update currentPrice of stock in DB
     *
     * @param stock {@link StockDTO}
     * @return updated {@link StockDTO}
     */
    StockDTO update(StockDTO stock);

    /**
     * Delete stock by id
     *
     * @param id stock id
     */
    void delete(Long id);
}
