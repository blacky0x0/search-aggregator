package com.blacky.sa.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BingCrawlerTest {

    @Test
    public void parseValidJsonResponse() throws Exception {
        String apiKey = "api_key";
        BingCrawler spy = spy(new BingCrawler(apiKey));
        String jsonResponse = "{\"d\":{\"results\":[]}}";
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        doReturn(jsonResponse).when(spy).getResponse(any());
        spy.search("Patience you must have");
        verify(spy).getResponse(captor.capture());

        String apiUrl = "https://api.datamarket.azure.com/Bing/Search/Web";
        String params = "?$top=10&$skip=0&$format=JSON";
        String formedUrl = apiUrl.concat(params.concat("&Query=%27Patience+you+must+have%27"));

        assertEquals(formedUrl, captor.getValue());
    }
}