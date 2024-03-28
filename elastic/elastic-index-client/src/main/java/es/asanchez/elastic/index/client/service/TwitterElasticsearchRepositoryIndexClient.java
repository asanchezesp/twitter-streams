package es.asanchez.elastic.index.client.service;

import es.asanchez.elastic.index.client.repository.TwitterElasticsearchIndexRepository;
import es.asanchez.elastic.index.client.service.api.IElasticIndexClient;
import es.asanchez.elastic.model.index.TwitterIndexModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class TwitterElasticsearchRepositoryIndexClient implements IElasticIndexClient<TwitterIndexModel>  {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticsearchRepositoryIndexClient.class);

    private final TwitterElasticsearchIndexRepository twitterElasticsearchIndexRepository;

    public TwitterElasticsearchRepositoryIndexClient(TwitterElasticsearchIndexRepository twitterElasticsearchIndexRepository) {
        this.twitterElasticsearchIndexRepository = twitterElasticsearchIndexRepository;
    }

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<TwitterIndexModel> saved = (List<TwitterIndexModel>) twitterElasticsearchIndexRepository.saveAll(documents);
        List<String> documentIds = saved.stream().map(TwitterIndexModel::getId).toList();
        LOG.info("Documents indexed successfully with type: {} nad ids: {}", TwitterIndexModel.class.getName(), documentIds);
        return documentIds;
    }
}
