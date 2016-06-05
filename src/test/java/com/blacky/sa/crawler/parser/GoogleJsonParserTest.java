package com.blacky.sa.crawler.parser;

import com.blacky.sa.model.SearchResult;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GoogleJsonParserTest {

    @Test
    public void parseValidJsonResponse() throws Exception {
        String jsonResponse = "{\"items\":[{\"title\":\"Urban Dictionary: spelunking\",\"link\": \"http://www.urbandictionary.com/define.php?term=spelunking\"}]}";

        List<SearchResult> serp = GoogleJsonParser.parse(jsonResponse);
        assertEquals(1, serp.size());

        SearchResult result = serp.get(0);

        assertEquals(1, result.getPosition());
        assertEquals("Google", result.getSearchEngine());
        assertEquals("Urban Dictionary: spelunking", result.getTitle());
        assertEquals("http://www.urbandictionary.com/define.php?term=spelunking", result.getUrl());
    }

}