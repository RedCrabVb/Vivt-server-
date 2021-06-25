package com.dataBase;

import com.google.gson.JsonObject;

public class CreationJson {
    public static JsonObject data(String field, String data) {
        JsonObject message = new JsonObject();
        message.addProperty(field, data);
        return message;
    }
}
