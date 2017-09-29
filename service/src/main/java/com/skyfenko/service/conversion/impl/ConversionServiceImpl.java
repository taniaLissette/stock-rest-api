package com.skyfenko.service.conversion.impl;

import com.skyfenko.core.domain.Concept;
import com.skyfenko.service.conversion.ConversionService;
import com.skyfenko.service.dto.ConceptDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.springframework.util.ReflectionUtils.*;

/**
 * Implementation of {@link ConversionService}
 *
 * @author Stanislav Kyfenko
 */
@Service
@Slf4j
public class ConversionServiceImpl<DOMAIN extends Concept, DTO extends ConceptDTO> implements ConversionService<DOMAIN, DTO> {

    @Override
    @SneakyThrows
    public DOMAIN convert(DTO dto, Class<DOMAIN> domainClass) {
        return convertFromAToB(dto, domainClass);
    }

    @Override
    @SneakyThrows
    public DTO convert(DOMAIN domain, Class<DTO> dtoClass) {
        return convertFromAToB(domain, dtoClass);
    }

    /**
     * Set fields from A to fields of B with the same names
     *
     * @param a source
     * @param bClass destination class
     * @param <B> destination generic
     * @param <A> srouce generic
     * @return destincation
     */
    private <B, A> B convertFromAToB(A a, Class<B> bClass) throws IllegalAccessException, InstantiationException {
        if (a == null) {
            return null;
        }

        B b = bClass.newInstance();

        doWithFields(a.getClass(), aField -> {
            makeAccessible(aField);

            Field bField = findField(bClass, aField.getName());
            makeAccessible(bField);
            setField(bField, b, aField.get(a));

        }, field -> !Modifier.isStatic(field.getModifiers()));

        return b;
    }
}
