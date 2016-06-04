package com.blacky.sa.model;

public class SearchResult {

    private int position;
    private String searchEngine;
    private String title;
    private String url;

    public SearchResult() {}

    public SearchResult(int position, String searchEngine, String title, String url) {
        this.position = position;
        this.searchEngine = searchEngine;
        this.title = title;
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public String getSearchEngine() {
        return searchEngine;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "position=" + position +
                ", searchEngine='" + searchEngine + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
