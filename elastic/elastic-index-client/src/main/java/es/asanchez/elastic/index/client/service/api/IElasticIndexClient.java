package es.asanchez.elastic.index.client.service.api;

import es.asanchez.elastic.model.index.api.IndexModel;

import java.util.List;

public interface IElasticIndexClient<T extends IndexModel> {

    List<String> save(List<T> documents);

}
