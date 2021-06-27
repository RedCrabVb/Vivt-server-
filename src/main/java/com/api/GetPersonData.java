package com.api;

import com.dataBase.DataBase;
import com.google.gson.JsonObject;
import com.server.Server;

import java.util.Map;

public class GetPersonData implements  Command {
    private DataBase dataBase;
    private Server server;

    public GetPersonData(DataBase dataBase, Server server) {
        this.dataBase = dataBase;
        this.server = server;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        return dataBase.personData(server.getIDForParameters(params));
    }
}
