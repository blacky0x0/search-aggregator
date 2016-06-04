package com.blacky.sa.crawler;

import com.blacky.sa.model.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoogleCrawlerTest {

    @Test
    public void parseValidJsonResponse() throws Exception {
        String apiKey = "api_key";
        String cx = "custom_search_engine_id";
        GoogleCrawler searchEngine = new GoogleCrawler(apiKey, cx);
        GoogleCrawler spy = spy(searchEngine);

        String s = "{\"items\":[{\"title\":\"Urban Dictionary: spelunking\",\"link\": \"http://www.urbandictionary.com/define.php?term=spelunking\"}]}";
        ByteArrayInputStream ba = new ByteArrayInputStream(s.getBytes());

        when(spy.getStream(any())).thenReturn(ba);

        List<SearchResult> list = spy.search("what is spelunking");
        SearchResult result = list.get(0);

        assertEquals(1, result.getPosition());
        assertEquals("Google", result.getSearchEngine());
        assertEquals("Urban Dictionary: spelunking", result.getTitle());
        assertEquals("http://www.urbandictionary.com/define.php?term=spelunking", result.getUrl());
    }

}