package com.blacky.sa;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws JSONException {
        Logger.getAnonymousLogger().info(getJsonObject("serialized", "json").toString());
    }

    public static JSONObject getJsonObject(String key, String value) throws JSONException {
        return new JSONObject(String.format("{'%s':'%s'}", key, value));
    }

}
