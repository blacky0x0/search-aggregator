package com.blacky.sa.strategy;

import com.blacky.sa.model.SearchResult;
import java.util.*;

public class NaturalOrderAggregationStrategy implements AggregationStrategy{

    @Override
    public List<SearchResult> aggregate(List<List<SearchResult>> searchResultList) {
        if (Objects.isNull(searchResultList)) return Collections.emptyList();

        List<SearchResult> resultList = new ArrayList<>();

        for (int i = 0; i < searchResultList.get(0).size(); i++) {
            for (List<SearchResult> list : searchResultList) {
                resultList.add(list.get(i));
            }
        }

        return resultList;
    }

}
