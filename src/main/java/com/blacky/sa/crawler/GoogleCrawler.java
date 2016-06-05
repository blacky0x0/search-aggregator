package com.blacky.sa.crawler;

import com.blacky.sa.crawler.parser.GoogleJsonParser;
import com.blacky.sa.model.SearchResult;
import org.json.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GoogleCrawler implements Crawler {
    public static final String urlPattern = "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&num=%d&q=%s";

    private final int size = 10;
    private final String apiKey;
    private final String cx;

    /**
     * @param apiKey - Google API key
     * @param cx - Google custom search engine ID
     */
    public GoogleCrawler(String apiKey, String cx) {
        this.apiKey = apiKey;
        this.cx = cx;
    }

    @Override
    public String getName() {
        return "Google";
    }

    @Override
    public List<SearchResult> search(String searchPhrase) throws IOException, JSONException {

        String encodedSearchPhrase = URLEncoder.encode(searchPhrase, StandardCharsets.UTF_8.name());
        String googleUrl = String.format(urlPattern, apiKey, cx, size, encodedSearchPhrase);

        return GoogleJsonParser.parse(getResponse(googleUrl));
    }

    public String getResponse(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();

        StringBuilder response = new StringBuilder();
        String inputLine;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return response.toString();
    }

    public int getSize() {
        return size;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getCx() {
        return cx;
    }
}
