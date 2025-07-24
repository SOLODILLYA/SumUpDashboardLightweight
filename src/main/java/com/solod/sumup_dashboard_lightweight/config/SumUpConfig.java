package com.solod.sumup_dashboard_lightweight.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SumUpConfig {

    @Value("${sumup.client.id}")
    private String clientId;

    @Value("${sumup.client.secret}")
    private String clientSecret;

    @Value("${sumup.redirect.uri}")
    private String redirectUri;

    @Value("${sumup.code}")
    private String code;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getCode() {
        return code;
    }
}