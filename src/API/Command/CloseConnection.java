package API.Command;

import DataBase.CreationJson;
import Server.ClientThread;
import com.google.gson.JsonObject;

public class CloseConnection implements Command {
    @Override
    public JsonObject execute(ClientThread client, JsonObject json) throws Exception {
        client.clientInfo.setReasonDownService("null");
        client.close();
        return new JsonObject();
    }
}
