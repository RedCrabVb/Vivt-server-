package API;

import Server.Server;
import com.google.gson.JsonObject;

import java.util.Map;

public class Message implements Command {
    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        return Server.dataBase.message(HandlerAPI.getIDForParameters(params));
    }
}
