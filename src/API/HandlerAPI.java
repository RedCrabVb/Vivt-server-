package API;

import Server.ServerControl;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import Server.Server;

public class HandlerAPI implements HttpHandler {
    private Command command;

    public HandlerAPI(Command command) {
        this.command = command;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ServerControl.LOGGER.log(Level.INFO, "get url: " + exchange.getRequestURI().getQuery());

        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());

        String response =  command.execute(params).toString();
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

        ServerControl.LOGGER.log(Level.INFO, "get json: " + params.get("json"));
        ServerControl.LOGGER.log(Level.INFO, "send json: " + response);
    }

    public static Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

    public static int getIDForParameters(Map<String, String> params) {
        String token = params.get("token");
        int userID = Server.dataBase.getIDForToken(token);
        return userID;
    }
}