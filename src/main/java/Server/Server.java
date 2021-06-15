package Server;

import API.*;
import DataBase.DataBase;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;
import com.vivt.Config;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.logging.Level;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;


/*
 * The class is responsible for storing data, and application state,
 * by the way, you are a cool hacker, hold my cup of coffee, axaxa
 */
public class Server {
    private static HttpServer server;
    private static final String apiKey = "AAAAHf4SliA:APA91bHpj0yrmmlORa4Zt_8-zx8ROEJutjfghnvdYGxDxQC0itHMOoOQf31XzsY6vUIffKb9tZU1A_xKq7etdCm6xIpmpm4cwjugerxzOENPFY8jm8o_D95Dnhvh7n-Sv1oz24uwKjax";

    private static boolean isActive = false;

    public static DataBase dataBase;
    public final static String tokenTest = "dG8qfPtBD6r60NQw-6r-qa:APA91bGeR0IHj7QqxO3lr5uGtPdd4ix_0VQqGRMjz8tcGyLJWqZstjmyzWGZUPubhmsnzEwYJvZB-XjesbLyd6FlMhzIvqkbm5dJqgRv0BVGzo2FjL1e9IlU543PNcEx3zB_JagdFA2K";


    public Server(int port) throws Exception {
        Server.dataBase = Config.databaseCreate(Config.getInstance());

        isActive = true;

        server = HttpServer.create(new InetSocketAddress(port), 0);

        Server.run();
    }

    private static void run() {
        server.createContext("/api/message", new HandlerAPI(new API.GetMessage()));
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

    public static void sendPushNotifications(List<String> tokens, String title, String body) throws Exception {
        for (String token : tokens) {
            org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
            post.setHeader("Content-type", "application/json");
            post.setHeader("Authorization", String.format("key=%s", Server.apiKey));
            JsonObject message = new JsonObject();
            message.addProperty("to", token);
            message.addProperty("priority", "high");
            JsonObject notification = new JsonObject();
            notification.addProperty("title", title);
            notification.addProperty("body", body);
            message.add("notification", notification);
            post.setEntity(new StringEntity(message.toString(), "UTF-8"));
            org.apache.http.HttpResponse response = client.execute(post);

            System.out.println(response);
            System.out.println(message);
        }
    }
}
