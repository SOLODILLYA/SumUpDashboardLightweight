package com.solod.sumup_dashboard_lightweight.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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
        mockMvc.perform(get("/api/summary")
                .param("token", "dummy-token"))
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
        mockMvc.perform(get("/api/summary")
                .param("token", "dummy")
                .header("Origin", "http://localhost:3000"))
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"));
    }

    @Test
    @DisplayName("GET /api/summary without token → 400 Bad Request")
    void summaryEndpointMissingToken() throws Exception {
        mockMvc.perform(get("/api/summary"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/test-items → 200 and JSON array")
    void testItemsEndpointReturnsArray() throws Exception {
        mockMvc.perform(get("/api/test-items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/summary with date filters → 200 OK")
    void summaryEndpointWithDateFilters() throws Exception {
        mockMvc.perform(get("/api/summary")
                .param("token", "dummy")
                .param("start", "2025-07-01T00:00")
                .param("end", "2025-07-02T00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salesOverTime").isArray());
    }

    @Test
    @DisplayName("calculateSoldProducts aggregates duplicates in same transaction")
    void calculateSoldProductsWithDuplicateNames() {
        List<Map<String, Object>> tx = List.of(
                Map.of("items", List.of(
                        Map.of("name", "X", "quantity", 1),
                        Map.of("name", "X", "quantity", 2))));
        List<Map<String, Object>> result = controller.calculateSoldProducts(tx);
        assertEquals(1, result.size());
        assertEquals("X", result.get(0).get("name"));
        assertEquals(3, result.get(0).get("quantity"));
    }

    @Test
    @DisplayName("calculateMostSoldProducts limits to top 10")
    void calculateMostSoldProductsLimitTo10() {
        List<Map<String, Object>> txs = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            txs.add(Map.of("items", List.of(
                    Map.of("name", "P" + i, "quantity", i))));
        }
        List<Map<String, Object>> top = controller.calculateMostSoldProducts(txs);
        assertEquals(10, top.size());
        assertEquals("P12", top.get(0).get("name"));
        assertEquals("P3", top.get(9).get("name"));
    }

    @Test
    @DisplayName("calculateLeastSoldProducts limits to bottom 10")
    void calculateLeastSoldProductsLimitTo10() {
        List<Map<String, Object>> txs = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            txs.add(Map.of("items", List.of(
                    Map.of("name", "P" + i, "quantity", i))));
        }
        List<Map<String, Object>> bottom = controller.calculateLeastSoldProducts(txs);
        assertEquals(10, bottom.size());
        assertEquals("P1", bottom.get(0).get("name"));
        assertEquals("P10", bottom.get(9).get("name"));
    }

    @Test
    @DisplayName("calculateMostSoldCombos limits to top 10 combos")
    void calculateMostSoldCombosLimitTo10() {
        Map<String, Object> itemsTx = Map.of("items", List.of(
                Map.of("name", "A"),
                Map.of("name", "B"),
                Map.of("name", "C"),
                Map.of("name", "D"),
                Map.of("name", "E"),
                Map.of("name", "F")));
        List<Map<String, Object>> txs = List.of(itemsTx);
        Map<String, Integer> combos = controller.calculateMostSoldCombos(txs);
        assertEquals(10, combos.size());
    }

    @Test
    @DisplayName("Calling getSummary(...) with null token should throw IllegalArgumentException")
    void directGetSummaryWithNullToken() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.getSummary(
                    /* token */ null,
                    /* start */ null,
                    /* end */ null);
        }, "Expected getSummary(null, ..) to throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Calling getSummary(...) with empty token should NOT throw")
    void directGetSummaryWithEmptyToken() {
        assertDoesNotThrow(() -> {
            Map<String, Object> summary = controller.getSummary(
                    /* token */ "",
                    /* start */ LocalDateTime.now().minusDays(1),
                    /* end */ LocalDateTime.now());
            assertNotNull(summary);
        });
    }

    @Test
    @DisplayName("calculateMostSoldProducts skips transactions with null items")
    void calculateMostSoldProductsWithNullItems() {
        List<Map<String, Object>> txs = List.of(
                Collections.singletonMap("items", null),
                Collections.singletonMap("items", List.of(
                        Map.of("name", "X", "quantity", 5))));

        var result = controller.calculateMostSoldProducts(txs);
        assertNotNull(result);
        assertEquals(1, result.size(), "Should return exactly one product");
        assertEquals("X", result.get(0).get("name"));
        assertEquals(5, result.get(0).get("quantity"));
    }

    @Test
    @DisplayName("calculateLeastSoldProducts skips transactions with null items")
    void calculateLeastSoldProductsWithNullItems() {
        List<Map<String, Object>> txs = List.of(
                Collections.singletonMap("items", null),
                Collections.singletonMap("items", List.of(
                        Map.of("name", "Y", "quantity", 3))));

        var result = controller.calculateLeastSoldProducts(txs);
        assertNotNull(result);
        assertEquals(1, result.size(), "Should return exactly one product");
        assertEquals("Y", result.get(0).get("name"));
        assertEquals(3, result.get(0).get("quantity"));
    }

    @Test
    @DisplayName("calculateSoldCombos skips null and single-item lists, counts valid combos")
    void calculateSoldCombosWithNullItems() {
        List<Map<String, Object>> txs = List.of(
                Collections.singletonMap("items", null),
                Collections.singletonMap("items", List.of(
                        Map.of("name", "A", "quantity", 1))),
                Collections.singletonMap("items", List.of(
                        Map.of("name", "B", "quantity", 1),
                        Map.of("name", "C", "quantity", 1))),
                Collections.singletonMap("items", List.of(
                        Map.of("name", "B", "quantity", 1),
                        Map.of("name", "C", "quantity", 1))));

        var result = controller.calculateSoldCombos(txs);
        assertEquals(1, result.size(), "Only one combo B + C should reach count>=2");
        assertTrue(result.containsKey("B + C"));
        assertEquals(2, result.get("B + C"));
    }
}
