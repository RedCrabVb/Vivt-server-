package DataBase;

import com.google.gson.JsonObject;

public class CreationJson {
    public static JsonObject data(String data) {
        JsonObject message = new JsonObject();
        message.addProperty("data", data);
        return message;
    }

    public static JsonObject templateData(String header, JsonObject data){
        JsonObject json = new JsonObject();
        json.addProperty("header", header);
        json.add("body", data);
        return json;
    }
}
