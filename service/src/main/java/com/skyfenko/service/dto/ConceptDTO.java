package com.skyfenko.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Umbrella class for all of DTOs
 *
 * @author Stanislav Kyfenko
 */
@Getter
@Setter
public abstract class ConceptDTO {

    @ApiModelProperty(notes = "id", value = "78")
    private Long id;
}
