package com.blacky.sa;

import com.blacky.sa.model.SearchResult;
import com.blacky.sa.crawler.Crawler;
import com.blacky.sa.strategy.AggregationStrategy;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchAggregator {

    public static List<SearchResult> searchPhrase(String searchPhrase,
                             List<Crawler> searchEngineList,
                             AggregationStrategy aggregationStrategy) throws IOException, JSONException {

        List<List<SearchResult>> lists = new ArrayList<>();

        for (Crawler crawler : searchEngineList) {
            lists.add(crawler.search(searchPhrase));
        }

        return aggregationStrategy.aggregate(lists);
    }

}
