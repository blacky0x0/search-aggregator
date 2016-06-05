package com.blacky.sa.crawler.parser;

import com.blacky.sa.exception.JsonParseRuntimeException;
import com.blacky.sa.model.SearchResult;
import org.json.*;
import java.util.*;
import java.util.logging.*;

public class BingJsonParser {

    public static List<SearchResult> parse(String jsonString) {
        List<SearchResult> serp = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONObject d = json.getJSONObject("d");
            JSONArray results = d.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String resultUrl = result.getString("Url");
                String title = result.getString("Title");
                serp.add(new SearchResult(i + 1, "Bing", title, resultUrl));
            }
        } catch (NullPointerException | JSONException ex) {
            String msg = String.format("BingJsonParser couldn't parse string: %s", jsonString);
            Logger.getAnonymousLogger().log(Level.SEVERE, msg);
            throw new JsonParseRuntimeException(msg, ex);
        }

        return serp;
    }
}
