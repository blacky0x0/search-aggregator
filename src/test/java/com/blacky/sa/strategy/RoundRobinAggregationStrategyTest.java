package com.blacky.sa.strategy;

import com.blacky.sa.model.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RoundRobinAggregationStrategyTest {

    @Test
    public void neverReturnNullValue() throws Exception {
        assertNotNull(new RoundRobinAggregationStrategy().aggregate(null));
    }

    @Test
    public void checkRoundRobinBehaviour() throws Exception {
        RoundRobinAggregationStrategy robinStrategy = new RoundRobinAggregationStrategy();
        List<List<SearchResult>> serps = new ArrayList<>();
        List<SearchResult> serp = new ArrayList<>();
        SearchResult search = new SearchResult(1, "Google", "Title", "https://google.com");
        SearchResult search2 = new SearchResult(2, "Bing", "Title2", "https://bing.com");

        serp.add(search);
        serp.add(search2);
        serps.add(serp);

        List<SearchResult> answer = robinStrategy.aggregate(serps);
        Iterator<SearchResult> it = answer.iterator();

        assertEquals(1, it.next().getPosition());
        assertEquals(2, it.next().getPosition());
        assertEquals("Title", it.next().getTitle());
        assertEquals("Title2", it.next().getTitle());
        assertEquals("https://google.com", it.next().getUrl());
        assertEquals("https://bing.com", it.next().getUrl());
    }

}