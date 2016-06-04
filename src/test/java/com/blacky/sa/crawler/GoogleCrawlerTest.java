package com.blacky.sa.crawler;

import com.blacky.sa.model.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoogleCrawlerTest {

    @Test
    public void parseValidJsonResponse() throws Exception {
        String apiKey = "api_key";
        String cx = "custom_search_engine_id";
        GoogleCrawler spy = spy(new GoogleCrawler(apiKey, cx));
        String jsonResponse = "{\"items\":[{\"title\":\"Urban Dictionary: spelunking\",\"link\": \"http://www.urbandictionary.com/define.php?term=spelunking\"}]}";

        doReturn(jsonResponse).when(spy).getResponse(any());

        List<SearchResult> serp = spy.search("what is spelunking");
        SearchResult result = serp.get(0);

        assertEquals(1, result.getPosition());
        assertEquals("Google", result.getSearchEngine());
        assertEquals("Urban Dictionary: spelunking", result.getTitle());
        assertEquals("http://www.urbandictionary.com/define.php?term=spelunking", result.getUrl());
    }

}