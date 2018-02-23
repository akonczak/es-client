package com.example.elasticsearchclient.model;

import com.example.elasticsearchclient.model.index.Index;
import com.example.elasticsearchclient.model.index.IndexAlreadyExistsException;
import com.example.elasticsearchclient.model.index.IndexDescription;
import com.example.elasticsearchclient.model.index.Settings;
import com.example.elasticsearchclient.service.IndexServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class IndexDescriptionTest {

    private String readJsonFile(String filePath) throws IOException {
        try (InputStreamReader in = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filePath))) {
            return FileCopyUtils.copyToString(in);
        }
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldMapIndexDescription() throws IOException {
        String json = readJsonFile("json/get-index.json");
        Map<String, IndexDescription> map = mapper.readValue(json, new TypeReference<Map<String, IndexDescription>>() {
        });
        System.out.println(map);
        assertThat(map.size(), is(1));
        IndexDescription testIndex = map.get("test_index");
        assertThat(testIndex.getAliases().keySet(), hasItems("alias1"));
        assertThat(testIndex.getAliases().keySet(), hasItems("alias2"));
        assertThat(testIndex.getMappings().get("test_type"), is(notNullValue()));
        assertThat(testIndex.getSettings(), is(notNullValue()));
    }


    @Test
    public void mapSettings() throws IOException {
        String settings = readJsonFile("json/get-settings.json");
        Map<String, Map<String,Settings>> map = mapper.readValue(settings, new TypeReference<Map<String, Map<String,Settings>>>() {
        });
        System.out.println(map);
        assertThat(map.size(), is(2));
        Index twitter = map.get("twitter").get("settings").getIndex();
        assertThat(twitter.getProvided_name(), is("twitter"));
        Index kibana = map.get(".kibana").get("settings").getIndex();
        assertThat(kibana.getProvided_name(), is(".kibana"));

    }

    @Test
    public void rest() throws ResourceException {

        String test_index = "test_index";
        if (indexService.isIndexExists(test_index)) {
            System.out.println(indexService.getIndex(test_index));
        }

        String test_index_v1 = "test_index_v1";
        if (!indexService.isIndexExists(test_index_v1)) {
            System.out.println(indexService.createIndex(test_index_v1, new Settings()));
        }
        String test_index_v2 = "test_index_v2";
        if (!indexService.isIndexExists(test_index_v2)) {
            System.out.println(indexService.createIndex(test_index_v2, new Settings()));
        }


        System.out.println(indexService.getIndex(test_index_v1));
        System.out.println(indexService.getSettings(test_index_v1, test_index_v2));

        //System.out.println(indexService.close(test_index_v1));
        //System.out.println(indexService.open(test_index_v1));
        //System.out.println(indexService.open(test_index_v1));


        //System.out.println(indexService.delete(test_index));
        //System.out.println(indexService.delete(test_index_v1));
        //System.out.println(indexService.delete(test_index_v2));


    }

    private IndexServiceImpl indexService = new IndexServiceImpl();

}