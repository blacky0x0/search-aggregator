package com.blacky.sa.crawler;

import com.blacky.sa.exception.CrawlingRuntimeException;
import com.blacky.sa.model.SearchResult;
import java.util.List;

public interface Crawler {

    /**
     * Crawler's name
     */
    String getName();

    /**
     * Data can be obtained through any search engine API.
     * Search must be performed by a given phrase.
     * Method have to return a SERP represented as a list.
     */
    List<SearchResult> search(String phrase) throws CrawlingRuntimeException;

}
