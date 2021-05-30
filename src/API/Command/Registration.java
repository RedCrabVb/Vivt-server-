package API.Command;

import Server.ClientThread;
import com.google.gson.JsonObject;

public class Registration implements Command {
    @Override
    public JsonObject execute(ClientThread client, JsonObject json) {
        return client.createMessage("registration", "true");
    }
}
