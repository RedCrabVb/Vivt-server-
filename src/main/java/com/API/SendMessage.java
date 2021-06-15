package com.API;

import com.DataBase.DataBase;
import com.Server.Server;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Map;

public class SendMessage implements Command {
    private DataBase dataBase;

    public SendMessage(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        int recipient = dataBase.getIDbyMail(params.get("recipient"));
        int from_whom = dataBase.getIDbyMail(params.get("from_whom"));
        String header = params.get("header");
        String text = params.get("text");

        boolean result = dataBase.sendMessage(recipient, from_whom, text, header);
        JsonObject json = new JsonObject();
        json.addProperty("result", result);

        Server.sendPushNotifications(Arrays.asList(new String[]{Server.tokenTest}), header, text);

        return json;
    }
}
