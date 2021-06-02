package API.Command;

import Server.ClientThread;
import Server.Server;
import com.google.gson.JsonObject;

public class PersonData implements  Command {
    @Override
    public JsonObject execute(ClientThread client, JsonObject json) throws Exception {
        return Server.dataBase.personData(client.clientInfo.getID());
    }
}
