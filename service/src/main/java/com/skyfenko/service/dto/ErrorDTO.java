package com.skyfenko.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ErrorDTO
 *
 * @author Stanislav Kyfenko
 */
@Getter
@Setter
@ToString
public class ErrorDTO {

    @ApiModelProperty(notes = "errorMessage")
    private String errorMessage;

}
