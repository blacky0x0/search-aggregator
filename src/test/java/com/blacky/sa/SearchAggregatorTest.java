package com.blacky.sa;

import com.blacky.sa.crawler.*;
import com.blacky.sa.model.SearchResult;
import com.blacky.sa.strategy.NaturalOrderAggregationStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchAggregatorTest {

    @Test
    public void checkEmptyResults() throws Exception {
        BingCrawler bingCrawlerMock = mock(BingCrawler.class);
        GoogleCrawler googleCrawlerMock = mock(GoogleCrawler.class);

        when(bingCrawlerMock.search(anyString())).thenReturn(Collections.<SearchResult>emptyList());
        when(googleCrawlerMock.search(anyString())).thenReturn(Collections.<SearchResult>emptyList());

        ArrayList<Crawler> crawlers = new ArrayList<Crawler>() {{
            add(bingCrawlerMock);
            add(googleCrawlerMock);
        }};

        List<SearchResult> aggregatedSerp = SearchAggregator.searchPhrase("abc", crawlers, new NaturalOrderAggregationStrategy());

        assertEquals(0, aggregatedSerp.size());
    }

    @Test
    public void checkSortOrderResults() throws Exception {
        BingCrawler bingCrawlerMock = mock(BingCrawler.class);
        GoogleCrawler googleCrawlerMock = mock(GoogleCrawler.class);

        List<SearchResult> googleSerp = new ArrayList<SearchResult>() {{
            add(new SearchResult(1, "Google", "Title", "https://google.com"));
            add(new SearchResult(5, "Google", "Title", "https://google.com"));
        }};

        List<SearchResult> bingSerp = new ArrayList<SearchResult>() {{
            add(new SearchResult(2, "Bing", "Title", "https://bing.com"));
            add(new SearchResult(7, "Bing", "Title", "https://bing.com"));
        }};

        when(bingCrawlerMock.search(anyString())).thenReturn(googleSerp);
        when(googleCrawlerMock.search(anyString())).thenReturn(bingSerp);

        ArrayList<Crawler> crawlers = new ArrayList<Crawler>() {{
            add(bingCrawlerMock);
            add(googleCrawlerMock);
        }};

        List<SearchResult> aggregatedSerp = SearchAggregator.searchPhrase("abc", crawlers, new NaturalOrderAggregationStrategy());

        assertEquals(4, aggregatedSerp.size());
        assertEquals(1, aggregatedSerp.get(0).getPosition());
        assertEquals(2, aggregatedSerp.get(1).getPosition());
        assertEquals(5, aggregatedSerp.get(2).getPosition());
        assertEquals(7, aggregatedSerp.get(3).getPosition());
    }

/*
    @Test
    public void realSearch() throws Exception {
        String bingApiKey = "bing_api_key";
        String googleApiKey = "google_api_key";
        String googleCx = "cx";

        BingCrawler bingCrawler = new BingCrawler(bingApiKey);
        GoogleCrawler googleCrawler = new GoogleCrawler(googleApiKey, googleCx);

        ArrayList<Crawler> crawlers = new ArrayList<Crawler>() {{
            add(bingCrawler);
            add(googleCrawler);
        }};

        List<SearchResult> aggregatedSerp = SearchAggregator.searchPhrase("abc", crawlers, new NaturalOrderAggregationStrategy());

        assertEquals(20, aggregatedSerp.size());
    }
*/
}