package com.blacky.sa;

import com.blacky.sa.crawler.BingCrawler;
import com.blacky.sa.crawler.Crawler;
import com.blacky.sa.crawler.GoogleCrawler;
import com.blacky.sa.model.SearchResult;
import com.blacky.sa.strategy.NaturalOrderAggregationStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchAggregatorTest {

    @Test
    public void testSearchPhrase() throws Exception {
        String bingApiKey = "bing_api_key";
        String googleApiKey = "google_api_key";
        String googleCx = "cx";

        BingCrawler bingCrawler = new BingCrawler(bingApiKey);
        GoogleCrawler googleCrawler= new GoogleCrawler(googleApiKey, googleCx);

        ArrayList<Crawler> crawlers = new ArrayList<Crawler>() {{
            add(bingCrawler);
            add(googleCrawler);
        }};

        List<SearchResult> results = SearchAggregator.searchPhrase("abc", crawlers, new NaturalOrderAggregationStrategy());

        for (SearchResult sr : results) {
            System.out.println(sr);
        }

        assertEquals(20, results.size());
    }

}