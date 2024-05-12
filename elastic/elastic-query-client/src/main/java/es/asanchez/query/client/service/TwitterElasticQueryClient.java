package es.asanchez.query.client.service;

import es.asanchez.app.config.ElasticConfigData;
import es.asanchez.app.config.ElasticQueryConfigData;
import es.asanchez.elastic.model.index.TwitterIndexModel;
import es.asanchez.query.client.exception.ElasticQueryClientException;
import es.asanchez.query.client.util.ElasticQueryUtil;
import es.asanchez.query.client.service.api.IElasticQueryClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwitterElasticQueryClient implements IElasticQueryClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryClient.class);

    private final ElasticConfigData elasticConfigData;
    private final ElasticQueryConfigData elasticQueryConfigData;

    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil;

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<TwitterIndexModel> searchResult =  elasticsearchOperations.searchOne(query,TwitterIndexModel.class, IndexCoordinates.of(elasticConfigData.getIndexName()));
        if(searchResult == null) {
            throw new ElasticQueryClientException(String.format("Document with id %s on index %s not found",id,elasticConfigData.getIndexName()));
        }
        LOG.info("Document with id {} on index {} retrieved",searchResult.getId(),searchResult.getIndex());
        return searchResult.getContent();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        return List.of();
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        return List.of();
    }
}
