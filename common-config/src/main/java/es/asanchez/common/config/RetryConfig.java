package es.asanchez.common.config;

import es.asanchez.app.config.RetryConfigData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
public class RetryConfig {

    private final RetryConfigData retryConfigData;

    @Bean
    public RetryTemplate retryTemplate(){
        RetryTemplate rt = new RetryTemplate();
        ExponentialBackOffPolicy ebop = new ExponentialBackOffPolicy();
        SimpleRetryPolicy srp = new SimpleRetryPolicy();

        ebop.setInitialInterval(retryConfigData.getInitialIntervalMs());
        ebop.setMaxInterval(retryConfigData.getMaxIntervalMs());
        ebop.setMultiplier(retryConfigData.getMultiplier());
        srp.setMaxAttempts(retryConfigData.getMaxAttempts());

        rt.setBackOffPolicy(ebop);
        rt.setRetryPolicy(srp);

        return rt;
    }
}
