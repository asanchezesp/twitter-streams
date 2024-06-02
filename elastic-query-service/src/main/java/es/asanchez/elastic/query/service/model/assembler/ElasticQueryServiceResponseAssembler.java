package es.asanchez.elastic.query.service.model.assembler;

import es.asanchez.elastic.model.index.TwitterIndexModel;
import es.asanchez.elastic.query.service.api.ElasticDocumentController;
import es.asanchez.elastic.query.service.converter.ElasticToResponseModelConverter;
import es.asanchez.elastic.query.service.model.ElasticQueryServiceResponseModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ElasticQueryServiceResponseAssembler extends RepresentationModelAssemblerSupport<TwitterIndexModel, ElasticQueryServiceResponseModel> {

    private final ElasticToResponseModelConverter converter;

    public ElasticQueryServiceResponseAssembler(ElasticToResponseModelConverter converter) {
        super(ElasticDocumentController.class, ElasticQueryServiceResponseModel.class);
        this.converter = converter;
    }

    @Override
    public ElasticQueryServiceResponseModel toModel(TwitterIndexModel entity) {
        ElasticQueryServiceResponseModel model = converter.getResponseModel(entity);
        model.add(linkTo(methodOn(ElasticDocumentController.class).getDocumentById((model.getId()))).withSelfRel());
//        model.add(linkTo((ElasticQueryServiceResponseModel.class)).withRel("documents"));
        return model;
    }

    public List<ElasticQueryServiceResponseModel> toModel(List<TwitterIndexModel> entities){
        return entities.stream().map(this::toModel).toList();
    }
}
