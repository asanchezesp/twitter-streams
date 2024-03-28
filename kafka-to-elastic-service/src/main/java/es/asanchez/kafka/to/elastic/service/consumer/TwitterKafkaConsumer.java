package es.asanchez.kafka.to.elastic.service.consumer;

import es.asanchez.app.config.KafkaConfigData;
import es.asanchez.app.config.KafkaConsumerConfigData;
import es.asanchez.elastic.index.client.service.api.IElasticIndexClient;
import es.asanchez.elastic.model.index.TwitterIndexModel;
import es.asanchez.kafka.admin.client.KafkaAdminClient;
import es.asanchez.kafka.avro.model.TwitterAvroModel;
import es.asanchez.kafka.to.elastic.service.consumer.api.KafkaConsumer;
import es.asanchez.kafka.to.elastic.service.converter.AvroToElasticModelConverter;
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
    private final KafkaConsumerConfigData kafkaConsumerConfigData;
    private final AvroToElasticModelConverter converter;
    private final IElasticIndexClient<TwitterIndexModel> elasticIndexClient;

    @EventListener
    public void onAppStarted(ApplicationStartedEvent event){
        kafkaAdminClient.checkTopicsCreated();
        LOG.info("Topics with name {} are ready", kafkaConfigData.getTopicNamesToCreate().toArray());
        MessageListenerContainer container = kafkaListenerEndpointRegistry.getListenerContainer(kafkaConsumerConfigData.getConsumerGroupId());
        if(container != null){
            container.start();
            LOG.info("Listener {} has been started", kafkaConsumerConfigData.getConsumerGroupId());
        }
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}", topics = "${kafka-config.topic-name}")
    public void recieve(@Payload List<TwitterAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Integer> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOG.info("{} number of meesagges received with keys {}, partitions {} and offsets {}, sending it to elastic: Thread id {}",
                messages.size(),keys.toString(),partitions.toString(),offsets.toString(),Thread.currentThread().threadId());
        List<String> documentIds = elasticIndexClient.save(converter.getElasticModels(messages));
        LOG.info("Documents saved to elasticsearch with ids {}",documentIds.toArray());
    }
}
