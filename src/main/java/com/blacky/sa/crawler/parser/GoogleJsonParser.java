package com.blacky.sa.crawler.parser;

import com.blacky.sa.exception.JsonParseRuntimeException;
import com.blacky.sa.model.SearchResult;
import org.json.*;
import java.util.*;
import java.util.logging.*;

public class GoogleJsonParser {

    public static List<SearchResult> parse(String jsonString) {
        List<SearchResult> serp = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            if (json.isNull("items") && "0".equals(json.getJSONObject("searchInformation").getString("totalResults"))) {
                return serp;
            }

            JSONArray items = json.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject result = items.getJSONObject(i);
                String resultUrl = result.getString("link");
                String title = result.getString("title");
                serp.add(new SearchResult(i + 1, "Google", title, resultUrl));
            }
        } catch (NullPointerException | JSONException ex) {
            String msg = String.format("GoogleJsonParser couldn't parse string: %s", jsonString);
            Logger.getAnonymousLogger().log(Level.SEVERE, msg);
            throw new JsonParseRuntimeException(msg, ex);
        }

        return serp;
    }
}
