package com.skyfenko.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyfenko.service.dto.impl.StockDTO;
import com.skyfenko.web.constants.URIConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", "password1"));
    }

    @Test
    public void stocksSize() throws Exception {
        this.mockMvc.perform(get(URIConstants.Api.STOCKS + "/")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(100)));
    }

    @Test
    public void stocksById() throws Exception {
        this.mockMvc.perform(get(URIConstants.Api.STOCKS + "/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("stockName0")));
    }

    @Test
    public void stocksByNonExistingId() throws Exception {
        this.mockMvc.perform(get(URIConstants.Api.STOCKS + "/1000")).andExpect(status().is(404));
    }

    @Test
    public void stockSaveAndDelete() throws Exception {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setCurrentPrice(12.12);
        stockDTO.setName("123");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = this.mockMvc.perform(post(URIConstants.Api.STOCKS + "/").with(csrf())
                .content(objectMapper.writeValueAsString(stockDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("123")))
                .andExpect(jsonPath("$.currentPrice", equalTo(12.12)))
                .andReturn();

        StockDTO received = (StockDTO) objectMapper.reader().forType(StockDTO.class).readValue(mvcResult.getResponse().getContentAsString());

        this.mockMvc.perform(delete(URIConstants.Api.STOCKS + "/" + received.getId()).with(csrf())).andExpect(status().isOk());

        this.mockMvc.perform(get(URIConstants.Api.STOCKS + "/" + received.getId())).andExpect(status().is(404));
    }

    @Test
    public void stockSaveAndUpdateAndDelete() throws Exception {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setCurrentPrice(12.12);
        stockDTO.setName("123");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = this.mockMvc.perform(post(URIConstants.Api.STOCKS + "/").with(csrf())
                .content(objectMapper.writeValueAsString(stockDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("123")))
                .andExpect(jsonPath("$.currentPrice", equalTo(12.12)))
                .andReturn();

        StockDTO received = (StockDTO) objectMapper.reader().forType(StockDTO.class).readValue(mvcResult.getResponse().getContentAsString());

        stockDTO.setCurrentPrice(13.13);

        this.mockMvc.perform(put(URIConstants.Api.STOCKS + "/" + received.getId()).with(csrf())
                .content(objectMapper.writeValueAsString(stockDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("123")))
                .andExpect(jsonPath("$.currentPrice", equalTo(13.13)));


        this.mockMvc.perform(delete(URIConstants.Api.STOCKS + "/" + received.getId()).with(csrf())).andExpect(status().isOk());
    }

    @Test
    public void deleteByNonExistingID() throws Exception {
        this.mockMvc.perform(get(URIConstants.Api.STOCKS + "/10001")).andExpect(status().is(404));
    }
}
