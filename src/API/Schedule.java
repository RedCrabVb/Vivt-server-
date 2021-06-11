package API;

import Server.Server;
import com.google.gson.JsonObject;

import java.util.Map;

public class Schedule implements Command {
    @Override
    public JsonObject execute(Map<String, String> params) {
        try {
            int ID = HandlerAPI.getIDForParameters(params);
            return Server.dataBase.schedule(ID);
        } catch (Exception e) {
            return new JsonObject();
        }
    }
}
