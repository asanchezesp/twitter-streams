package es.asanchez.elastic.index.client.service;

import es.asanchez.app.config.ElasticConfigData;
import es.asanchez.elastic.index.client.service.api.IElasticIndexClient;
import es.asanchez.elastic.index.client.util.ElasticIndexUtil;
import es.asanchez.elastic.model.index.TwitterIndexModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwitterElasticIndexClient implements IElasticIndexClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticIndexClient.class);

    private final ElasticConfigData elasticConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<IndexQuery> indexQueries = elasticIndexUtil.getIndexQueries(documents);
        List<IndexedObjectInformation> documentInformation = elasticsearchOperations.bulkIndex(indexQueries,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        List<String> documentIds = documentInformation.stream().map(IndexedObjectInformation::id).toList();
        LOG.info("Documents indexed successfully with type: {} nad ids: {}", TwitterIndexModel.class.getName(), documentIds);

        return documentIds;
    }
}
