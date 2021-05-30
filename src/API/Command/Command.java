package API.Command;

import Server.ClientThread;
import com.google.gson.JsonObject;

public interface Command {
    JsonObject execute(ClientThread client, JsonObject json) throws Exception;
}
