package com.skyfenko.service;

import com.skyfenko.service.dto.ConceptDTO;

import java.util.List;

/**
 * Umbrella interface for all the services
 *
 * @param <DTO>
 * @author Stanislav Kyfenko
 */
public interface Service<DTO extends ConceptDTO> {

    List<DTO> findAll();

    DTO findById(Long id);

    DTO save(DTO stock);

    DTO update(DTO stock);

    void delete(Long id);
}
