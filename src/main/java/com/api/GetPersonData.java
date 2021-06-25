package com.api;

import com.dataBase.DataBase;
import com.server.*;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Map;

public class GetPersonData implements  Command {
    private DataBase dataBase;
    private Server server;

    public GetPersonData(DataBase dataBase, Server server) {
        this.dataBase = dataBase;
        this.server = server;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws SQLException {
        return dataBase.personData(server.getIDForParameters(params));
    }
}
