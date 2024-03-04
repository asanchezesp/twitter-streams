package es.asanchez.kafka.producer.service;


import es.asanchez.kafka.avro.model.TwitterAvroModel;
import es.asanchez.kafka.producer.service.api.IKafkaProducer;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaProducer implements IKafkaProducer<Long, TwitterAvroModel> {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<Long,TwitterAvroModel> kafkaTemplate;

    @Override
    public void send(String topic, Long key, TwitterAvroModel message) {
        LOG.info("Sending message={} to topic={}",message,topic);
        CompletableFuture<SendResult<Long,TwitterAvroModel>> kafkaResultFuture = kafkaTemplate.send(topic,key,message);

//        kafkaResultFuture
//                .thenAccept(this::onSuccessFuture)
//                .exceptionally(throwable -> {
//                    onErrorFuture(topic, message, throwable);
//                    return null;
//                });
    }

    /**
     * No es necesario pero por si acaso
     */
    @PreDestroy
    public void close(){
        if(kafkaTemplate != null){
            LOG.info("Closing kafka producer");
            kafkaTemplate.destroy();
        }
    }

    private void onSuccessFuture(SendResult<Long,TwitterAvroModel> result){
        RecordMetadata metadata = result.getRecordMetadata();
        LOG.debug("Recieved new metadata. Topic: {}, Partition: {}, Offset: {}, Timestamp: {} at time {}",
                metadata.topic(),
                metadata.partition(),
                metadata.offset(),
                metadata.timestamp(),
                System.nanoTime());
    }

    private void onErrorFuture(String topic, TwitterAvroModel message,Throwable throwable){
         LOG.error("Error while sending message {} to topic {}",message.toString(),topic,throwable);
    }
}
