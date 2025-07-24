package com.solod.sumup_dashboard_lightweight.controller;

import com.solod.sumup_dashboard_lightweight.config.SumUpConfig;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/sumup")
public class SumUpAuthController {

    private final SumUpConfig constants;
    private final RestTemplate restTemplate = new RestTemplate();

    public SumUpAuthController(SumUpConfig constants) {
        this.constants = constants;
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        String sumupAuthUrl = "https://api.sumup.com/authorize?" +
                "response_type=code" +
                "&client_id=" + constants.getClientId() +
                "&redirect_uri=" + URLEncoder.encode(constants.getRedirectUri(), StandardCharsets.UTF_8) +
                "&scope="
                + URLEncoder.encode("user.app-settings transactions.history user.profile", StandardCharsets.UTF_8);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(sumupAuthUrl))
                .build();
    }

    @GetMapping("/callback")
    public ResponseEntity<?> handleCallback(@RequestParam("code") String code) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "authorization_code");
        data.add("code", code);
        data.add("client_id", constants.getClientId());
        data.add("client_secret", constants.getClientSecret());
        data.add("redirect_uri", constants.getRedirectUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.sumup.com/token", request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String accessToken = (String) response.getBody().get("access_token");
            String redirectUrl = "http://localhost:8080/sumup/dashboard?token=" + accessToken;
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }
}
