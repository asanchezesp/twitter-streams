package es.asanchez.elastic.query.service.converter;

import es.asanchez.elastic.model.index.TwitterIndexModel;
import es.asanchez.elastic.query.service.model.ElasticQueryServiceResponseModel;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticToResponseModelConverter {

    public ElasticQueryServiceResponseModel getResponseModel(TwitterIndexModel indexModel){
        return ElasticQueryServiceResponseModel.builder()
                .id(indexModel.getId())
                .createdAt(indexModel.getCreatedAt())
                .text(indexModel.getText())
                .userId(indexModel.getUserId())
                .build();
    }

    public List<ElasticQueryServiceResponseModel> getResponseModel(List<TwitterIndexModel> indexModels){
        return indexModels.stream().map(this::getResponseModel).toList();
    }

}
