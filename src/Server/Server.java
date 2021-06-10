package Server;

import API.Command.*;
import DataBase.DataBase;

import com.sun.net.httpserver.HttpServer;
import com.vivt.Config;
import DataBase.ClientInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.logging.Level;

/**
 * Creates streams for new customers, stores some important objects
 */
public class Server {
    private static HttpServer server;
    private static Boolean isServerRun = true;

    protected static LinkedList<ClientInfo> serverList = new LinkedList<>();
    public static DataBase dataBase;

    public Server(int port) throws Exception {
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

    private static void run() {
        ClientInfo clientTestApi = new ClientInfo();
        clientTestApi.setIsRealAccount("mail", "pass");
        clientTestApi.setToken("test");
        serverList.add(clientTestApi);

        server.createContext("/api/message", new HandlerAPI(new Message()));
        server.createContext("/api/person_data", new HandlerAPI(new PersonData()));
        server.createContext("/api/news", new HandlerAPI(new News()));
        server.createContext("/api/schedule", new HandlerAPI(new Schedule()));
        server.createContext("/api/registration", new HandlerAPI(new Registration()));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void close() {
        server.stop(1);
        ServerControl.LOGGER.log(Level.INFO, "Server stop " + new Date().toString());
    }
}
