package API;

import Server.Server;
import com.google.gson.JsonObject;

import java.util.Map;

public class SendMessage implements Command {
    @Override
    public JsonObject execute(Map<String, String> params) throws Exception {
        int recipient = Server.dataBase.getIDbyMail(params.get("recipient"));
        int from_whom = Server.dataBase.getIDbyMail(params.get("from_whom"));
        String header = params.get("header");
        String text = params.get("text");

        boolean result = Server.dataBase.sendMessage(recipient, from_whom, text, header);
        JsonObject json = new JsonObject();
        json.addProperty("result", result);

        return json;
    }
}
