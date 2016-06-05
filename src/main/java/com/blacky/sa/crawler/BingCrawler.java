package com.blacky.sa.crawler;

import com.blacky.sa.crawler.parser.BingJsonParser;
import com.blacky.sa.exception.CrawlingRuntimeException;
import com.blacky.sa.model.SearchResult;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.*;

public class BingCrawler implements Crawler {
    public static final String urlPattern = "https://api.datamarket.azure.com/Bing/Search/Web?$top=%d&$skip=0&$format=JSON&Query=%%27%s%%27";

    private final int size = 10;
    private final String apiKey;
    private final String apiKeyEnc;

    /**
     * @param apiKey - primary account key for Bing search API
     */
    public BingCrawler(String apiKey) {
        this.apiKey = apiKey;
        this.apiKeyEnc = Base64.getEncoder().encodeToString((apiKey + ":" + apiKey).getBytes());
    }

    @Override
    public String getName() {
        return "Bing";
    }

    @Override
    public List<SearchResult> search(String searchPhrase) {
        String encodedSearchPhrase, formedUrl;

        try {
            encodedSearchPhrase = URLEncoder.encode(searchPhrase, StandardCharsets.UTF_8.name());
            formedUrl = String.format(urlPattern, size, encodedSearchPhrase);
        } catch (UnsupportedEncodingException ex) {
            String msg = "The UTF8 charset couldn't be found";
            Logger.getAnonymousLogger().log(Level.WARNING, msg);
            throw new CrawlingRuntimeException(msg, ex);
        }

        return BingJsonParser.parse(getResponse(formedUrl));
    }

    public String getResponse(String url) {
        StringBuilder response;

        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Authorization", "Basic " + apiKeyEnc);

            response = new StringBuilder();
            String inputLine;

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
        } catch (IOException ex) {
            String msg = "BingCrawler couldn't crawl";
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

}
