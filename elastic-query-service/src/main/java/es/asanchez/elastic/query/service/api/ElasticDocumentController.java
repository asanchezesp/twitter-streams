package es.asanchez.elastic.query.service.api;

import es.asanchez.elastic.query.service.business.ElasticQueryService;
import es.asanchez.elastic.query.service.model.ElasticQueryServiceRequestModel;
import es.asanchez.elastic.query.service.model.ElasticQueryServiceResponseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "documents")
@RequiredArgsConstructor
public class ElasticDocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);

    private final ElasticQueryService elasticQueryService;

    @GetMapping
    public ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments(){
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getAllDocuments();
        LOG.info("ElasticSearch returned {} documents", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable @NotEmpty String id){
        ElasticQueryServiceResponseModel response = elasticQueryService.getDocumentById(id);
        LOG.info("ElasticSearch returned document with id {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("get-document-by-text")
    public ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentByText(@RequestBody @Valid ElasticQueryServiceRequestModel request){
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getDocumentByText(request.getText());
        LOG.info("ElasticSearch returned {} documents", response.size());
        return ResponseEntity.ok(response);
    }


    }
