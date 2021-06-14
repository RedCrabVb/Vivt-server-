package Server;

import API.*;
import DataBase.DataBase;

import com.sun.net.httpserver.HttpServer;
import com.vivt.Config;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.logging.Level;

/**
 * Creates streams for new customers, stores some important objects
 */
public class Server {
    private static HttpServer server;
    private static boolean isActive = false;

    public static DataBase dataBase;

    public Server(int port) throws Exception {
        Server.dataBase = Config.databaseCreate(Config.getInstance());

        isActive = true;

        server = HttpServer.create(new InetSocketAddress(port), 0);

        Server.run();
    }

    private static void run() {
        server.createContext("/api/message", new HandlerAPI(new Message()));
        server.createContext("/api/person_data", new HandlerAPI(new PersonData()));
        server.createContext("/api/news", new HandlerAPI(new News()));
        server.createContext("/api/schedule", new HandlerAPI(new Schedule()));
        server.createContext("/api/authorization", new HandlerAPI(new Authorization()));
        server.createContext("/api/registration", new HandlerAPI(new Registration()));
        server.createContext("/api/send_message", new HandlerAPI(new SendMessage()));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void close() {
        server.stop(1);
        isActive = false;
        ServerControl.LOGGER.log(Level.INFO, "Server stop " + new Date().toString());
    }

    public static boolean isActive() {
        return isActive;
    }
}
