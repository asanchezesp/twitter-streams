package es.asanchez.elastic.query.service.api;

import es.asanchez.elastic.query.service.model.ElasticQueryServiceRequestModel;
import es.asanchez.elastic.query.service.model.ElasticQueryServiceResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "documents")
public class ElasticDocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);

    @GetMapping
    public ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments(){
        List<ElasticQueryServiceResponseModel> response = new ArrayList<>();
        LOG.info("ElasticSearch returned {} documents", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable String id){
        ElasticQueryServiceResponseModel response = ElasticQueryServiceResponseModel.builder().id(id).build();
        LOG.info("ElasticSearch returned document with id {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("get-document-by-text")
    public ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentByText(@RequestBody ElasticQueryServiceRequestModel request){
        List<ElasticQueryServiceResponseModel> response = new ArrayList<>();
        LOG.info("ElasticSearch returned {} documents", response.size());
        return ResponseEntity.ok(response);
    }


    }