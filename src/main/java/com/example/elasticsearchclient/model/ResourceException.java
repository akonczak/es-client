package com.example.elasticsearchclient.model;

public class ResourceException extends Exception {

    public ResourceException(String response) {
        super(response);
    }
}
