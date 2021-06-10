package API;

import Server.Server;
import com.google.gson.JsonObject;

import java.util.Map;

public class PersonData implements  Command {
    @Override
    public JsonObject execute(Map<String, String> params) {
        try {
            return Server.dataBase.personData(HandlerAPI.getIDForParameters(params));
        } catch (Exception e) {
            return new JsonObject();
        }
    }
}
