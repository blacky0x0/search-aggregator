package com.blacky.sa.strategy;

import com.blacky.sa.model.SearchResult;
import java.util.*;

/**
 * SERP's objects are sorted by their position parameter.
 */
public class NaturalOrderAggregationStrategy implements AggregationStrategy {

    @Override
    public List<SearchResult> aggregate(List<List<SearchResult>> serps) {
        if (Objects.isNull(serps) || serps.isEmpty()) return Collections.emptyList();

        List<SearchResult> resultList = new ArrayList<>();

        for (int i = 0; i < serps.get(0).size(); i++) {
            for (List<SearchResult> serp : serps) {
                resultList.add(serp.get(i));
            }
        }

        return resultList;
    }
}
