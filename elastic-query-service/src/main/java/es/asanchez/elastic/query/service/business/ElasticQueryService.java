package es.asanchez.elastic.query.service.business;

import es.asanchez.elastic.query.service.model.ElasticQueryServiceResponseModel;

import java.util.List;

public interface ElasticQueryService {

    ElasticQueryServiceResponseModel getDocumentById(String id);
    List<ElasticQueryServiceResponseModel> getDocumentByText(String text);
    List<ElasticQueryServiceResponseModel> getAllDocuments();

}
