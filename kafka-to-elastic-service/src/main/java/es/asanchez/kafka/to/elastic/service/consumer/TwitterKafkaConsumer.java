package es.asanchez.kafka.to.elastic.service.consumer;

import es.asanchez.app.config.KafkaConfigData;
import es.asanchez.kafka.admin.client.KafkaAdminClient;
import es.asanchez.kafka.avro.model.TwitterAvroModel;
import es.asanchez.kafka.to.elastic.service.consumer.api.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaConsumer.class);

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaAdminClient kafkaAdminClient;
    private final KafkaConfigData kafkaConfigData;

    private static final String topicListener = "twitterTopicListener";

    @EventListener
    public void onAppStarted(ApplicationStartedEvent event){
        kafkaAdminClient.checkTopicsCreated();
        LOG.info("Topics with name {} are ready", kafkaConfigData.getTopicNamesToCreate().toArray());
        MessageListenerContainer container = kafkaListenerEndpointRegistry.getListenerContainer(topicListener);
        if(container != null){
            container.start();
            LOG.info("Listener {} has been started", topicListener);
        }
    }

    @Override
    @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
    public void recieve(@Payload List<TwitterAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Integer> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOG.info("{} number of meesagges received with keys {}, partitions {} and offsets {}, sending it to elastic: Thread id {}",
                messages.size(),keys.toString(),partitions.toString(),offsets.toString(),Thread.currentThread().threadId());
    }
}
