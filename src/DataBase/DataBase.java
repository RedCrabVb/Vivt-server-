package DataBase;

import com.google.gson.JsonObject;

import java.sql.SQLException;

public interface DataBase {
    int realAccount(String login, String password);
    JsonObject personData(int ID) throws SQLException;
    JsonObject schedule(int ID) throws SQLException;
    JsonObject news() throws SQLException;
    JsonObject importantDates() throws SQLException;
    int getId(String login);

}
