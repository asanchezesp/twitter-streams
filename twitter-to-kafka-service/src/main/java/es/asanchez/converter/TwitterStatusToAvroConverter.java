package es.asanchez.converter;

import es.asanchez.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;
import twitter4j.v1.Status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class TwitterStatusToAvroConverter {

    public TwitterAvroModel getTwitterAvroModelFromStatus(Status status){
        LocalDateTime created = status.getCreatedAt().toLocalDate().atStartOfDay();
        return TwitterAvroModel.newBuilder()
                .setId(status.getId())
                .setUserId(status.getUser().getId())
                .setText(status.getText())
                .setCreatedAt(Timestamp.valueOf(created).getTime())
                .build();
    }
}
