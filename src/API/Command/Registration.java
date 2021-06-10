package API.Command;

import DataBase.ClientInfo;
import DataBase.CreationJson;
import com.google.gson.JsonObject;

import java.util.Map;

public class Registration implements Command {
    @Override
    public JsonObject execute(ClientInfo client, Map<String, String> params) {
        try {
            String login = params.get("login");
            String password = params.get("password");
            client.setIsRealAccount(login, password);
            return CreationJson.data(client.isAuthorized() + "");
        } catch (Exception e) {
            return new JsonObject();
        }
    }
}
