package com.solod.sumup_dashboard_lightweight.controller;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SummaryControllerTest {

    private final SummaryController controller = new SummaryController(); // Replace with your actual class name

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
    public void testCalculateSoldProducts() {
        List<Map<String, Object>> result = controller.calculateSoldProducts(mockTransactions());
        assertEquals(4, result.size());
        assertEquals("Coffee", result.get(0).get("name"));
        assertEquals(3, result.get(0).get("quantity"));
    }

    @Test
    public void testCalculateMostSoldProducts() {
        List<Map<String, Object>> result = controller.calculateMostSoldProducts(mockTransactions());
        assertEquals(4, result.size());
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
        assertFalse(result.containsKey("Muffin + Tea"));
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
}
