package com.example.elasticsearchclient.service;

import com.example.elasticsearchclient.model.ElasticsearchServerException;
import com.example.elasticsearchclient.model.ResourceException;
import com.example.elasticsearchclient.model.index.IndexAlreadyExistsException;
import com.example.elasticsearchclient.model.index.IndexDescription;
import com.example.elasticsearchclient.model.index.IndexNotFoundException;
import com.example.elasticsearchclient.model.index.Settings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class IndexServiceImpl implements IndexService {

    private static final Logger LOG = LoggerFactory.getLogger(IndexServiceImpl.class);
    public static final String SETTINGS = "settings";

    private RestTemplate restTemplate = new RestTemplate();

    private String getIndexPath(String name) {
        return String.format("http://localhost:9260/%s", name);
    }

    @Override
    public boolean isIndexExists(String name) throws ResourceException {
        try {
            return execute(() ->
                    restTemplate.exchange(getIndexPath(name), HttpMethod.HEAD, null, String.class).getStatusCode().is2xxSuccessful()
            );
        } catch (IndexNotFoundException e) {
            return false;
        }
    }


    public boolean createIndex(String name) throws ResourceException {
        return createIndex(name, null);
    }

    @Override
    public boolean createIndex(String name, Settings settings) throws ResourceException {

        HashMap<String, Settings> configuration = new HashMap<>();

        if (settings != null) {
            configuration.put(SETTINGS, settings);
        }

        try {
            check(configuration);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return execute(() -> {
            ResponseEntity<String> exchange = restTemplate.exchange(getIndexPath(name),
                    HttpMethod.PUT,
                    new HttpEntity(configuration),
                    String.class
            );
            return exchange.getStatusCode().is2xxSuccessful();
        });

    }

    private void check(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(obj));
    }

    @Override
    public IndexDescription getIndex(String name) throws ResourceException {
        return execute(() -> {
            ResponseEntity<Map<String, IndexDescription>> exchange = restTemplate.exchange(getIndexPath(name),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, IndexDescription>>() {
                    });
            return exchange.getStatusCode().is2xxSuccessful() ? exchange.getBody().get(name) : null;
        });
    }

    @Override
    public boolean delete(String name) throws ResourceException {
        return execute(() -> {
            ResponseEntity<String> exchange = restTemplate.exchange(getIndexPath(name), HttpMethod.DELETE, null, String.class);
            if (LOG.isDebugEnabled()) {
                LOG.debug(exchange.toString());
            }
            return exchange.getStatusCode().is2xxSuccessful();
        });
    }

    @Override
    public boolean open(String name) throws ResourceException {
        return execute(() -> {
            ResponseEntity<String> exchange = restTemplate.exchange(String.format("%s/%s", getIndexPath(name), "_open"), HttpMethod.POST, null, String.class);
            if (LOG.isDebugEnabled()) {
                LOG.debug(exchange.toString());
            }
            return exchange.getStatusCode().is2xxSuccessful();
        });
    }

    @Override
    public boolean close(String name) throws ResourceException {
        return execute(() -> {
            ResponseEntity<String> exchange = restTemplate.exchange(String.format("%s/%s", getIndexPath(name), "_close"), HttpMethod.POST, null, String.class);
            if (LOG.isDebugEnabled()) {
                LOG.debug(exchange.toString());
            }
            return exchange.getStatusCode().is2xxSuccessful();
        });
    }

    @Override
    public Map<String, Map<String, Settings>> getSettings(String... names) throws ResourceException {
        return execute(() -> {
            ParameterizedTypeReference<Map<String, Map<String, Settings>>> type = new ParameterizedTypeReference<Map<String, Map<String, Settings>>>() {
            };

            String settings = String.format("%s/%s", getIndexPath(String.join(",", names)), "_settings");
            ResponseEntity<Map<String, Map<String, Settings>>> exchange = restTemplate.exchange(settings, HttpMethod.GET, null, type);
            return exchange.getStatusCode().is2xxSuccessful() ? exchange.getBody() : null;
        });
    }


    public <T> T execute(Supplier<T> supplier) throws ResourceException {
        try {
            return supplier.get();
        } catch (HttpClientErrorException e) {
            throw mapErrors(e);
        }
    }

    protected ResourceException mapErrors(HttpClientErrorException error) {
        String responseBodyAsString = error.getResponseBodyAsString();
        if (LOG.isDebugEnabled()) {
            LOG.debug(responseBodyAsString);
        }
        if (responseBodyAsString.contains("index_not_found_exception")) {
            return new IndexNotFoundException(responseBodyAsString);
        }

        if (responseBodyAsString.contains("resource_already_exists_exception")) {
            return new IndexAlreadyExistsException(responseBodyAsString);
        }

        if(error.getRawStatusCode()==404){
            return new IndexNotFoundException(responseBodyAsString);
        }

        return new ElasticsearchServerException(responseBodyAsString);
    }


}
