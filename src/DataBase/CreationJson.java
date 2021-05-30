package DataBase;

import com.google.gson.JsonObject;

public class CreationJson {
    public static JsonObject message(String header, String data) {
        JsonObject message = new JsonObject();
        message.addProperty("data", data);
        return templateData(header, message);
    }

    public static JsonObject personData(String FIO,
                                        String group,
                                        String studentsBook,
                                        String mail) {
        JsonObject json = new JsonObject();
        json.addProperty("FIO", FIO);
        json.addProperty("group", group);
        json.addProperty("studentsBook", studentsBook);
        json.addProperty("mail", mail);

        return templateData("personData", json);
    }

    /*public static JsonObject schedule(JsonObject data) {
        return templateData("schedule", data);
    }

    public static JsonObject news(JsonObject data) {
        return templateData("news", data);
    }

    public static JsonObject importantDates(JsonObject data) {
        return templateData("importantDates", data);
    }*/

    public static JsonObject templateData(String header, JsonObject data){
        JsonObject json = new JsonObject();
        json.addProperty("header", header);
        json.add("data", data);
        return json;
    }
}
