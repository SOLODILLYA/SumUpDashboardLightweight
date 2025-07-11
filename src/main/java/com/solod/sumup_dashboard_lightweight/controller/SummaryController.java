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
                        "id", "tx0001",
                        "date", "2025-07-06T13:15",
                        "amount", 19.5,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Espresso", "quantity", 1, "price", 2.5),
                                Map.of("name", "Iced Coffee", "quantity", 2, "price", 3.2),
                                Map.of("name", "Scone", "quantity", 2, "price", 2.3),
                                Map.of("name", "Orange Juice", "quantity", 2, "price", 3.0))),

                Map.of(
                        "id", "tx0002",
                        "date", "2025-07-11T08:15",
                        "amount", 18.6,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Chai Latte", "quantity", 2, "price", 4.2),
                                Map.of("name", "Sparkling Water", "quantity", 1, "price", 2.0),
                                Map.of("name", "Iced Coffee", "quantity", 1, "price", 3.2),
                                Map.of("name", "Muffin", "quantity", 2, "price", 2.5))),

                Map.of(
                        "id", "tx0003",
                        "date", "2025-07-06T14:45",
                        "amount", 7.0,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Espresso", "quantity", 2, "price", 2.5),
                                Map.of("name", "Croissant", "quantity", 1, "price", 2.0))),

                Map.of(
                        "id", "tx0004",
                        "date", "2025-07-01T18:30",
                        "amount", 18.8,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Croissant", "quantity", 2, "price", 2.0),
                                Map.of("name", "Brownie", "quantity", 2, "price", 2.6),
                                Map.of("name", "Macchiato", "quantity", 2, "price", 2.7),
                                Map.of("name", "Chai Latte", "quantity", 1, "price", 4.2))),

                Map.of(
                        "id", "tx0005",
                        "date", "2025-07-08T17:45",
                        "amount", 2.3,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Scone", "quantity", 1, "price", 2.3))),

                Map.of(
                        "id", "tx0006",
                        "date", "2025-07-13T16:45",
                        "amount", 40.2,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Espresso", "quantity", 2, "price", 2.5),
                                Map.of("name", "Granola Bowl", "quantity", 3, "price", 4.8),
                                Map.of("name", "Cappuccino", "quantity", 2, "price", 3.5),
                                Map.of("name", "Sparkling Water", "quantity", 3, "price", 2.0),
                                Map.of("name", "Brownie", "quantity", 3, "price", 2.6))),

                Map.of(
                        "id", "tx0007",
                        "date", "2025-07-06T17:30",
                        "amount", 8.0,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Mocha", "quantity", 2, "price", 4.0))),

                Map.of(
                        "id", "tx0008",
                        "date", "2025-07-15T10:45",
                        "amount", 36.7,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Croissant", "quantity", 2, "price", 2.0),
                                Map.of("name", "Cold Brew", "quantity", 3, "price", 3.7),
                                Map.of("name", "Hot Chocolate", "quantity", 2, "price", 3.6),
                                Map.of("name", "Flat White", "quantity", 1, "price", 3.9),
                                Map.of("name", "Cappuccino", "quantity", 3, "price", 3.5))),

                Map.of(
                        "id", "tx0009",
                        "date", "2025-07-14T14:30",
                        "amount", 16.2,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Croissant", "quantity", 1, "price", 2.0),
                                Map.of("name", "Fruit Salad", "quantity", 2, "price", 3.5),
                                Map.of("name", "Hot Chocolate", "quantity", 2, "price", 3.6))),

                Map.of(
                        "id", "tx0010",
                        "date", "2025-07-12T14:30",
                        "amount", 31.2,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Matcha Latte", "quantity", 3, "price", 4.5),
                                Map.of("name", "Flat White", "quantity", 1, "price", 3.9),
                                Map.of("name", "Orange Juice", "quantity", 1, "price", 3.0),
                                Map.of("name", "Granola Bowl", "quantity", 1, "price", 4.8),
                                Map.of("name", "Sparkling Water", "quantity", 3, "price", 2.0))),

                Map.of(
                        "id", "tx0011",
                        "date", "2025-07-08T17:45",
                        "amount", 5.2,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Brownie", "quantity", 2, "price", 2.6))),

                Map.of(
                        "id", "tx0012",
                        "date", "2025-07-11T14:15",
                        "amount", 10.8,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Hot Chocolate", "quantity", 3, "price", 3.6))),

                Map.of(
                        "id", "tx0013",
                        "date", "2025-07-02T07:30",
                        "amount", 6.0,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Bagel", "quantity", 2, "price", 3.0))),

                Map.of(
                        "id", "tx0014",
                        "date", "2025-07-02T09:00",
                        "amount", 3.6,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Hot Chocolate", "quantity", 1, "price", 3.6))),

                Map.of(
                        "id", "tx0015",
                        "date", "2025-07-10T12:30",
                        "amount", 12.9,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Macchiato", "quantity", 2, "price", 2.7),
                                Map.of("name", "Cappuccino", "quantity", 1, "price", 3.5),
                                Map.of("name", "Mocha", "quantity", 1, "price", 4.0))),

                Map.of(
                        "id", "tx0016",
                        "date", "2025-07-10T07:30",
                        "amount", 8.4,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Americano", "quantity", 3, "price", 2.8))),

                Map.of(
                        "id", "tx0017",
                        "date", "2025-07-12T13:45",
                        "amount", 15.9,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Latte", "quantity", 3, "price", 3.8),
                                Map.of("name", "Matcha Latte", "quantity", 1, "price", 4.5))),

                Map.of(
                        "id", "tx0018",
                        "date", "2025-07-09T10:45",
                        "amount", 3.6,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Hot Chocolate", "quantity", 1, "price", 3.6))),

                Map.of(
                        "id", "tx0019",
                        "date", "2025-07-02T08:15",
                        "amount", 39.1,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Brownie", "quantity", 2, "price", 2.6),
                                Map.of("name", "Chai Latte", "quantity", 2, "price", 4.2),
                                Map.of("name", "Fruit Salad", "quantity", 3, "price", 3.5),
                                Map.of("name", "Mocha", "quantity", 3, "price", 4.0),
                                Map.of("name", "Bagel", "quantity", 1, "price", 3.0))),

                Map.of(
                        "id", "tx0020",
                        "date", "2025-07-11T16:30",
                        "amount", 2.7,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Macchiato", "quantity", 1, "price", 2.7))),

                Map.of(
                        "id", "tx0021",
                        "date", "2025-07-10T14:00",
                        "amount", 27.7,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Cappuccino", "quantity", 1, "price", 3.5),
                                Map.of("name", "Chai Latte", "quantity", 3, "price", 4.2),
                                Map.of("name", "Latte", "quantity", 2, "price", 3.8),
                                Map.of("name", "Sparkling Water", "quantity", 2, "price", 2.0))),

                Map.of(
                        "id", "tx0022",
                        "date", "2025-07-03T11:30",
                        "amount", 25.8,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Latte", "quantity", 2, "price", 3.8),
                                Map.of("name", "Sandwich", "quantity", 1, "price", 4.5),
                                Map.of("name", "Toast", "quantity", 1, "price", 2.8),
                                Map.of("name", "Hot Chocolate", "quantity", 2, "price", 3.6),
                                Map.of("name", "Cold Brew", "quantity", 1, "price", 3.7))),

                Map.of(
                        "id", "tx0023",
                        "date", "2025-07-09T10:30",
                        "amount", 13.0,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Latte", "quantity", 1, "price", 3.8),
                                Map.of("name", "Hot Chocolate", "quantity", 2, "price", 3.6),
                                Map.of("name", "Croissant", "quantity", 1, "price", 2.0))),

                Map.of(
                        "id", "tx0024",
                        "date", "2025-07-12T12:15",
                        "amount", 4.0,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Sparkling Water", "quantity", 2, "price", 2.0))),

                Map.of(
                        "id", "tx0025",
                        "date", "2025-07-06T11:30",
                        "amount", 22.9,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Latte", "quantity", 2, "price", 3.8),
                                Map.of("name", "Cappuccino", "quantity", 3, "price", 3.5),
                                Map.of("name", "Granola Bowl", "quantity", 1, "price", 4.8))),

                Map.of(
                        "id", "tx0026",
                        "date", "2025-07-13T13:00",
                        "amount", 15.6,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Espresso", "quantity", 3, "price", 2.5),
                                Map.of("name", "Macchiato", "quantity", 3, "price", 2.7))),

                Map.of(
                        "id", "tx0027",
                        "date", "2025-07-07T07:00",
                        "amount", 14.6,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Iced Coffee", "quantity", 1, "price", 3.2),
                                Map.of("name", "Croissant", "quantity", 1, "price", 2.0),
                                Map.of("name", "Scone", "quantity", 1, "price", 2.3),
                                Map.of("name", "Fruit Salad", "quantity", 1, "price", 3.5),
                                Map.of("name", "Hot Chocolate", "quantity", 1, "price", 3.6))),

                Map.of(
                        "id", "tx0028",
                        "date", "2025-07-07T16:15",
                        "amount", 9.0,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Orange Juice", "quantity", 3, "price", 3.0))),

                Map.of(
                        "id", "tx0029",
                        "date", "2025-07-07T08:30",
                        "amount", 14.8,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Orange Juice", "quantity", 2, "price", 3.0),
                                Map.of("name", "Sparkling Water", "quantity", 2, "price", 2.0),
                                Map.of("name", "Granola Bowl", "quantity", 1, "price", 4.8))),

                Map.of(
                        "id", "tx0030",
                        "date", "2025-07-15T10:15",
                        "amount", 19.0,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Bagel", "quantity", 3, "price", 3.0),
                                Map.of("name", "Sparkling Water", "quantity", 2, "price", 2.0),
                                Map.of("name", "Muffin", "quantity", 1, "price", 2.5),
                                Map.of("name", "Fruit Salad", "quantity", 1, "price", 3.5))),

                Map.of(
                        "id", "tx0031",
                        "date", "2025-07-14T08:30",
                        "amount", 30.3,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Granola Bowl", "quantity", 1, "price", 4.8),
                                Map.of("name", "Mocha", "quantity", 3, "price", 4.0),
                                Map.of("name", "Sandwich", "quantity", 3, "price", 4.5))),

                Map.of(
                        "id", "tx0032",
                        "date", "2025-07-09T14:15",
                        "amount", 23.4,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Latte", "quantity", 1, "price", 3.8),
                                Map.of("name", "Espresso", "quantity", 2, "price", 2.5),
                                Map.of("name", "Toast", "quantity", 2, "price", 2.8),
                                Map.of("name", "Matcha Latte", "quantity", 2, "price", 4.5))),

                Map.of(
                        "id", "tx0033",
                        "date", "2025-07-09T11:00",
                        "amount", 7.6,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Sparkling Water", "quantity", 1, "price", 2.0),
                                Map.of("name", "Americano", "quantity", 2, "price", 2.8))),

                Map.of(
                        "id", "tx0034",
                        "date", "2025-07-01T14:15",
                        "amount", 24.4,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Toast", "quantity", 3, "price", 2.8),
                                Map.of("name", "Bagel", "quantity", 1, "price", 3.0),
                                Map.of("name", "Matcha Latte", "quantity", 2, "price", 4.5),
                                Map.of("name", "Croissant", "quantity", 2, "price", 2.0))),

                Map.of(
                        "id", "tx0035",
                        "date", "2025-07-12T07:15",
                        "amount", 16.6,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Cold Brew", "quantity", 1, "price", 3.7),
                                Map.of("name", "Scone", "quantity", 3, "price", 2.3),
                                Map.of("name", "Bagel", "quantity", 2, "price", 3.0))),

                Map.of(
                        "id", "tx0036",
                        "date", "2025-07-13T13:30",
                        "amount", 32.8,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Hot Chocolate", "quantity", 3, "price", 3.6),
                                Map.of("name", "Bagel", "quantity", 2, "price", 3.0),
                                Map.of("name", "Matcha Latte", "quantity", 3, "price", 4.5),
                                Map.of("name", "Espresso", "quantity", 1, "price", 2.5))),

                Map.of(
                        "id", "tx0037",
                        "date", "2025-07-04T09:00",
                        "amount", 5.0,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Muffin", "quantity", 2, "price", 2.5))),

                Map.of(
                        "id", "tx0038",
                        "date", "2025-07-01T09:15",
                        "amount", 18.1,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Macchiato", "quantity", 1, "price", 2.7),
                                Map.of("name", "Fruit Salad", "quantity", 2, "price", 3.5),
                                Map.of("name", "Flat White", "quantity", 1, "price", 3.9),
                                Map.of("name", "Muffin", "quantity", 1, "price", 2.5),
                                Map.of("name", "Sparkling Water", "quantity", 1, "price", 2.0))),

                Map.of(
                        "id", "tx0039",
                        "date", "2025-07-02T11:30",
                        "amount", 23.3,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Espresso", "quantity", 3, "price", 2.5),
                                Map.of("name", "Croissant", "quantity", 3, "price", 2.0),
                                Map.of("name", "Fruit Salad", "quantity", 2, "price", 3.5),
                                Map.of("name", "Americano", "quantity", 1, "price", 2.8))),

                Map.of(
                        "id", "tx0040",
                        "date", "2025-07-11T11:00",
                        "amount", 40.6,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Sparkling Water", "quantity", 2, "price", 2.0),
                                Map.of("name", "Macchiato", "quantity", 2, "price", 2.7),
                                Map.of("name", "Matcha Latte", "quantity", 2, "price", 4.5),
                                Map.of("name", "Hot Chocolate", "quantity", 3, "price", 3.6),
                                Map.of("name", "Latte", "quantity", 3, "price", 3.8))),

                Map.of(
                        "id", "tx0041",
                        "date", "2025-07-05T13:15",
                        "amount", 10.7,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Scone", "quantity", 1, "price", 2.3),
                                Map.of("name", "Americano", "quantity", 3, "price", 2.8))),

                Map.of(
                        "id", "tx0042",
                        "date", "2025-07-11T15:15",
                        "amount", 18.6,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Cold Brew", "quantity", 3, "price", 3.7),
                                Map.of("name", "Muffin", "quantity", 1, "price", 2.5),
                                Map.of("name", "Espresso", "quantity", 2, "price", 2.5))),

                Map.of(
                        "id", "tx0043",
                        "date", "2025-07-15T08:30",
                        "amount", 4.8,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Granola Bowl", "quantity", 1, "price", 4.8))),

                Map.of(
                        "id", "tx0044",
                        "date", "2025-07-03T12:45",
                        "amount", 26.6,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Bagel", "quantity", 3, "price", 3.0),
                                Map.of("name", "Espresso", "quantity", 2, "price", 2.5),
                                Map.of("name", "Chai Latte", "quantity", 3, "price", 4.2))),

                Map.of(
                        "id", "tx0045",
                        "date", "2025-07-08T13:00",
                        "amount", 17.3,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Iced Coffee", "quantity", 1, "price", 3.2),
                                Map.of("name", "Orange Juice", "quantity", 2, "price", 3.0),
                                Map.of("name", "Macchiato", "quantity", 3, "price", 2.7))),

                Map.of(
                        "id", "tx0046",
                        "date", "2025-07-05T13:30",
                        "amount", 43.2,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Iced Coffee", "quantity", 3, "price", 3.2),
                                Map.of("name", "Orange Juice", "quantity", 1, "price", 3.0),
                                Map.of("name", "Granola Bowl", "quantity", 3, "price", 4.8),
                                Map.of("name", "Matcha Latte", "quantity", 1, "price", 4.5),
                                Map.of("name", "Flat White", "quantity", 3, "price", 3.9))),

                Map.of(
                        "id", "tx0047",
                        "date", "2025-07-12T11:30",
                        "amount", 23.1,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Matcha Latte", "quantity", 2, "price", 4.5),
                                Map.of("name", "Croissant", "quantity", 1, "price", 2.0),
                                Map.of("name", "Muffin", "quantity", 1, "price", 2.5),
                                Map.of("name", "Granola Bowl", "quantity", 2, "price", 4.8))),

                Map.of(
                        "id", "tx0048",
                        "date", "2025-07-06T13:30",
                        "amount", 6.9,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Scone", "quantity", 3, "price", 2.3))),

                Map.of(
                        "id", "tx0049",
                        "date", "2025-07-01T13:45",
                        "amount", 15.3,
                        "paymentType", "card",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Scone", "quantity", 3, "price", 2.3),
                                Map.of("name", "Americano", "quantity", 3, "price", 2.8))),

                Map.of(
                        "id", "tx0050",
                        "date", "2025-07-04T11:45",
                        "amount", 29.0,
                        "paymentType", "cash",
                        "status", "paid",
                        "items", List.of(
                                Map.of("name", "Flat White", "quantity", 2, "price", 3.9),
                                Map.of("name", "Americano", "quantity", 2, "price", 2.8),
                                Map.of("name", "Bagel", "quantity", 2, "price", 3.0),
                                Map.of("name", "Granola Bowl", "quantity", 2, "price", 4.8))));
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

}
