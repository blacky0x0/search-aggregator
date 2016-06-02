package com.blacky.sa;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testGetJsonObject() throws Exception {
        JSONObject json = Main.getJsonObject("key", "value");

        assertTrue(json.has("key"));
        assertEquals("value", json.getString("key"));
        assertEquals(1, json.length());
    }

}