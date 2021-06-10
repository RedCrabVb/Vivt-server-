package API.Command;

import DataBase.ClientInfo;
import com.google.gson.JsonObject;

import java.util.Map;

public class Authorization implements Command {
    @Override
    public JsonObject execute(ClientInfo client, Map<String, String> params) {
        return null;
    }
}
