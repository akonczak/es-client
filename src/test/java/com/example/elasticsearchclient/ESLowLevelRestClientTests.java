package com.example.elasticsearchclient;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.IOException;


public class ESLowLevelRestClientTests {

    @Test
    public void shouldConnectToES5() throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9250, "http")).build();
        Response response = restClient.performRequest("GET", "/");
    }

    @Test
    public void shouldConnectToES6() throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9260, "http")).build();
        Response response = restClient.performRequest("GET", "/");
    }

}
