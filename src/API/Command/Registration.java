package API.Command;

import DataBase.ClientInfo;
import DataBase.CreationJson;
import com.google.gson.JsonObject;

public class Registration implements Command {
    @Override
    public JsonObject execute(ClientInfo client, JsonObject json) {
        String login = json.get("login").getAsString();
        String password = json.get("password").getAsString();
        client.setIsRealAccount(login, password);
        return  CreationJson.data(client.isAuthorized() + "");
    }
}
