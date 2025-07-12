package com.solod.sumup_dashboard_lightweight.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SummaryControllerTest {

    @Autowired
    private SummaryController controller;

    @Autowired
    private MockMvc mockMvc;

    private List<Map<String, Object>> mockTransactions() {
        return List.of(
                Map.of("items", List.of(
                        Map.of("name", "Coffee", "quantity", 2),
                        Map.of("name", "Bagel", "quantity", 1))),
                Map.of("items", List.of(
                        Map.of("name", "Coffee", "quantity", 1),
                        Map.of("name", "Bagel", "quantity", 1),
                        Map.of("name", "Muffin", "quantity", 1))),
                Map.of("items", List.of(
                        Map.of("name", "Tea", "quantity", 1),
                        Map.of("name", "Muffin", "quantity", 1))));
    }

    @Test
    void summaryEndpointReturns200AndValidJson() throws Exception {
        mockMvc.perform(get("/api/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSales").exists())
                .andExpect(jsonPath("$.numTransactions").exists())
                .andExpect(jsonPath("$.transactions").isArray());
    }

    @Test
    public void testCalculateSoldProducts() {
        List<Map<String, Object>> result = controller.calculateSoldProducts(mockTransactions());
        assertEquals(4, result.size());
        assertEquals("Coffee", result.get(0).get("name"));
        assertEquals(3, result.get(0).get("quantity"));
    }

    @Test
    public void testCalculateMostSoldProducts() {
        List<Map<String, Object>> result = controller.calculateMostSoldProducts(mockTransactions());
        assertEquals("Coffee", result.get(0).get("name"));
        assertEquals(3, result.get(0).get("quantity"));
    }

    @Test
    public void testCalculateLeastSoldProducts() {
        List<Map<String, Object>> result = controller.calculateLeastSoldProducts(mockTransactions());
        assertEquals("Tea", result.get(0).get("name"));
        assertEquals(1, result.get(0).get("quantity"));
    }

    @Test
    public void testCalculateSoldCombos() {
        Map<String, Integer> result = controller.calculateSoldCombos(mockTransactions());
        assertTrue(result.containsKey("Bagel + Coffee"));
        assertEquals(2, result.get("Bagel + Coffee"));
    }

    @Test
    public void testCalculateMostSoldCombos() {
        Map<String, Integer> result = controller.calculateMostSoldCombos(mockTransactions());
        assertTrue(result.containsKey("Bagel + Coffee"));
        assertEquals(2, result.get("Bagel + Coffee"));
    }

    @Test
    public void testCalculateLeastSoldCombos() {
        Map<String, Integer> result = controller.calculateLeastSoldCombos(mockTransactions());
        assertTrue(result.containsKey("Bagel + Coffee"));
        assertEquals(2, result.get("Bagel + Coffee"));
    }

    @Test
    public void testEmptyTransactions() {

        List<Map<String, Object>> empty = Collections.emptyList();

        assertTrue(controller.calculateSoldProducts(empty).isEmpty());

        assertTrue(controller.calculateMostSoldProducts(empty).isEmpty());

        assertTrue(controller.calculateLeastSoldProducts(empty).isEmpty());

        assertTrue(controller.calculateSoldCombos(empty).isEmpty());

        assertTrue(controller.calculateMostSoldCombos(empty).isEmpty());

        assertTrue(controller.calculateLeastSoldCombos(empty).isEmpty());

    }

    @Test
    public void testTransactionWithNoItems() {

        List<Map<String, Object>> tx = List.of(Map.of("id", "123"));

        assertTrue(controller.calculateSoldProducts(tx).isEmpty());

        assertTrue(controller.calculateSoldCombos(tx).isEmpty());

    }

    @Test
    public void testCorsHeadersPresent() throws Exception {

        mockMvc.perform(get("/api/summary").header("Origin", "http://localhost:3000"))

                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"));

    }
}
