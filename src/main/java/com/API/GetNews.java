package com.API;

import com.DataBase.DataBase;
import com.Server.Server;
import com.google.gson.JsonObject;

import java.util.Map;

public class GetNews implements Command {
    private DataBase dataBase;

    public GetNews(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        return dataBase.news();
    }
}
