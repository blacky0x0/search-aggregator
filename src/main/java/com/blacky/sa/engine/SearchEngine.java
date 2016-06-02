package com.blacky.sa.engine;

import com.blacky.sa.model.SearchResult;

import java.util.List;

public interface SearchEngine {

    List<SearchResult> search(String phrase);

}
