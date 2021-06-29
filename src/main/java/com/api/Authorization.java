package com.api;

import com.dataBase.fileDataBase.CreationJson;
import com.dataBase.DataBase;
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
