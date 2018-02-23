package com.example.elasticsearchclient.service;

import com.example.elasticsearchclient.model.ResourceException;
import com.example.elasticsearchclient.model.index.IndexDescription;
import com.example.elasticsearchclient.model.index.Settings;

import java.util.Map;

public interface IndexService {

    boolean isIndexExists(String name) throws ResourceException;

    boolean createIndex(String name) throws ResourceException;

    boolean createIndex(String name, Settings settings) throws ResourceException;

    IndexDescription getIndex(String name) throws ResourceException;

    boolean delete(String name) throws ResourceException;

    boolean open(String name) throws ResourceException;

    boolean close(String name) throws ResourceException;

    //mappings

    Map<String, Map<String,Settings>> getSettings(String ... name) throws ResourceException;


}
