package com.solod.sumup_dashboard_lightweight.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SummaryController {

    @GetMapping("/api/summary")
    public Map<String, Object> getSummary() {
        List<Map<String, Object>> transactions = List.of(
                Map.of(
                        "id", "tx1001",
                        "date", "2025-07-10",
                        "amount", 25.5,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Cappuccino", "quantity", 2, "price", 3.5),
                                Map.of("name", "Muffin", "quantity", 1, "price", 4.5),
                                Map.of("name", "Orange Juice", "quantity", 1, "price", 4.0))),
                Map.of(
                        "id", "tx1002",
                        "date", "2025-07-10",
                        "amount", 39.9,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Espresso", "quantity", 3, "price", 2.5),
                                Map.of("name", "Ham Croissant", "quantity", 2, "price", 5.5),
                                Map.of("name", "Flat White", "quantity", 1, "price", 3.9))),
                Map.of(
                        "id", "tx1003",
                        "date", "2025-07-11",
                        "amount", 51.3,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Latte", "quantity", 2, "price", 4.2),
                                Map.of("name", "Bagel", "quantity", 2, "price", 6.0),
                                Map.of("name", "Smoothie", "quantity", 1, "price", 5.0),
                                Map.of("name", "Water", "quantity", 2, "price", 2.5))),
                Map.of(
                        "id", "tx1004",
                        "date", "2025-07-11",
                        "amount", 38.45,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Iced Latte", "quantity", 1, "price", 4.5),
                                Map.of("name", "Brownie", "quantity", 2, "price", 3.5),
                                Map.of("name", "Club Sandwich", "quantity", 1, "price", 8.0),
                                Map.of("name", "Cold Brew", "quantity", 1, "price", 4.5),
                                Map.of("name", "Sparkling Water", "quantity", 2, "price", 2.5))),
                Map.of(
                        "id", "tx1005",
                        "date", "2025-07-12",
                        "amount", 61.50,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Cappuccino", "quantity", 3, "price", 3.5),
                                Map.of("name", "Almond Croissant", "quantity", 2, "price", 4.5),
                                Map.of("name", "Orange Juice", "quantity", 2, "price", 4.0),
                                Map.of("name", "Granola Bowl", "quantity", 1, "price", 6.0))),
                Map.of(
                        "id", "tx1006",
                        "date", "2025-07-13",
                        "amount", 84.15,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Flat White", "quantity", 4, "price", 3.9),
                                Map.of("name", "Avocado Toast", "quantity", 2, "price", 7.5),
                                Map.of("name", "Matcha Latte", "quantity", 1, "price", 5.5),
                                Map.of("name", "Brownie", "quantity", 1, "price", 3.5))),
                Map.of(
                        "id", "tx1007",
                        "date", "2025-07-14",
                        "amount", 53.75,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Espresso", "quantity", 2, "price", 2.5),
                                Map.of("name", "Turkey Wrap", "quantity", 2, "price", 7.5),
                                Map.of("name", "Iced Tea", "quantity", 2, "price", 3.0),
                                Map.of("name", "Cookie", "quantity", 2, "price", 2.5))),
                Map.of(
                        "id", "tx1008",
                        "date", "2025-07-15",
                        "amount", 64.90,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Latte", "quantity", 3, "price", 4.2),
                                Map.of("name", "Club Sandwich", "quantity", 2, "price", 8.0),
                                Map.of("name", "Smoothie", "quantity", 1, "price", 5.0),
                                Map.of("name", "Apple Pie", "quantity", 1, "price", 4.5))));
        Map<String, Double> salesMap = transactions.stream()
                .collect(Collectors.groupingBy(
                        tx -> (String) tx.get("date"),
                        Collectors.summingDouble(tx -> (double) tx.get("amount"))));

        List<Map<String, Object>> salesOverTime = salesMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", entry.getKey());
                    map.put("amount", Math.round(entry.getValue() * 100.0) / 100.0);
                    return map;
                })
                .collect(Collectors.toList());

        double totalSales = transactions.stream()
                .mapToDouble(tx -> (double) tx.get("amount"))
                .sum();

        int numTransactions = transactions.size();

        double avgTransactionValue = numTransactions > 0
                ? totalSales / numTransactions
                : 0;

        Map<String, Integer> productCount = new HashMap<>();
        for (Map<String, Object> tx : transactions) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) tx.get("items");
            if (items != null) {
                for (Map<String, Object> item : items) {
                    String name = (String) item.get("name");
                    int quantity = (int) item.get("quantity");
                    productCount.put(name, productCount.getOrDefault(name, 0) + quantity);
                }
            }
        }

        List<Map<String, Object>> productSales = productCount.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", entry.getKey());
                    map.put("quantity", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
        productSales.sort((a, b) -> ((Integer) a.get("quantity")) - ((Integer) b.get("quantity")));
        Map<String, Integer> topCombos = calculateTopCombos(transactions);
        Map<String, Integer> reversedCombos = new LinkedHashMap<>();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(topCombos.entrySet());
        Collections.reverse(entries);
        for (Map.Entry<String, Integer> entry : entries) {
            reversedCombos.put(entry.getKey(), entry.getValue());
        }
        topCombos = reversedCombos;

        return Map.of(
                "totalSales", Math.round(totalSales * 100.0) / 100.0,
                "numTransactions", numTransactions,
                "avgTransactionValue", Math.round(avgTransactionValue * 100.0) / 100.0,
                "salesOverTime", salesOverTime,
                "transactions", transactions,
                "productSales", productSales,
                "productCombos", topCombos);
    }

    private Map<String, Integer> calculateTopCombos(List<Map<String, Object>> transactions) {
        Map<String, Integer> comboCount = new HashMap<>();
        for (Map<String, Object> tx : transactions) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) tx.get("items");
            if (items == null || items.size() < 2)
                continue;

            List<String> names = items.stream()
                    .map(i -> (String) i.get("name"))
                    .distinct()
                    .sorted()
                    .toList();

            for (int i = 0; i < names.size(); i++) {
                for (int j = i + 1; j < names.size(); j++) {
                    String comboKey = names.get(i) + " + " + names.get(j);
                    comboCount.put(comboKey, comboCount.getOrDefault(comboKey, 0) + 1);
                }
            }
        }

        return comboCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10) // top 10 combos
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }
}
