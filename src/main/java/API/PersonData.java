package API;

import Server.Server;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Map;

public class PersonData implements  Command {
    @Override
    public JsonObject execute(Map<String, String> params) throws SQLException {
        return Server.dataBase.personData(HandlerAPI.getIDForParameters(params));
    }
}
