package com.example.elasticsearchclient.model;

import com.example.elasticsearchclient.model.index.IndexDescription;
import com.example.elasticsearchclient.model.index.Settings;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class IndexDescriptionTest {

    private final String json="{\n" +
            "  \"test_index\": {\n" +
            "    \"aliases\": [\"aaaaaa\"],\n" +
            "    \"mappings\": {\n" +
            "      \"test_type\": {\n" +
            "        \"properties\": {\n" +
            "          \"name\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"fields\": {\n" +
            "              \"keyword\": {\n" +
            "                \"type\": \"keyword\",\n" +
            "                \"ignore_above\": 256\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"settings\": {\n" +
            "      \"index\": {\n" +
            "        \"creation_date\": \"1510316866502\",\n" +
            "        \"number_of_shards\": \"5\",\n" +
            "        \"number_of_replicas\": \"1\",\n" +
            "        \"uuid\": \"Yc4RstHSRJenDArbp-kQ3A\",\n" +
            "        \"version\": {\n" +
            "          \"created\": \"6000052\"\n" +
            "        },\n" +
            "        \"provided_name\": \"test_index\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";


    @Test
    public void test() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //Map<String, Object> map = new HashMap<String, Object>();


        Map<String, IndexDescription> map = mapper.readValue(json, new TypeReference<Map<String, IndexDescription>>(){});
        //IndexDescription map = mapper.readValue(json, new TypeReference<IndexDescription>(){});
        System.out.println(map);

    }

    @Test
    public void rest() throws IOException {

        String test_index = "test_index";
        if(indexService.isIndexExists(test_index)) {
            System.out.println(indexService.getIndex(test_index));
        }

        String test_index_v1 = "test_index_v1";
        if(!indexService.isIndexExists(test_index_v1)) {
            System.out.println(indexService.createIndex(test_index_v1, new Settings()));
        }
        String test_index_v2 = "test_index_v2";
        if(!indexService.isIndexExists(test_index_v2)) {
            System.out.println(indexService.createIndex(test_index_v2, new Settings()));
        }

    }

    private IndexService indexService = new IndexService();

    class IndexService {

        RestTemplate restTemplate = new RestTemplate();


        private String getIndexPath(String name){
            return String.format("http://localhost:9202/%s", name);
        }

        public boolean isIndexExists(String name){
            try {
                return restTemplate.exchange(getIndexPath(name), HttpMethod.HEAD, null, String.class).getStatusCode().is2xxSuccessful();
            }catch (HttpClientErrorException e){
                return false;
            }
        }

        public Map<String,Object> createIndex(String name, Settings settings){

            ParameterizedTypeReference<Map<String, Object>> type = new ParameterizedTypeReference<Map<String, Object>>(){};
            HashMap<String, Settings> kvHashMap = new HashMap<>();
            kvHashMap.put("settings", settings);
            HttpEntity body = new HttpEntity(kvHashMap);
            ParameterizedTypeReference<String> typeAsString = new ParameterizedTypeReference<String>(){};
            ResponseEntity<Map<String, Object>> exchange = restTemplate.exchange(getIndexPath(name), HttpMethod.PUT, body, type);
            if(!exchange.getStatusCode().is2xxSuccessful()){
                System.out.println(exchange.toString());
            }
            return exchange.getStatusCode().is2xxSuccessful() ? exchange.getBody():null;
        }

        public IndexDescription getIndex(String name){
            ParameterizedTypeReference<Map<String, IndexDescription>> type = new ParameterizedTypeReference<Map<String, IndexDescription>>(){};


            System.out.println(String.format("Quering %s", getIndexPath(name)));
            ResponseEntity<Map<String, IndexDescription>> exchange = restTemplate.exchange(getIndexPath(name), HttpMethod.GET, null, type);
            return exchange.getStatusCode().is2xxSuccessful() ? exchange.getBody().get(name):null;
        }
    }

}