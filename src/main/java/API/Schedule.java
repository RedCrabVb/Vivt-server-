package API;

import Server.Server;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Map;

public class Schedule implements Command {
    @Override
    public JsonObject execute(Map<String, String> params) throws SQLException {
        int ID = HandlerAPI.getIDForParameters(params);
        return Server.dataBase.schedule(ID);
    }
}
