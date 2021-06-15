package com.API;

import com.DataBase.DataBase;
import com.Server.Server;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Map;

public class GetSchedule implements Command {
    private DataBase dataBase;
    private Server server;

    public GetSchedule(DataBase dataBase, Server server) {
        this.dataBase = dataBase;
        this.server = server;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws SQLException {
        int ID = server.getIDForParameters(params);
        return dataBase.schedule(ID);
    }
}
