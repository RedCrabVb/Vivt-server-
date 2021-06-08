package Server;

import API.Command.*;
import DataBase.DataBase;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.cj.xdevapi.Client;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.vivt.Config;
import DataBase.ClientInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.logging.Level;

/**
 * Creates streams for new customers, stores some important objects
 */
public class Server {
    private static HttpServer server;
    private static Boolean isServerRun = true;

    protected static LinkedList<ClientInfo> serverList = new LinkedList<>();
    public static SwitchCommand switchCommand = new SwitchCommand();
    public static DataBase dataBase;

    public Server(int port) throws Exception {
        Server.switchCommand.register("registration", new Registration());
        Server.switchCommand.register("person data", new PersonData());
        Server.switchCommand.register("message", new Message());
        Server.switchCommand.register("news", new News());
        Server.switchCommand.register("schedule", new Schedule());

        Server.dataBase = Config.databaseCreate(Config.getInstance());

        server = HttpServer.create(new InetSocketAddress(port), 0);
        Server.run();
    }

    public static ClientInfo getClient(String token) {
        for (ClientInfo client : serverList) {
            if (client.getToken().equals(token)) { ;
                return client;
            } else {
                System.out.println(client.getToken());
            }
        }

        return null;
    }

    private static void run() throws IOException {
        ClientInfo clientTestApi = new ClientInfo();
        clientTestApi.setIsRealAccount("mail", "pass");
        clientTestApi.setToken("test");
        serverList.add(clientTestApi);

        server.createContext("/api", new Handler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void shutdowns() {
        server.stop(1);
        ServerControl.LOGGER.log(Level.INFO, "Server stop " + new Date().toString());
    }

    static class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ServerControl.LOGGER.log(Level.INFO, "get url: " + exchange.getRequestURI().getQuery());

            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            String token = params.get("token");
            JsonObject json = JsonParser.parseString(params.get("json")).getAsJsonObject();

            switchCommand.execute(token, json);

            String response = switchCommand.execute(token, json).toString();
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            ServerControl.LOGGER.log(Level.INFO, "get json: " + params.get("json"));
            ServerControl.LOGGER.log(Level.INFO, "send json: " + response);
        }
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
}
