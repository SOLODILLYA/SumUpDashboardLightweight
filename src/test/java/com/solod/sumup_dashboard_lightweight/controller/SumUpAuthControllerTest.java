package com.solod.sumup_dashboard_lightweight.controller;

import com.solod.sumup_dashboard_lightweight.config.SumUpConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SumUpAuthControllerTest {

    @Mock
    private SumUpConfig config;

    @Mock
    private RestTemplate mockRestTemplate;

    private SumUpAuthController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new SumUpAuthController(config);
        ReflectionTestUtils.setField(controller, "restTemplate", mockRestTemplate);
    }

    @Test
    void loginRedirectsToSumUp() {
        when(config.getClientId()).thenReturn("id123");
        when(config.getRedirectUri()).thenReturn("http://localhost/callback");

        ResponseEntity<Void> response = controller.login();

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        URI location = response.getHeaders().getLocation();
        String url = location.toString();
        assertTrue(url.startsWith("https://api.sumup.com/authorize?"));
        assertTrue(url.contains("client_id=id123"));
        String encodedRedirect = URLEncoder.encode("http://localhost/callback", StandardCharsets.UTF_8);
        assertTrue(url.contains("redirect_uri=" + encodedRedirect));
        String expectedScope = URLEncoder.encode(
                "user.app-settings transactions.history user.profile", StandardCharsets.UTF_8);
        assertTrue(url.contains("scope=" + expectedScope));
    }

    @Test
    void handleCallbackSuccess() {
        when(config.getClientId()).thenReturn("id123");
        when(config.getClientSecret()).thenReturn("sec456");
        when(config.getRedirectUri()).thenReturn("http://localhost/callback");

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("access_token", "tok789");
        ResponseEntity<Map> fakeResponse = new ResponseEntity<>(tokens, HttpStatus.OK);

        when(mockRestTemplate.postForEntity(
                eq("https://api.sumup.com/token"),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(fakeResponse);

        ResponseEntity<?> response = controller.handleCallback("codeXYZ");

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        URI location = response.getHeaders().getLocation();
        assertEquals(
                "http://localhost:8080/sumup/dashboard?token=tok789",
                location.toString());
    }

    @Test
    void handleCallbackError() {
        when(config.getClientId()).thenReturn("id123");
        when(config.getClientSecret()).thenReturn("sec456");
        when(config.getRedirectUri()).thenReturn("http://localhost/callback");

        Map<String, Object> errorBody = Map.of("error", "invalid_grant");
        ResponseEntity<Map> fakeError = new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);

        when(mockRestTemplate.postForEntity(
                eq("https://api.sumup.com/token"),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(fakeError);

        ResponseEntity<?> response = controller.handleCallback("badcode");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorBody, response.getBody());
    }
}
