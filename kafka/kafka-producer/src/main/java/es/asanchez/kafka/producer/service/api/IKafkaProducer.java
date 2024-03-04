package es.asanchez.kafka.producer.service.api;


import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;

public interface IKafkaProducer<T extends Serializable, R extends SpecificRecordBase> {

    void send(String topic,T key, R message);
}
