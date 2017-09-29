package com.skyfenko.core.domain.impl;

import com.skyfenko.core.domain.Concept;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Stock
 *
 * @author Stanislav Kyfenko
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(builderClassName = "Builder")
public class Stock extends Concept {

    @Tolerate
    public Stock() {
    }

    /**
     * Name
     */
    private String name;

    /**
     * Stock price
     */
    private Double currentPrice;

    /**
     * Last modified timestamp
     */
    private Long lastUpdate;

    /**
     * Automatically update lastUpdate with current time millis
     */
    @PreUpdate
    @PrePersist
    public void updateLastUpdateTimestamp() {
        lastUpdate = System.currentTimeMillis();
    }
}
