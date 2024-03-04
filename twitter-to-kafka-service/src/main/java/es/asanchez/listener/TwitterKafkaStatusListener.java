package es.asanchez.listener;

import es.asanchez.app.config.KafkaConfigData;
import es.asanchez.converter.TwitterStatusToAvroConverter;
import es.asanchez.kafka.avro.model.TwitterAvroModel;
import es.asanchez.kafka.producer.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.v1.Status;
import twitter4j.v1.StatusAdapter;

@Component
@RequiredArgsConstructor
public class TwitterKafkaStatusListener extends StatusAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer kafkaProducer;
    private final TwitterStatusToAvroConverter converter;
    @Override
    public void onStatus(Status status) {
        LOG.info("Received status text {} sending to kafka topic {}",status.getText(),kafkaConfigData.getTopicName());
        TwitterAvroModel twitterAvroModel = converter.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getId(),twitterAvroModel);
    }
}
