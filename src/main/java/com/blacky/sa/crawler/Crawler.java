package com.blacky.sa.crawler;

import com.blacky.sa.model.SearchResult;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface Crawler {

    String getName();
    List<SearchResult> search(String phrase) throws IOException, JSONException;

}
