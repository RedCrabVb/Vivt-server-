package DataBase;

import Server.ServerControl;
import com.google.gson.JsonObject;
import com.vivt.Config;

import java.sql.*;
import java.util.logging.Level;

public class MySqlDataBase implements DataBase {
    private final Connection connects;

    public MySqlDataBase(Config config) throws SQLException {
        String url = String.format("jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false",
                config.getServerNameDB(), config.getPortParameterDB(), config.getDatabaseNameParameterDB());

        connects = DriverManager.getConnection(url, config.getUserParameterDB(), config.getPasswordParameterDB());
    }


    @Override
    public int realAccount(String login, String password) {
        int ID = -1;
        try {
            String query = "SELECT ID FROM users WHERE username = ? AND password = ?;";
            PreparedStatement st = connects.prepareStatement(query);
            st.setString(1, login);
            st.setString(2, password);
            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {
                ID = resultSet.getInt("ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServerControl.LOGGER.log(Level.WARNING, "Error trying to get an account", e);
        }

        return ID;
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
