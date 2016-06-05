package com.blacky.sa;

import com.blacky.sa.model.SearchResult;
import com.blacky.sa.crawler.Crawler;
import com.blacky.sa.strategy.AggregationStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class SearchAggregator {

    /**
     * Method combines SERP results from given search engines into single list.
     *
     * @param searchPhrase - phrase to be searched in any search engine
     * @param searchEngineList - list of crawlers
     * @param aggregationStrategy - strategy to be applied to combine different SERP's lists
     * @return - SERP sorted by any strategy
     */
    public static List<SearchResult> searchPhrase(String searchPhrase,
                             List<Crawler> searchEngineList,
                             AggregationStrategy aggregationStrategy) {

        List<List<SearchResult>> serps = new ArrayList<>();
        Map<Crawler, FutureTask<List<SearchResult>>> tasks = new HashMap<>();

        for (Crawler crawler : searchEngineList) {
            FutureTask<List<SearchResult>> task = new FutureTask<>(new Crawling(crawler, searchPhrase));
            tasks.put(crawler, task);
            new Thread(task).start();
        }

        for (Crawler crawler : tasks.keySet()) {
            try {
                serps.add(tasks.get(crawler).get(5, TimeUnit.SECONDS));
            } catch (TimeoutException | InterruptedException | ExecutionException e ) {
                Logger.getAnonymousLogger().warning("Skip crawling from " + crawler.getName());
            }
        }

        return aggregationStrategy.aggregate(serps);
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
