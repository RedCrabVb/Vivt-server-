package API.Command;

import Server.ClientThread;
import Server.Server;
import com.google.gson.JsonObject;

public class Message implements Command {
    @Override
    public JsonObject execute(ClientThread client, JsonObject json) throws Exception {
        return Server.dataBase.message(client.clientInfo.getID());
    }
}
