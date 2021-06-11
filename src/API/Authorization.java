package API;

import DataBase.CreationJson;
import Server.Server;
import com.google.gson.JsonObject;

import java.util.Map;

public class Authorization implements Command {
    @Override
    public JsonObject execute(Map<String, String> params) {
        try {
            String login = params.get("login");
            String password = params.get("password");

            String token = Server.dataBase.authorization(login, password);

            return CreationJson.data("token", token);
        } catch (Exception e) {
            return new JsonObject();
        }
    }
}
