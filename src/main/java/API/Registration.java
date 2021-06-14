package API;

import DataBase.CreationJson;
import DataBase.DataBase;
import Server.Server;
import com.google.gson.JsonObject;

import java.util.Locale;
import java.util.Map;

public class Registration implements Command {
    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        Server.dataBase.registration(params.get("name"), params.get("surname"),
                params.get("patronymic"), params.get("groups"),
                params.get("mail").toLowerCase(Locale.ROOT), params.get("password"));


        String login = params.get("mail").toLowerCase(Locale.ROOT);
        String password = params.get("password");
        String token = Server.dataBase.authorization(login, password);

        return CreationJson.data("token", token);
    }
}
