package com.eczaneler.api;

import com.eczaneler.api.exceptions.AuthenticationException;
import com.eczaneler.api.exceptions.ValidationException;
import com.eczaneler.api.models.ApiResponse;
import com.eczaneler.api.models.Pharmacy;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NobetciEczaneClientTest {
    private MockWebServer server;
    private NobetciEczaneClient client;

    @BeforeEach
    public void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        client = new NobetciEczaneClient("test_api_key", server.url("/").toString(), null);
    }

    @AfterEach
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testGetSentryByCity() throws InterruptedException {
        String mockBody = "{\n" +
                "  \"status\": \"success\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": 101,\n" +
                "      \"city_id\": 34,\n" +
                "      \"district_id\": 440,\n" +
                "      \"name\": \"Kadıköy Eczanesi\",\n" +
                "      \"city\": \"İstanbul\",\n" +
                "      \"district\": \"Kadıköy\",\n" +
                "      \"address\": \"Moda Cad. No:1\",\n" +
                "      \"is_sentry\": true\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(mockBody));

        ApiResponse<List<Pharmacy>> response = client.getSentryByCity(34);

        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals("Kadıköy Eczanesi", response.getData().get(0).getName());

        RecordedRequest request = server.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("test_api_key", request.getHeader("X-Api-Key"));
        assertTrue(request.getPath().contains("/pharmacies/sentry-city-list/34"));
    }

    @Test
    public void testAuthenticationExceptionOn401() {
        server.enqueue(new MockResponse()
                .setResponseCode(401)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"status\": \"error\", \"message\": \"Geçersiz API Anahtarı\"}"));

        assertThrows(AuthenticationException.class, () -> client.getSentryByCity(34));
    }

    @Test
    public void testValidationExceptionOnInvalidCoordinates() {
        assertThrows(ValidationException.class, () -> client.getNearbyPharmacies(999.0, 29.0));
    }
}
