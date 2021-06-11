package API;

import DataBase.CreationJson;
import DataBase.DataBase;
import Server.Server;
import com.google.gson.JsonObject;

import java.util.Map;

public class Registration implements Command {
    @Override
    public JsonObject execute(Map<String, String> params) {
        try {
            Server.dataBase.registration(params.get("name"), params.get("surname"),
                    params.get("patronymic"), params.get("groups"),
                    params.get("mail"), params.get("password"));


            String login = params.get("mail");
            String password = params.get("password");
            String token = Server.dataBase.authorization(login, password);

            return CreationJson.data("token", token);
        } catch (Exception e) {
            return new JsonObject();
        }
    }
}
