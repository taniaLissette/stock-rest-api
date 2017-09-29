package com.skyfenko.persistence.repository;

import com.skyfenko.core.domain.impl.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
}
