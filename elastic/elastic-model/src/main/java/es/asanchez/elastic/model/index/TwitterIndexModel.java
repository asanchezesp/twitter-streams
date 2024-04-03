package es.asanchez.elastic.model.index;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import es.asanchez.elastic.model.index.api.IndexModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Builder
//TODO: Ver si se puede cambiar por el valor dinamico de elasticConfigData.indexName
@Document(indexName = "twitter-index")
public class TwitterIndexModel implements IndexModel {

    @Id
    private String id;
    private Long userId;
    private String text;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    @JsonProperty
    private LocalDateTime createdAt;


}
