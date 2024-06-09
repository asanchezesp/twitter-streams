package es.asanchez.elastic.query.web.client.api;

import es.asanchez.elastic.query.web.client.model.ElasticWebClientRequestModel;
import es.asanchez.elastic.query.web.client.model.ElasticWebClientResponseModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QueryController {

    private final Logger LOG = LoggerFactory.getLogger(QueryController.class);

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("error")
    public String error(){
        return "error";
    }

    @GetMapping("home")
    public String home(Model model){
        model.addAttribute("elasticQueryWebClientRequestModel", ElasticWebClientRequestModel.builder().build());
        return "home";
    }

    @PostMapping("/query-by-text")
    public String queryByText(@Valid  ElasticWebClientRequestModel requestModel, Model model){
        List<ElasticWebClientResponseModel> response = new ArrayList<>();
        response.add(ElasticWebClientResponseModel.builder().id("1").userId(1L).text("prueba").build());
        model.addAttribute("elasticQueryWebClientResponseModel", response);
        model.addAttribute("searchText",requestModel.getText());
        model.addAttribute("elasticQueryWebClientRequestModel", ElasticWebClientRequestModel.builder().build());
        return "home";
    }
}
