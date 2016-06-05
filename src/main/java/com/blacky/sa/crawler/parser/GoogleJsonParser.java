package com.blacky.sa.crawler.parser;

import com.blacky.sa.model.SearchResult;
import org.json.*;
import java.util.*;

public class GoogleJsonParser {

    public static List<SearchResult> parse(String jsonString) throws JSONException {
        List<SearchResult> serp = new ArrayList<>();

        JSONObject json = new JSONObject(jsonString);
        JSONArray items = json.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject result = items.getJSONObject(i);
            String resultUrl = result.getString("link");
            String title = result.getString("title");
            serp.add(new SearchResult(i + 1, "Google", title, resultUrl));
        }

        return serp;
    }
}
