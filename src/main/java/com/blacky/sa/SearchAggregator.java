package com.blacky.sa;

import com.blacky.sa.model.SearchResult;
import com.blacky.sa.crawler.Crawler;
import com.blacky.sa.strategy.AggregationStrategy;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class SearchAggregator {

    public static List<SearchResult> searchPhrase(String searchPhrase,
                             List<Crawler> searchEngineList,
                             AggregationStrategy aggregationStrategy) throws IOException, JSONException {

        List<List<SearchResult>> lists = new ArrayList<>();
        Map<Crawler, FutureTask<List<SearchResult>>> tasks = new HashMap<>();

        for (Crawler crawler : searchEngineList) {
            FutureTask<List<SearchResult>> task = new FutureTask<>(new Crawling(crawler, searchPhrase));
            tasks.put(crawler, task);
            new Thread(task).start();
        }

        for (Crawler crawler : tasks.keySet()) {
            try {
                lists.add(tasks.get(crawler).get(5, TimeUnit.SECONDS));
            } catch (TimeoutException | InterruptedException | ExecutionException e ) {
                Logger.getAnonymousLogger().warning("Skip crawling from " + crawler.getName());
            }
        }

        return aggregationStrategy.aggregate(lists);
    }

    static class Crawling implements Callable<List<SearchResult>> {
        private Crawler crawler;
        private String searchPhrase;

        public Crawling(Crawler crawler, String searchPhrase) {
            this.crawler = crawler;
            this.searchPhrase = searchPhrase;
        }

        @Override
        public List<SearchResult> call() throws Exception {
            return crawler.search(searchPhrase);
        }
    }

}
