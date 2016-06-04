package com.blacky.sa.crawler;

import com.blacky.sa.model.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import java.io.*;
import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BingCrawlerTest {

    @Test
    public void parseValidJsonResponse() throws Exception {
        String apiKey = "api_key";
        BingCrawler searchEngine = new BingCrawler(apiKey);
        BingCrawler spy = spy(searchEngine);

        String s = "{\"d\":{\"results\":[{\"Title\":\"Caving - Wikipedia\",\"Url\":\"https://en.wikipedia.org/wiki/Caving\"}]}}";
        ByteArrayInputStream ba = new ByteArrayInputStream(s.getBytes());
        when(spy.getStream(any())).thenReturn(ba);

        BufferedReader buf = mock(BufferedReader.class);

        List<SearchResult> list = spy.search("what is spelunking");
        SearchResult result = list.get(0);

        assertEquals(1, result.getPosition());
        assertEquals("Bing", result.getSearchEngine());
        assertEquals("Caving - Wikipedia", result.getTitle());
        assertEquals("https://en.wikipedia.org/wiki/Caving", result.getUrl());
    }

}