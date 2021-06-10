package API.Command;

import DataBase.ClientInfo;
import com.google.gson.JsonObject;

import java.util.Map;

public interface Command {
    JsonObject execute(ClientInfo client, Map<String, String> params);
}
