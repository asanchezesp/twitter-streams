package es.asanchez.elastic.query.web.client.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElasticWebClientRequestModel {

    private String id;
    @NotEmpty
    private String text;

}

