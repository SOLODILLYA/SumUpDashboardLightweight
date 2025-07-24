package com.solod.sumup_dashboard_lightweight.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
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
        public Map<String, Object> getSummary(
                        @RequestParam("token") String accessToken,
                        @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                        @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

                if (accessToken == null) {
                        throw new IllegalArgumentException("Token was not provided in the query!");
                }
                LocalDateTime from = (start != null) ? start : LocalDateTime.now().minusDays(7);
                LocalDateTime to = (end != null) ? end : LocalDateTime.now();
                List<Map<String, Object>> allTransactions = getAllTransactions(accessToken,
                                from.toString(), to.toString());
                List<Map<String, Object>> transactions = allTransactions.stream().collect(Collectors.toList());
                Map<String, Double> salesMap = transactions.stream()
                                .filter(tx -> tx.get("date") != null)
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
                                .mapToDouble(tx -> {
                                        Object amount = tx.get("amount");
                                        return amount instanceof Number ? ((Number) amount).doubleValue() : 0.0;
                                })
                                .sum();

                int numTransactions = transactions.size();

                double avgTransactionValue = numTransactions > 0
                                ? totalSales / numTransactions
                                : 0;

                Map<String, Object> summary = new HashMap<>();
                summary.put("totalSales", Math.round(totalSales * 100.0) / 100.0);
                summary.put("numTransactions", numTransactions);
                summary.put("avgTransactionValue", Math.round(avgTransactionValue * 100.0) / 100.0);
                summary.put("salesOverTime", salesOverTime);
                summary.put("transactions", transactions);
                summary.put("soldProducts", calculateSoldProducts(transactions));
                summary.put("mostSoldProducts", calculateMostSoldProducts(transactions));
                summary.put("leastSoldProducts", calculateLeastSoldProducts(transactions));
                summary.put("soldCombos", calculateSoldCombos(transactions));
                summary.put("mostSoldCombos", calculateMostSoldCombos(transactions));
                summary.put("leastSoldCombos", calculateLeastSoldCombos(transactions));

                return summary;

        }

        @GetMapping("/api/test-items")
        public List<Map<String, Object>> testItems() {
                return getAllTransactions("dummy_key", "2025-07-01T00:00", "2025-07-31T23:59");
        }

        List<Map<String, Object>> calculateSoldProducts(List<Map<String, Object>> transactions) {
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

                return productCount.entrySet().stream()
                                .sorted((a, b) -> b.getValue() - a.getValue())
                                .map(entry -> {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("name", entry.getKey());
                                        map.put("quantity", entry.getValue());
                                        return map;
                                })
                                .collect(Collectors.toList());
        }

        Map<String, Integer> calculateSoldCombos(List<Map<String, Object>> transactions) {
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
                                .filter(e -> e.getValue() >= 2)
                                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                                .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (e1, e2) -> e1,
                                                LinkedHashMap::new));
        }

        Map<String, Integer> calculateMostSoldCombos(List<Map<String, Object>> transactions) {
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

        List<Map<String, Object>> calculateMostSoldProducts(List<Map<String, Object>> transactions) {
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

                return productCount.entrySet().stream()
                                .sorted((a, b) -> b.getValue() - a.getValue())
                                .limit(10)
                                .map(entry -> {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("name", entry.getKey());
                                        map.put("quantity", entry.getValue());
                                        return map;
                                })
                                .collect(Collectors.toList());
        }

        List<Map<String, Object>> calculateLeastSoldProducts(List<Map<String, Object>> transactions) {
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

                return productCount.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue()) // ascending order
                                .limit(10) // 10 least sold
                                .map(entry -> {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("name", entry.getKey());
                                        map.put("quantity", entry.getValue());
                                        return map;
                                })
                                .collect(Collectors.toList());
        }

        Map<String, Integer> calculateLeastSoldCombos(List<Map<String, Object>> transactions) {
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
                                .filter(e -> e.getValue() >= 2) // only combos with 2 or more
                                .sorted(Map.Entry.comparingByValue()) // ascending
                                .limit(10) // 10 least sold
                                .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (e1, e2) -> e1,
                                                LinkedHashMap::new));
        }

        public static List<Map<String, Object>> getAllTransactions(
                        String apiKey, String startDate, String endDate) {

                List<Map<String, Object>> allTransactions = new ArrayList<>();
                int page = 1;
                while (true) { // Limit to 2 pages for testing if using getTransactionsTest (&& page<=2)
                        List<Map<String, Object>> transactions = getTransactions(apiKey, startDate, endDate, page);

                        if (transactions == null || transactions.isEmpty()) {
                                System.out.println("No more transactions found or error occurred.");
                                break;
                        }

                        allTransactions.addAll(transactions);
                        page++;
                }

                return allTransactions;
        }

        public static List<Map<String, Object>> getTransactions(String apiKey, String startDate, String endDate,
                        int page) {
                List<Map<String, Object>> transactions = new ArrayList<>();

                try {
                        String url = String.format("https://api.sumup.com/v0.1/me/transactions?from=%s&to=%s&page=%d",
                                        startDate, endDate, page);

                        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(url))
                                        .header("Authorization", "Bearer " + apiKey)
                                        .header("Accept", "application/json")
                                        .GET()
                                        .build();

                        HttpClient client = HttpClient.newHttpClient();
                        HttpResponse<String> response = client.send(request,
                                        HttpResponse.BodyHandlers.ofString());

                        if (response.statusCode() == 200) {
                                String json = response.body();

                                ObjectMapper objectMapper = new ObjectMapper();

                                Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);
                                Object items = jsonMap.get("items");

                                if (items instanceof List) {
                                        transactions = (List<Map<String, Object>>) items;
                                }

                        } else {
                                System.err.println("Failed to fetch transactions: " + response.statusCode() +
                                                " "
                                                + response.body());
                        }

                } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                }

                return transactions;
        }

        // Stub method to simulate API call
        public static List<Map<String, Object>> getTransactionsTest(String apiKey, String startDate, String endDate,
                        int page) {
                List<Map<String, Object>> transactions = new ArrayList<>();

                try {
                        String url = "http://localhost:8080/api/transactions";

                        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(url))
                                        .header("Accept", "application/json")
                                        .GET()
                                        .build();

                        HttpClient client = HttpClient.newHttpClient();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        if (response.statusCode() == 200) {
                                String json = response.body();

                                ObjectMapper objectMapper = new ObjectMapper();

                                transactions = objectMapper.readValue(
                                                json,
                                                new TypeReference<List<Map<String, Object>>>() {
                                                });

                        } else {
                                System.err.println("Failed to fetch local transactions: " + response.statusCode());
                        }

                } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                }

                return transactions;

        }

}