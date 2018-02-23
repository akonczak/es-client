package com.example.elasticsearchclient.model;

public class ElasticsearchServerException extends ResourceException {
    public ElasticsearchServerException(String response) {
        super(response);
    }
}
