# 🏥 nobetci-eczane-api-java (Java & Android & Kotlin SDK)

Türkiye ve KKTC genelindeki 81 ilin nöbetçi eczane verilerine, il/ilçe sorgularına ve GPS koordinat bazlı en yakın eczane lokasyonlarına programatik erişim sağlayan resmi **Java, Android ve Kotlin SDK** kütüphanesidir.

[![JitPack](https://jitpack.io/v/mehmeterdogan/nobetci-eczane-api-java.svg)](https://jitpack.io/#mehmeterdogan/nobetci-eczane-api-java)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![Java 8+](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/)
[![Android Compatible](https://img.shields.io/badge/Android-5.0%2B%20%28API%2021%2B%29-green.svg)](https://developer.android.com)

---

## 📦 Kurulum (Installation)

### 1. Gradle (Android / Kotlin / Spring Boot)

`settings.gradle` veya kök `build.gradle` dosyanıza JitPack repository'sini ekleyin:

```groovy
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Ardından `app/build.gradle` dosyanıza bağımlılığı ekleyin:

```groovy
dependencies {
    implementation 'com.github.mehmeterdogan:nobetci-eczane-api-java:1.0.0'
}
```

### 2. Maven (`pom.xml`)

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.mehmeterdogan</groupId>
        <artifactId>nobetci-eczane-api-java</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

---

## 🚀 Hızlı Başlangıç (Quick Start)

### Java
```java
import com.eczaneler.api.NobetciEczaneClient;
import com.eczaneler.api.models.ApiResponse;
import com.eczaneler.api.models.Pharmacy;
import com.eczaneler.api.exceptions.NobetciEczaneApiException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. İstemciyi API anahtarınız ile başlatın
        NobetciEczaneClient client = new NobetciEczaneClient("SENIN_API_ANAHTARIN");

        try {
            // 2. İstanbul (34) nöbetçi eczanelerini çekin
            ApiResponse<List<Pharmacy>> response = client.getSentryByCity(34);
            
            System.out.println("Durum: " + response.getStatus());
            for (Pharmacy p : response.getData()) {
                System.out.println("💊 " + p.getName() + " (" + p.getDistrict() + " / " + p.getLocality() + ")");
                System.out.println("   📞 Tel: " + p.getPhone());
                System.out.println("   📍 Adres: " + p.getAddress());
            }

            // 3. Konum bazlı en yakın nöbetçi eczaneler (Kadıköy Moda)
            ApiResponse<List<Pharmacy>> nearby = client.getNearbyPharmacies(40.9876, 28.9784);
            for (Pharmacy p : nearby.getData()) {
                System.out.println("- " + p.getName() + " (Mesafe: " + p.getDistanceKm() + " km)");
            }

        } catch (NobetciEczaneApiException e) {
            System.err.println("API Hatası (" + e.getStatusCode() + "): " + e.getMessage());
        }
    }
}
```

### Kotlin (Android)
```kotlin
val client = NobetciEczaneClient("SENIN_API_ANAHTARIN")

// Coroutine veya Arka Plan Thread içinde:
val response = client.getSentryByCity(34)
response.data?.forEach { pharmacy ->
    Log.d("Eczane", "${pharmacy.name}: ${pharmacy.phone}")
}
```

---

## 💻 Tüm Metodlar

- `getSentryPharmacies(int page, int limit)`: Genel nöbetçi eczaneler listesi.
- `getSentryByCity(int cityId, int limit)`: İl bazlı nöbetçi eczaneler.
- `getSentryByDistrict(int cityId, int districtId, int limit)`: İlçe bazlı nöbetçi eczaneler.
- `getNearbyPharmacies(double lat, double lon, boolean isSentry, int limit)`: En yakın eczaneler.
- `getCities()`: 81 il ve KKTC listesi.
- `getDistricts(int cityId)`: İle ait ilçeler listesi.
- `getAccountInfo()`: Lisans ve kalan gün/kota bilgisi.
- `updateWhitelist(String ips)`: IP Whitelist güncelleme.

---

## 🛡️ Hata Yönetimi

- `AuthenticationException`: 401 Yetkisiz erişim (API anahtarı eksik/geçersiz).
- `ForbiddenException`: 403 Erişim engeli (IP Whitelist eşleşmedi).
- `NotFoundException`: 404 Kayıt bulunamadı.
- `RateLimitException`: 429 Aşırı istek sınırı.
- `ValidationException`: 400 Geçersiz parametre.

---

## 📄 Lisans & İletişim

- **Lisans**: MIT
- **Geliştirici**: [Mehmet Erdoğan](https://github.com/mehmeterdogan)
- **E-Posta**: [mehmeterdogan080@gmail.com](mailto:mehmeterdogan080@gmail.com)
- **Telegram**: [@mehmeterdogannet](https://t.me/mehmeterdogannet)
- **Web Dokümantasyonu**: [https://eczaneler.org/nobetci-eczane-api](https://eczaneler.org/nobetci-eczane-api)
