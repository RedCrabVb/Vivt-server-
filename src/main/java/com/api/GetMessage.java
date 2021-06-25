package com.api;

import com.dataBase.DataBase;
import com.server.Server;
import com.google.gson.JsonObject;

import java.util.Map;

public class GetMessage implements Command {
    private DataBase dataBase;
    private Server server;

    public GetMessage(DataBase dataBase, Server server) {
        this.dataBase = dataBase;
        this.server = server;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        return dataBase.message(server.getIDForParameters(params));
    }
}
