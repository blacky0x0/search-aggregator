package com.blacky.sa.crawler;

import com.blacky.sa.model.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BingCrawlerTest {

    @Test
    public void parseValidJsonResponse() throws Exception {
        String apiKey = "api_key";
        BingCrawler spy = spy(new BingCrawler(apiKey));
        String jsonResponse = "{\"d\":{\"results\":[{\"Title\":\"Caving - Wikipedia\",\"Url\":\"https://en.wikipedia.org/wiki/Caving\"}]}}";

        doReturn(jsonResponse).when(spy).getResponse(any());

        List<SearchResult> serp = spy.search("what is spelunking");
        SearchResult result = serp.get(0);

        assertEquals(1, result.getPosition());
        assertEquals("Bing", result.getSearchEngine());
        assertEquals("Caving - Wikipedia", result.getTitle());
        assertEquals("https://en.wikipedia.org/wiki/Caving", result.getUrl());
    }

}