package com.blacky.sa.crawler;

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
    public List<SearchResult> search(String searchPhrase) throws IOException, JSONException {
        List<SearchResult> resultList = new ArrayList<>();

        String encodedSearchPhrase = URLEncoder.encode(searchPhrase, StandardCharsets.UTF_8.name());
        String bingUrl = String.format(urlPattern, size, encodedSearchPhrase);

        URLConnection connection = new URL(bingUrl).openConnection();
        connection.setRequestProperty("Authorization", "Basic " + apiKeyEnc);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(getStream(connection.getInputStream())))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            JSONObject json = new JSONObject(response.toString());
            JSONObject d = json.getJSONObject("d");
            JSONArray results = d.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String resultUrl = result.getString("Url");
                String title = result.getString("Title");
                resultList.add(new SearchResult(i + 1, "Bing", title, resultUrl));
            }
        }

        return resultList;
    }

    public InputStream getStream(InputStream is) {
        return is;
    }

    public int getSize() {
        return size;
    }

    public String getApiKey() {
        return apiKey;
    }

}
