package es.asanchez.kafka.admin.client;

import es.asanchez.app.config.KafkaConfigData;
import es.asanchez.app.config.RetryConfigData;
import es.asanchez.kafka.admin.exception.KafkaClientException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class KafkaAdminClient {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaAdminClient.class);

    private final WebClient webClient;

    private final KafkaConfigData kafkaConfigData;
    private final RetryConfigData retryConfigData;
    private final AdminClient adminClient;
    private final RetryTemplate retryTemplate;

    public void createTopics() {
        try {
            CreateTopicsResult result = retryTemplate.execute(this::doCreateTopics);
        } catch (Throwable t) {
            throw new RuntimeException("Reached max number of retry for creating kafka topic(s)", t);
        }
        checkTopicsCreated();
    }

    public void checkSchemaRegistry() {
        int retryCount = 1;
        int maxRetry = retryConfigData.getMaxAttempts();
        int multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        while (!getSchemaRegistryStatus().is2xxSuccessful()) {
            checkMaxRetry(retryCount++, maxRetry);
            sleep(sleepTimeMs);
            sleepTimeMs += multiplier;
        }
    }

    public void checkTopicsCreated() {
        Collection<TopicListing> topics = getTopics();
        int retryCount = 1;
        int maxRetry = retryConfigData.getMaxAttempts();
        int multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        for (String topic : kafkaConfigData.getTopicNamesToCreate()) {
            while (!isTopicCreated(topics, topic)) {
                checkMaxRetry(retryCount++, maxRetry);
                sleep(sleepTimeMs);
                sleepTimeMs += multiplier;
                topics = getTopics();
            }
        }
    }

    private HttpStatusCode getSchemaRegistryStatus() {
        try {
            return webClient
                    .get()
                    .uri(kafkaConfigData.getSchemaRegistryUrl())
                    .exchangeToMono(Mono::just)
                    .map(ClientResponse::statusCode).block();
        } catch (Exception e) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }

    private void sleep(Long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Reached max number of retry for reade kafka topic(s)");
        }
    }

    private void checkMaxRetry(int retry, Integer maxRetry) {
        if (retry > maxRetry) {
            throw new KafkaClientException("Reached max number of retry for reade kafka topic(s)");
        }
    }

    private boolean isTopicCreated(Collection<TopicListing> topics, String topic) {
        if (CollectionUtils.isEmpty(topics))
            return false;
        return topics.stream().anyMatch(t -> t.name().equals(topic));
    }

    private CreateTopicsResult doCreateTopics(RetryContext retryContext) {
        List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
        LOG.info("Creating {} topic(s), attempt {}", topicNames.size(), retryContext.getRetryCount());
        List<NewTopic> kafkaTopics = topicNames.stream().map(topic -> new NewTopic(
                topic.trim(),
                kafkaConfigData.getNumOfPartitions(),
                kafkaConfigData.getReplicationFactor()
        )).toList();
        return adminClient.createTopics(kafkaTopics);

    }

    private Collection<TopicListing> getTopics() {
        try {
            return retryTemplate.execute(this::doGetTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for reading kafka topic(s)", t);
        }
    }

    private Collection<TopicListing> doGetTopics(RetryContext retryContext) throws ExecutionException, InterruptedException {
        LOG.info("Reading kafka topic {}, attempt {}", kafkaConfigData.getTopicNamesToCreate(), retryContext.getRetryCount());
        Collection<TopicListing> topicListings = adminClient.listTopics().listings().get();
        if (!CollectionUtils.isEmpty(topicListings)) {
            topicListings.forEach(topic -> LOG.debug("Topic with name {}", topic.name()));
        }
        return topicListings;
    }

}
