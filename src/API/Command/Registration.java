package API.Command;

import Server.ClientThread;
import DataBase.CreationJson;
import com.google.gson.JsonObject;

public class Registration implements Command {
    @Override
    public JsonObject execute(ClientThread client, JsonObject json) {
        String login = json.get("login").getAsString();
        String password = json.get("password").getAsString();
        client.clientInfo.setIsRealAccount(login, password);
        return  CreationJson.message("registration", client.clientInfo.isAuthorized() + "");
    }
}
