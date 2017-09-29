package com.skyfenko.service.dto.impl;

import com.skyfenko.service.dto.ConceptDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class StockDTO extends ConceptDTO {

    @ApiModelProperty(notes = "Stock name", value = "stockName")
    private String name;

    @ApiModelProperty(notes = "Stock price", required = true, value = "12.345")
    private Double currentPrice;

    @ApiModelProperty(notes = "Last modified date", value = "1506625773887")
    private Long lastUpdate;

}
