package es.asanchez.query.client.service.api;

import es.asanchez.elastic.model.index.api.IndexModel;

import java.util.List;

public interface IElasticQueryClient<T extends IndexModel> {

    T getIndexModelById(String id);

    List<T> getIndexModelByText(String text);

    List<T> getAllIndexModels();

}
