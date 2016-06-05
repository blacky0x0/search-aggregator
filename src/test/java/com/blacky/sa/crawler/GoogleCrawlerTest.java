package com.blacky.sa.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoogleCrawlerTest {

    @Test
    public void parseValidJsonResponse() throws Exception {
        String apiKey = "api_key";
        String cx = "custom_search_engine_id";
        GoogleCrawler spy = spy(new GoogleCrawler(apiKey, cx));
        String jsonResponse = "{\"items\":[]}";
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        doReturn(jsonResponse).when(spy).getResponse(any());
        spy.search("Always pass on what you have learned");
        verify(spy).getResponse(captor.capture());

        String apiUrl = "https://www.googleapis.com/customsearch/v1";
        String params = "?key=api_key&cx=custom_search_engine_id&num=10";
        String formedUrl = apiUrl.concat(params.concat("&q=%27Always+pass+on+what+you+have+learned%27"));

        assertEquals(formedUrl, captor.getValue());
    }
}