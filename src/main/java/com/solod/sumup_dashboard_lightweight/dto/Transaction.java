package com.solod.sumup_dashboard_lightweight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String id;
    private String status;
    private String user;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime timestamp;
}