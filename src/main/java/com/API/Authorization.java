package com.API;

import com.DataBase.CreationJson;
import com.DataBase.DataBase;
import com.Server.Server;
import com.google.gson.JsonObject;

import java.util.Locale;
import java.util.Map;

public class Authorization implements Command {
    private DataBase dataBase;

    public Authorization(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        String login = params.get("login").toLowerCase(Locale.ROOT);
        String password = params.get("password");

        String token = dataBase.authorization(login, password);

        return CreationJson.data("token", token);
    }
}
