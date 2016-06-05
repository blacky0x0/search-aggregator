package com.blacky.sa.crawler;

import com.blacky.sa.crawler.parser.BingJsonParser;
import com.blacky.sa.model.SearchResult;
import org.json.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    public List<SearchResult> search(String searchPhrase) throws IOException, JSONException {

        String encodedSearchPhrase = URLEncoder.encode(searchPhrase, StandardCharsets.UTF_8.name());
        String bingUrl = String.format(urlPattern, size, encodedSearchPhrase);

        return BingJsonParser.parse(getResponse(bingUrl));
    }

    public String getResponse(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Basic " + apiKeyEnc);

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

}
