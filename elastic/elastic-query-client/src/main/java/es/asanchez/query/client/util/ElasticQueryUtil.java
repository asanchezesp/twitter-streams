package es.asanchez.query.client.util;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import es.asanchez.elastic.model.index.api.IndexModel;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ElasticQueryUtil<T extends IndexModel> {

    public Query getSearchQueryById(String id){
        return new NativeQueryBuilder().withIds(Collections.singletonList(id))
                .build();
    }

    public Query getSeerchQueryByFieldText(String field, String text){
        MatchQuery matchQuery = new MatchQuery.Builder().field(field).analyzer(text).build();
        BoolQuery boolQuery = new BoolQuery.Builder().must(matchQuery._toQuery()).build();
        return new NativeQueryBuilder().withQuery(boolQuery._toQuery()).build();
    }

    public Query getSeerchQueryForAll(){
        BoolQuery boolQuery = new BoolQuery.Builder().must(QueryBuilders.matchAll().build()._toQuery()).build();
        return new NativeQueryBuilder().withQuery(boolQuery._toQuery()).build();
    }


}
