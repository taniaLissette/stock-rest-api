package com.skyfenko.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Parent domain
 *
 * @author Stanislav Kyfenko
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
