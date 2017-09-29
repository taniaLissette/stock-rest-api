package com.skyfenko.service.conversion;

import com.skyfenko.core.domain.Concept;
import com.skyfenko.service.dto.ConceptDTO;

/**
 * Service to convert dto and domain classes and vice versa
 *
 * @author Stanislav Kyfenko
 */
public interface ConversionService<DOMAIN extends Concept, DTO extends ConceptDTO> {

    DOMAIN convert(DTO dto, Class<DOMAIN> domainClass);

    DTO convert(DOMAIN domain, Class<DTO> dtoClass);
}
