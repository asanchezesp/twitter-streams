package es.asanchez.kafka.to.elastic.service.converter;

import es.asanchez.elastic.model.index.TwitterIndexModel;
import es.asanchez.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class AvroToElasticModelConverter {

    public List<TwitterIndexModel> getElasticModels(List<TwitterAvroModel> avroModels) {
        return avroModels.stream()
                .map(m ->
                        TwitterIndexModel.builder().userId(m.getUserId())
                                .id(String.valueOf(m.getId()))
                                .text(m.getText())
                                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(m.getCreatedAt()), ZoneId.systemDefault()))
                                .build()
                ).toList();
    }
}
