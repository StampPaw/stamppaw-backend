package org.example.stamppaw_backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "toss.payments")
@Data
public class TossPaymentConfig {
    private String clientKey;
    private String secretKey;
    private Api api = new Api(); // private Api api;

    @Data
    public static  class Api {
        private String baseUrl;
    }
}
