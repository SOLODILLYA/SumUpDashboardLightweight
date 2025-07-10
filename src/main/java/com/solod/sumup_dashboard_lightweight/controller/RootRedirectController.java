package com.solod.sumup_dashboard_lightweight.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class RootRedirectController {
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }
}
