package com.blacky.sa.strategy;

import com.blacky.sa.model.SearchResult;

import java.util.List;

public interface AggregationStrategy {

    /**
     * Method have to combine multiple lists of SERP by any algorithm and return a single SERP represented as a list.
     */
    List<SearchResult> aggregate(List<List<SearchResult>> serps);

}
