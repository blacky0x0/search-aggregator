package com.blacky.sa.crawler;

import com.blacky.sa.crawler.parser.GoogleJsonParser;
import com.blacky.sa.exception.CrawlingRuntimeException;
import com.blacky.sa.model.SearchResult;
import org.json.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoogleCrawler implements Crawler {
    public static final String urlPattern = "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&num=%d&q=%%27%s%%27";

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
    public List<SearchResult> search(String searchPhrase) {
        String encodedSearchPhrase, formedUrl;

        try {
            encodedSearchPhrase = URLEncoder.encode(searchPhrase, StandardCharsets.UTF_8.name());
            formedUrl = String.format(urlPattern, apiKey, cx, size, encodedSearchPhrase);
        } catch (UnsupportedEncodingException ex) {
            String msg = "The UTF8 charset couldn't be found";
            Logger.getAnonymousLogger().log(Level.WARNING, msg);
            throw new CrawlingRuntimeException(msg, ex);
        }

        return GoogleJsonParser.parse(getResponse(formedUrl));
    }

    public String getResponse(String url) {
        StringBuilder response;

        try {
            URLConnection connection = new URL(url).openConnection();

            response = new StringBuilder();
            String inputLine;

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
        } catch (IOException ex) {
            String msg = "GoogleCrawler couldn't crawl";
            Logger.getAnonymousLogger().log(Level.WARNING, msg);
            throw new CrawlingRuntimeException(msg, ex);
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
