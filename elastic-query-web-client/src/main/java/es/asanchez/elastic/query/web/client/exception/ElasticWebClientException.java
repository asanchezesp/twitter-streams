package es.asanchez.elastic.query.web.client.exception;

public class ElasticWebClientException extends RuntimeException{

    public ElasticWebClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticWebClientException() {
    }

    public ElasticWebClientException(String message) {
        super(message);
    }
}
