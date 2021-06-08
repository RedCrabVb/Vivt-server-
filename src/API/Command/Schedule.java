package API.Command;

import DataBase.ClientInfo;
import Server.Server;
import com.google.gson.JsonObject;

public class Schedule implements Command {
    @Override
    public JsonObject execute(ClientInfo client, JsonObject json) throws Exception {
        return Server.dataBase.schedule(client.getID());
    }
}
