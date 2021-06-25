package com.api;

import com.dataBase.CreationJson;
import com.dataBase.DataBase;
import com.google.gson.JsonObject;

import java.util.Locale;
import java.util.Map;

public class Registration implements Command {
    private DataBase dataBase;

    public Registration(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        dataBase.registration(params.get("name"), params.get("surname"),
                params.get("patronymic"), params.get("groups"),
                params.get("mail").toLowerCase(Locale.ROOT), params.get("password"));


        String login = params.get("mail").toLowerCase(Locale.ROOT);
        String password = params.get("password");
        String token = dataBase.authorization(login, password);

        return CreationJson.data("token", token);
    }
}
