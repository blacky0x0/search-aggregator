package com.blacky.sa.crawler.parser;

import com.blacky.sa.model.SearchResult;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BingJsonParserTest {

    @Test
    public void parseValidJsonResponse() throws Exception {
        String jsonResponse = "{\"d\":{\"results\":[{\"Title\":\"Caving - Wikipedia\",\"Url\":\"https://en.wikipedia.org/wiki/Caving\"}]}}";

        List<SearchResult> serp = BingJsonParser.parse(jsonResponse);
        assertEquals(1, serp.size());

        SearchResult result = serp.get(0);

        assertEquals(1, result.getPosition());
        assertEquals("Bing", result.getSearchEngine());
        assertEquals("Caving - Wikipedia", result.getTitle());
        assertEquals("https://en.wikipedia.org/wiki/Caving", result.getUrl());
    }

}