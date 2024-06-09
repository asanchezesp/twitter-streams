package es.asanchez.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("elastic-query-web-client")
public class ElasticQueryWebClientConfigData {

    private WebClient webClient;

    @Data
    public static class WebClient {
        private Integer connectTimeout;
        private Integer readTimeout;
        private Integer writeTimeout;
        private Integer maxInMemorySixze;
        private String contentType;
        private String acceptType;
        private String baseUrl;
    }
}
