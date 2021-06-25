package com.dataBase;

import com.server.ServerControl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.logging.Level;

public class MySqlDataBase implements DataBase {
    private final Connection connects;

    @Deprecated
    public MySqlDataBase(String serverName, String port, String databaseName, String userDB, String usersPasswordDB) throws SQLException {
        String url = String.format("jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false",
                serverName, port, databaseName);

        connects = DriverManager.getConnection(url, userDB, usersPasswordDB);
    }


    @Override
    public String authorization(String login, String password) {
        String token = "";
        try {
            String query = "SELECT ID, token FROM students WHERE mail = ? AND password = ?;";
            PreparedStatement st = connects.prepareStatement(query);
            st.setString(1, login);
            st.setString(2, password);
            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {
                token = resultSet.getString("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServerControl.LOGGER.log(Level.WARNING, "Error trying to get an account", e);
        }

        return token;
    }

    @Override
    public void registration(String name, String surname, String patronymic, String groups, String mail, String password) {
    }

    @Override
    public int getIDForToken(String token) {
        return 0;
    }

    @Override
    public JsonObject personData(int ID) throws SQLException {
        String query = "SELECT mail, grade_book_number, name_groups, FIO FROM students " +
                "JOIN groups g on g.ID = students.groups_ID WHERE students.ID = ?;";

        PreparedStatement st = connects.prepareStatement(query);
        st.setString(1, String.valueOf(ID));

        ResultSet rs = st.executeQuery();
        rs.next();
        ResultSetMetaData rsmd = rs.getMetaData();

        int numColumns = rsmd.getColumnCount();
        JsonObject obj = new JsonObject();
        for (int i = 1; i <= numColumns; i++) {
            String column_name = rsmd.getColumnName(i);
            try {
                obj.addProperty(column_name, rs.getObject(column_name).toString());
            } catch (Exception e) {
                obj.addProperty(column_name, "");
            }
        }

        return obj;
    }

    @Override
    public JsonObject schedule(int ID) throws SQLException {
        return new JsonObject();
    }

    @Override
    public JsonObject news() throws SQLException {
        String query = "select * from news;";

        PreparedStatement st = connects.prepareStatement(query);

        JsonArray arr = new JsonArray();

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();

            int numColumns = rsmd.getColumnCount();

            JsonObject obj = new JsonObject();
            for (int i = 1; i <= numColumns; i++) {
                String column_name = rsmd.getColumnName(i);
                try {
                    obj.addProperty(column_name, rs.getObject(column_name).toString());
                } catch (Exception e) {
                    obj.addProperty(column_name, "");
                }
            }

            arr.add(obj);
        }

        JsonObject res = new JsonObject();
        res.add("arr", arr);

        return res;
    }

    @Override
    public JsonObject importantDates() throws SQLException {
        return new JsonObject();
    }

    @Override
    public JsonObject message(int ID) throws SQLException {
        return new JsonObject();
    }

    @Override
    public JsonObject academicPerformance(int ID) throws SQLException {
        return new JsonObject();
    }

    @Override
    public boolean sendMessage(int sender, int recipient, String header, String text) {
        return false;
    }

    @Override
    public int getIDbyMail(String name) {
        return 0;
    }
}
