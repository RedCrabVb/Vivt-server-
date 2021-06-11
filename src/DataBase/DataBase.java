package DataBase;

import com.google.gson.JsonObject;

import java.sql.SQLException;

public interface DataBase {
    String authorization(String login, String password) throws Exception;
    void registration(String name, String surname, String patronymic, String groups, String mail, String password);

    int getIDForToken(String token);

    JsonObject personData(int ID) throws SQLException;
    JsonObject schedule(int ID) throws SQLException;
    JsonObject news() throws SQLException;
    JsonObject importantDates() throws SQLException;
    JsonObject message(int ID) throws SQLException;
    JsonObject academicPerformance(int ID) throws SQLException;
}
