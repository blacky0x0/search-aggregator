package com.blacky.sa.strategy;

import com.blacky.sa.model.SearchResult;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.sort;

/**
 * SERP's objects are sorted by their position parameter.
 */
public class NaturalOrderAggregationStrategy implements AggregationStrategy {

    @Override
    public List<SearchResult> aggregate(List<List<SearchResult>> serps) {
        if (Objects.isNull(serps) || serps.isEmpty()) return Collections.emptyList();

        List<SearchResult> resultList = new ArrayList<>();

        for (List<SearchResult> serp : serps) {
            resultList.addAll(serp.stream().collect(Collectors.toList()));
        }

        sort(resultList, (o1, o2) -> o1.getPosition() - o2.getPosition());

        return resultList;
    }
}
