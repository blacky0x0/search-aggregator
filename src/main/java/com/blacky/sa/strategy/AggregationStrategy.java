package com.blacky.sa.strategy;

import com.blacky.sa.model.SearchResult;

import java.util.List;

public interface AggregationStrategy {

    List<SearchResult> aggregate(List<SearchResult>... searchResultList);

}
