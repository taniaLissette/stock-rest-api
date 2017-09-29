package com.skyfenko.web.controllers;

import com.skyfenko.service.dto.impl.StockDTO;
import com.skyfenko.service.stock.StockService;
import com.skyfenko.web.constants.URIConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Stock API controller
 *
 * @author Stanislav Kyfenko
 */
@RestController
@RequestMapping(URIConstants.Api.STOCKS)
@Api(value = "Stock REST Service", description = "Management of Stock items")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @ApiOperation(value = "List of all of stocks", response = StockDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StockDTO>> findAll() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @ApiOperation(value = "Find a stock by an ID", response = StockDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stock was successfully found"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "There is no stock by such ID")
    }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDTO> findById(@PathVariable Long id) {
        StockDTO byId = stockService.findById(id);

        if (byId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(byId);
    }

    @ApiOperation(value = "Create a stock", response = StockDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Stock was successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    }
    )
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO, UriComponentsBuilder builder) {
        StockDTO saved = stockService.save(stockDTO);

        UriComponents uriComponents = builder.path("/api/stocks/{id}").buildAndExpand(saved.getId());

        return ResponseEntity.created(uriComponents.toUri()).body(saved);
    }

    @ApiOperation(value = "Update a stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stock was successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "There is no stock by such ID to update")
    }
    )
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDTO> updateStock(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        StockDTO byId = stockService.findById(id);

        if (byId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // update current price only
        byId.setCurrentPrice(stockDTO.getCurrentPrice());

        StockDTO updated = stockService.update(byId);
        return ResponseEntity.ok(updated);
    }

    @ApiOperation(value = "Delete a stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stock was successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "There is no stock by such ID to delete")
    }
    )
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDTO> deleteStock(@PathVariable Long id) {
        StockDTO byId = stockService.findById(id);

        if (byId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        stockService.delete(id);

        return ResponseEntity.ok().build();
    }
}
