package es.asanchez.elastic.query.service.business.impl;

import es.asanchez.elastic.model.index.TwitterIndexModel;
import es.asanchez.elastic.query.service.business.ElasticQueryService;
import es.asanchez.elastic.query.service.converter.ElasticToResponseModelConverter;
import es.asanchez.elastic.query.service.model.ElasticQueryServiceResponseModel;
import es.asanchez.elastic.query.client.service.api.IElasticQueryClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwitterElasticQueryService implements ElasticQueryService {

    private final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryService.class);

    private final ElasticToResponseModelConverter converter;
    private final IElasticQueryClient<TwitterIndexModel> elasticQueryClient;

    @Override
    public ElasticQueryServiceResponseModel getDocumentById(String id) {
        return converter.getResponseModel(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        return converter.getResponseModel(elasticQueryClient.getIndexModelByText(text));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getAllDocuments() {
        return converter.getResponseModel(elasticQueryClient.getAllIndexModels());
    }
}
