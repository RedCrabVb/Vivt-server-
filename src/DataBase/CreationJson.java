package DataBase;

import com.google.gson.JsonObject;

public class CreationJson {
    public static JsonObject message(String header, String data) {
        JsonObject message = new JsonObject();
        message.addProperty("data", data);
        return templateData(header, message);
    }

    public static JsonObject templateData(String header, JsonObject data){
        JsonObject json = new JsonObject();
        json.addProperty("header", header);
        json.add("data", data);
        return json;
    }
}
