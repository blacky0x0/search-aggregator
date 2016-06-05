package com.blacky.sa.crawler;

import com.blacky.sa.exception.CrawlingRuntimeException;
import com.blacky.sa.model.SearchResult;
import java.util.List;

public interface Crawler {

    String getName();
    List<SearchResult> search(String phrase) throws CrawlingRuntimeException;

}
