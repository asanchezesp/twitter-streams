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

import java.util.List;

@RestController
@RequestMapping(value = "/documents", produces = "application/vnd.api.v2+json")
@RequiredArgsConstructor
public class ElasticDocumentControllerV2 {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentControllerV2.class);

    @GetMapping
    public ResponseEntity<String> getAllDocuments(){
        return ResponseEntity.ok("This is version 2");
    }

    }
