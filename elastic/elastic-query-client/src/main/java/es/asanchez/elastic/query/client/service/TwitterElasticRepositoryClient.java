package es.asanchez.elastic.query.client.service;

import es.asanchez.common.util.CollectionsUtil;
import es.asanchez.elastic.query.client.repository.TwitterElasticsearchQueryRepository;
import es.asanchez.elastic.query.client.service.api.IElasticQueryClient;
import es.asanchez.elastic.model.index.TwitterIndexModel;
import es.asanchez.elastic.query.client.exception.ElasticQueryClientException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class TwitterElasticRepositoryClient implements IElasticQueryClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticRepositoryClient.class);

    private final TwitterElasticsearchQueryRepository repository;

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        return repository.findById(id).orElseThrow(() -> new ElasticQueryClientException(String.format("Document with id %s on index not found", id)));
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        List<TwitterIndexModel> result = repository.findByText(text);
        LOG.info("{} documents with text {} retrieved", result.size(), text);
        return result;
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        List<TwitterIndexModel> result = CollectionsUtil.getInstance().getListFromIterable(repository.findAll());
        LOG.info("{} documents retrieved", result.size());
        return result;
    }
}
