package es.asanchez.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("elastic-query-service")
public class ElasticQueryServiceConfigData {

    private String version;
}
