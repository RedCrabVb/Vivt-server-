package API.Command;

import DataBase.ClientInfo;
import com.google.gson.JsonObject;

public interface Command {
    JsonObject execute(ClientInfo client, JsonObject json) throws Exception;
}
