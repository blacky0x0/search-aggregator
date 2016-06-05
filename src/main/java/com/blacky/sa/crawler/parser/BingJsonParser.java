package com.blacky.sa.crawler.parser;

import com.blacky.sa.model.SearchResult;
import org.json.*;
import java.util.*;

public class BingJsonParser {

    public static List<SearchResult> parse(String jsonString) throws JSONException {
        List<SearchResult> serp = new ArrayList<>();

        JSONObject json = new JSONObject(jsonString);
        JSONObject d = json.getJSONObject("d");
        JSONArray results = d.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            String resultUrl = result.getString("Url");
            String title = result.getString("Title");
            serp.add(new SearchResult(i + 1, "Bing", title, resultUrl));
        }

        return serp;
    }
}
