package br.com.fatec.atv1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BrandsController.class)
public classBrandsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Teste GET ALL
    @Test
    public void testGetAllBrands() throws Exception {
        mockMvc.perform(get("/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1.name").value("Xiaomi"))
                .andExpect(jsonPath("$.2.name").value("Samsung"))
                .andExpect(jsonPath("$.3.name").value("Jovi"));
    }

    // Teste GET BY ID - Sucesso
    @Test
    public void testGetBrandById_Success() throws Exception {
        mockMvc.perform(get("/brands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Xiaomi"))
                .andExpect(jsonPath("$.country").value("China"))
                .andExpect(jsonPath("$.founded").value("2010"));
    }

    // Teste GET BY ID - Não encontrado
    @Test
    public void testGetBrandById_NotFound() throws Exception {
        mockMvc.perform(get("/brands/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Marca não encontrada"))
                .andExpect(jsonPath("$.id").value("999"));
    }

    // Teste POST - Adicionar nova marca
    @Test
    public void testAddBrand() throws Exception {
        String brandJson = """
            {
                "id": "4",
                "name": "Apple",
                "country": "USA",
                "founded": "1976"
            }
        """;

        mockMvc.perform(post("/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(brandJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.country").value("USA"))
                .andExpect(jsonPath("$.founded").value("1976"))
                .andExpect(jsonPath("$.message").value("Marca adicionada com sucesso"));
    }
}