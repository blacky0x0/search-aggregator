package com.blacky.sa.strategy;

import com.blacky.sa.model.SearchResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class NaturalOrderAggregationStrategyTest {
    @Test
    public void neverReturnNullValue() throws Exception {
        assertNotNull(new NaturalOrderAggregationStrategy().aggregate(null));
    }

    @Test
    public void checkNaturalOrderSorting() throws Exception {
        NaturalOrderAggregationStrategy strategy = new NaturalOrderAggregationStrategy();
        List<List<SearchResult>> serps = new ArrayList<>();
        List<SearchResult> serp = new ArrayList<>();
        SearchResult search = new SearchResult(4, "Google", "Title", "https://google.com");
        SearchResult search2 = new SearchResult(2, "Bing", "Title2", "https://bing.com");

        serp.add(search);
        serp.add(search2);
        serps.add(serp);

        List<SearchResult> answer = strategy.aggregate(serps);
        Iterator<SearchResult> it = answer.iterator();

        assertEquals(2, it.next().getPosition());
        assertEquals(4, it.next().getPosition());
    }
}