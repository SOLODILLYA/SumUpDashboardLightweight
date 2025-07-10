package com.solod.sumup_dashboard_lightweight.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SummaryController {

    @GetMapping("/api/summary")
    public Map<String, Object> getSummary() {
        return Map.of(
            "totalSales", 234.75,
            "numTransactions", 5,
            "avgTransactionValue", 46.95,
            "salesOverTime", List.of(
                Map.of("date", "2025-07-08", "amount", 40),
                Map.of("date", "2025-07-09", "amount", 95),
                Map.of("date", "2025-07-10", "amount", 99.75)
            ),
            "transactions", List.of(
                Map.of("id", "tx1", "date", "2025-07-10", "amount", 25.5, "paymentType", "card", "status", "paid"),
                Map.of("id", "tx2", "date", "2025-07-10", "amount", 39.9, "paymentType", "cash", "status", "paid")
            )
        );
    }
}
