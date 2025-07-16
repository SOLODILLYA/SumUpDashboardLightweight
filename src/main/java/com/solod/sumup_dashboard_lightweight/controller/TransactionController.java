package com.solod.sumup_dashboard_lightweight.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class TransactionController {

    @GetMapping(value = "/api/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTransactions() throws IOException {
        ClassPathResource resource = new ClassPathResource("transactions.json");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
