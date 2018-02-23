package com.example.elasticsearchclient.model.index;

import com.example.elasticsearchclient.model.ResourceException;

public class IndexCloseException extends ResourceException {
    public IndexCloseException(String response) {
        super(response);
    }
}
