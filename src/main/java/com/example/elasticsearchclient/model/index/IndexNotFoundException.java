package com.example.elasticsearchclient.model.index;

import com.example.elasticsearchclient.model.ResourceException;

public class IndexNotFoundException extends ResourceException {
    public IndexNotFoundException(String response) {
        super(response);
    }
}
