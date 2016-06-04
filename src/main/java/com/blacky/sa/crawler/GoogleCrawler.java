package com.blacky.sa.crawler;

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
        List<SearchResult> resultList = new ArrayList<>();

        String encodedSearchPhrase = URLEncoder.encode(searchPhrase, StandardCharsets.UTF_8.name());
        String googleUrl = String.format(urlPattern, apiKey, cx, size, encodedSearchPhrase);

        URL url = new URL(googleUrl);
        URLConnection connection = url.openConnection();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(getStream(connection.getInputStream())))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            JSONObject json = new JSONObject(response.toString());
            JSONArray items = json.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject result = items.getJSONObject(i);
                String resultUrl = result.getString("link");
                String title = result.getString("title");
                resultList.add(new SearchResult(i + 1, "Google", title, resultUrl));
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

    public String getCx() {
        return cx;
    }
}
