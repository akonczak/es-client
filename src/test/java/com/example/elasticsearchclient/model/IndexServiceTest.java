package com.example.elasticsearchclient.model;

import com.example.elasticsearchclient.model.index.Index;
import com.example.elasticsearchclient.model.index.IndexAlreadyExistsException;
import com.example.elasticsearchclient.model.index.IndexNotFoundException;
import com.example.elasticsearchclient.model.index.Settings;
import com.example.elasticsearchclient.service.IndexServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IndexServiceTest {

    public static final String TEST_INDEX_NAME = "unit_test_index";

    private IndexServiceImpl indexService = new IndexServiceImpl();

    @Before
    public void init() {
        try {
            indexService.delete(TEST_INDEX_NAME);
        } catch (ResourceException e) {
            //ignore this error
        }
    }

    @Test
    public void shouldCreateIndex() throws ResourceException {
        assertThat(indexService.createIndex(TEST_INDEX_NAME), is(true));
    }

    @Test(expected = IndexAlreadyExistsException.class)
    public void shouldCreateIndexThrowErrorWhenIndexExists() throws ResourceException {
        assertThat(indexService.createIndex(TEST_INDEX_NAME), is(true));
        assertThat(indexService.createIndex(TEST_INDEX_NAME), is(true));
    }

    @Test(expected = IndexNotFoundException.class)
    public void shouldDeleteIndexThrowErrorWhenIndexDoesNotExists() throws ResourceException {
        assertThat(indexService.delete(TEST_INDEX_NAME), is(true));
    }

}