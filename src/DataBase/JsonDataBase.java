package DataBase;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.SQLException;

public class JsonDataBase implements DataBase {
    private JsonObject baseJson;
    public JsonDataBase() {
        baseJson = new JsonObject();

        JsonArray users = new JsonArray();
        users.add("testPass");
        users.add("redcrabTest");

        baseJson.add("users", users);
    }
    @Override
    public int realAccount(String login, String password) {
        for (var user : baseJson.getAsJsonArray("users")) {
            if (user.getAsString().equals(login+password)){
                return 1;
            }
        }

        return -1;
    }

    @Override
    public JsonObject personData(int ID) throws SQLException {
        return null;
    }

    @Override
    public JsonObject schedule(int ID) throws SQLException {
        return null;
    }

    @Override
    public JsonObject news() throws SQLException {
        return null;
    }

    @Override
    public JsonObject importantDates() throws SQLException {
        return null;
    }

    @Override
    public int getId(String login) {
        return 0;
    }
}
