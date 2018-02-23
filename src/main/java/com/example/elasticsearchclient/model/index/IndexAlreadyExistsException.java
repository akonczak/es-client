package com.example.elasticsearchclient.model.index;

import com.example.elasticsearchclient.model.ResourceException;

public class IndexAlreadyExistsException extends ResourceException {
    public IndexAlreadyExistsException(String response) {
        super(response);
    }
}
