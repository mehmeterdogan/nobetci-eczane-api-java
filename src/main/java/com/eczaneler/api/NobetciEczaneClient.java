package com.eczaneler.api;

import com.eczaneler.api.exceptions.*;
import com.eczaneler.api.models.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Nöbetçi Eczane REST API resmi Java ve Android istemcisi.
 */
public class NobetciEczaneClient {
    private static final String DEFAULT_BASE_URL = "https://api.eczaneler.org/v2";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    private final String apiKey;
    private final String baseUrl;
    private final OkHttpClient httpClient;
    private final Gson gson;

    /**
     * Yeni bir NobetciEczaneClient örneği başlatır.
     *
     * @param apiKey Eczaneler.org API anahtarınız.
     */
    public NobetciEczaneClient(String apiKey) {
        this(apiKey, DEFAULT_BASE_URL, null);
    }

    /**
     * Özel taban URL veya OkHttpClient ile başlatır.
     *
     * @param apiKey     Eczaneler.org API anahtarınız.
     * @param baseUrl    Özel taban URL adresi.
     * @param httpClient Özel OkHttpClient örneği.
     */
    public NobetciEczaneClient(String apiKey, String baseUrl, OkHttpClient httpClient) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API anahtarı boş bırakılamaz.");
        }
        this.apiKey = apiKey.trim();
        this.baseUrl = (baseUrl != null ? baseUrl : DEFAULT_BASE_URL).replaceAll("/+$", "");
        this.gson = new Gson();

        if (httpClient != null) {
            this.httpClient = httpClient;
        } else {
            this.httpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
    }

    /**
     * Tüm nöbetçi eczaneleri sayfalı olarak getirir.
     */
    public ApiResponse<List<Pharmacy>> getSentryPharmacies(int page, int limit) {
        int clampedPage = Math.max(page, 1);
        int clampedLimit = Math.min(Math.max(limit, 1), 50);
        String endpoint = "/pharmacies/sentry-pharmacies?page=" + clampedPage + "&limit=" + clampedLimit;
        Type type = new TypeToken<ApiResponse<List<Pharmacy>>>() {}.getType();
        return sendGetRequest(endpoint, type);
    }

    public ApiResponse<List<Pharmacy>> getSentryPharmacies() {
        return getSentryPharmacies(1, 25);
    }

    /**
     * Belirli bir ile ait nöbetçi eczaneleri getirir (Örn: 34 - İstanbul).
     */
    public ApiResponse<List<Pharmacy>> getSentryByCity(int cityId, int limit) {
        if (cityId <= 0) {
            throw new ValidationException("Geçersiz il ID numarası.");
        }
        int clampedLimit = Math.min(Math.max(limit, 1), 50);
        String endpoint = "/pharmacies/sentry-city-list/" + cityId + "?limit=" + clampedLimit;
        Type type = new TypeToken<ApiResponse<List<Pharmacy>>>() {}.getType();
        return sendGetRequest(endpoint, type);
    }

    public ApiResponse<List<Pharmacy>> getSentryByCity(int cityId) {
        return getSentryByCity(cityId, 50);
    }

    /**
     * Belirli bir il ve ilçeye ait nöbetçi eczaneleri getirir (Örn: 34 / 440 - Kadıköy).
     */
    public ApiResponse<List<Pharmacy>> getSentryByDistrict(int cityId, int districtId, int limit) {
        if (cityId <= 0 || districtId <= 0) {
            throw new ValidationException("Geçersiz il veya ilçe ID numarası.");
        }
        int clampedLimit = Math.min(Math.max(limit, 1), 50);
        String endpoint = "/pharmacies/sentry-district-list/" + cityId + "/" + districtId + "?limit=" + clampedLimit;
        Type type = new TypeToken<ApiResponse<List<Pharmacy>>>() {}.getType();
        return sendGetRequest(endpoint, type);
    }

    public ApiResponse<List<Pharmacy>> getSentryByDistrict(int cityId, int districtId) {
        return getSentryByDistrict(cityId, districtId, 50);
    }

    /**
     * GPS koordinatlarına göre en yakın eczaneleri getirir.
     */
    public ApiResponse<List<Pharmacy>> getNearbyPharmacies(double latitude, double longitude, boolean isSentry, int limit) {
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new ValidationException("Geçersiz enlem veya boylam koordinatları.");
        }
        int clampedLimit = Math.min(Math.max(limit, 1), 50);

        Map<String, Object> body = new HashMap<>();
        body.put("lat", latitude);
        body.put("lon", longitude);
        body.put("is_sentry", isSentry ? 1 : 0);
        body.put("limit", clampedLimit);

        Type type = new TypeToken<ApiResponse<List<Pharmacy>>>() {}.getType();
        return sendPostRequest("/pharmacies/pharmacies-nearby", body, type);
    }

    public ApiResponse<List<Pharmacy>> getNearbyPharmacies(double latitude, double longitude) {
        return getNearbyPharmacies(latitude, longitude, true, 10);
    }

    /**
     * 81 il ve KKTC listesini getirir.
     */
    public ApiResponse<List<City>> getCities() {
        Type type = new TypeToken<ApiResponse<List<City>>>() {}.getType();
        return sendGetRequest("/pharmacies/cities", type);
    }

    /**
     * Bir ile ait resmi ilçe listesini getirir.
     */
    public ApiResponse<List<District>> getDistricts(int cityId) {
        if (cityId <= 0) {
            throw new ValidationException("Geçersiz il ID numarası.");
        }
        Type type = new TypeToken<ApiResponse<List<District>>>() {}.getType();
        return sendGetRequest("/pharmacies/districts/" + cityId, type);
    }

    /**
     * Hesap ve kota bilgilerini getirir.
     */
    public ApiResponse<AccountInfo> getAccountInfo() {
        Type type = new TypeToken<ApiResponse<AccountInfo>>() {}.getType();
        return sendGetRequest("/pharmacies/api-account-info", type);
    }

    /**
     * İzinli IP adresleri listesini (Whitelist) günceller.
     */
    public ApiResponse<Object> updateWhitelist(String ips) {
        if (ips == null || ips.trim().isEmpty()) {
            throw new ValidationException("IP adres listesi boş bırakılamaz.");
        }
        Map<String, String> body = new HashMap<>();
        body.put("ips", ips.trim());

        Type type = new TypeToken<ApiResponse<Object>>() {}.getType();
        return sendPostRequest("/update-whitelist", body, type);
    }

    /**
     * [Premium] Türkiye'deki tüm eczaneleri getirir.
     */
    public ApiResponse<List<Pharmacy>> getAllPharmacies(int page, int limit) {
        int clampedPage = Math.max(page, 1);
        int clampedLimit = Math.min(Math.max(limit, 1), 50);
        String endpoint = "/pharmacies/all-pharmacies?page=" + clampedPage + "&limit=" + clampedLimit;
        Type type = new TypeToken<ApiResponse<List<Pharmacy>>>() {}.getType();
        return sendGetRequest(endpoint, type);
    }

    /**
     * [Premium] Bir ildeki tüm eczaneleri getirir.
     */
    public ApiResponse<List<Pharmacy>> getCityPharmacies(int cityId, int limit) {
        if (cityId <= 0) {
            throw new ValidationException("Geçersiz il ID numarası.");
        }
        int clampedLimit = Math.min(Math.max(limit, 1), 50);
        String endpoint = "/pharmacies/city-pharmacies/" + cityId + "?limit=" + clampedLimit;
        Type type = new TypeToken<ApiResponse<List<Pharmacy>>>() {}.getType();
        return sendGetRequest(endpoint, type);
    }

    /**
     * [Premium] Bir ilçedeki tüm eczaneleri getirir.
     */
    public ApiResponse<List<Pharmacy>> getDistrictPharmacies(int cityId, int districtId, int limit) {
        if (cityId <= 0 || districtId <= 0) {
            throw new ValidationException("Geçersiz il veya ilçe ID numarası.");
        }
        int clampedLimit = Math.min(Math.max(limit, 1), 50);
        String endpoint = "/pharmacies/district-pharmacies/" + cityId + "/" + districtId + "?limit=" + clampedLimit;
        Type type = new TypeToken<ApiResponse<List<Pharmacy>>>() {}.getType();
        return sendGetRequest(endpoint, type);
    }

    private <T> T sendGetRequest(String endpoint, Type responseType) {
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .addHeader("Accept", "application/json")
                .addHeader("X-Api-Key", apiKey)
                .get()
                .build();

        return executeRequest(request, responseType);
    }

    private <T> T sendPostRequest(String endpoint, Object bodyObject, Type responseType) {
        String jsonBody = gson.toJson(bodyObject);
        RequestBody body = RequestBody.create(jsonBody, JSON_MEDIA_TYPE);

        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Api-Key", apiKey)
                .post(body)
                .build();

        return executeRequest(request, responseType);
    }

    private <T> T executeRequest(Request request, Type responseType) {
        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";

            if (!response.isSuccessful()) {
                handleError(response.code(), responseBody);
            }

            return gson.fromJson(responseBody, responseType);
        } catch (IOException e) {
            throw new NobetciEczaneApiException("HTTP isteği gerçekleştirilemedi: " + e.getMessage(), 0, e);
        }
    }

    private void handleError(int statusCode, String responseBody) {
        String message = "API isteği başarısız oldu (HTTP " + statusCode + ")";
        try {
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            if (jsonObject.has("message")) {
                message = jsonObject.get("message").getAsString();
            }
        } catch (Exception ignored) {
        }

        switch (statusCode) {
            case 400:
                throw new ValidationException(message);
            case 401:
                throw new AuthenticationException(message);
            case 403:
                throw new ForbiddenException(message);
            case 404:
                throw new NotFoundException(message);
            case 429:
                throw new RateLimitException(message);
            default:
                throw new NobetciEczaneApiException(message, statusCode);
        }
    }
}
