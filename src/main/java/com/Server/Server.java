package com.Server;

import com.API.*;
import com.DataBase.DataBase;

import com.DataBase.JsonDataBase;
import com.google.api.client.repackaged.com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;

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
    private static Server server = null;
    private HttpServer serverHttp;
    private DataBase dataBase;

    public final static String tokenTest = "dG8qfPtBD6r60NQw-6r-qa:APA91bGeR0IHj7QqxO3lr5uGtPdd4ix_0VQqGRMjz8tcGyLJWqZstjmyzWGZUPubhmsnzEwYJvZB-XjesbLyd6FlMhzIvqkbm5dJqgRv0BVGzo2FjL1e9IlU543PNcEx3zB_JagdFA2K";
    private final static String apiKey = "AAAAHf4SliA:APA91bHpj0yrmmlORa4Zt_8-zx8ROEJutjfghnvdYGxDxQC0itHMOoOQf31XzsY6vUIffKb9tZU1A_xKq7etdCm6xIpmpm4cwjugerxzOENPFY8jm8o_D95Dnhvh7n-Sv1oz24uwKjax";

    private SendMessage sendMessage;

    private Server(int port, DataBase dataBase) throws Exception {
        this.dataBase = dataBase;
        this.serverHttp = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public static Server getInstance() {
        return Server.server;
    }

    public static Server getInstance(int port, DataBase dataBase) throws Exception {
        if (Server.server == null) {
            Server.server = new Server(port, dataBase);
        }

        return Server.server;
    }

    public void run() {
        this.sendMessage = new SendMessage(dataBase);
        serverHttp.createContext("/api/message", new HandlerAPI(new GetMessage(dataBase, this), this));
        serverHttp.createContext("/api/person_data", new HandlerAPI(new GetPersonData(dataBase, this), this));
        serverHttp.createContext("/api/news", new HandlerAPI(new GetNews(dataBase), this));
        serverHttp.createContext("/api/schedule", new HandlerAPI(new GetSchedule(dataBase, this), this));
        serverHttp.createContext("/api/authorization", new HandlerAPI(new Authorization(dataBase), this));
        serverHttp.createContext("/api/registration", new HandlerAPI(new Registration(dataBase), this));
        serverHttp.createContext("/api/send_message", new HandlerAPI(this.sendMessage, this));
        serverHttp.setExecutor(null); // creates a default executor
        serverHttp.start();
    }

    public void close() {
        serverHttp.stop(1);

        ServerControl.LOGGER.log(Level.INFO, "Server stop " + new Date().toString());
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

    public Map<String, String> queryToMap(String query) {
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

    public int getIDForParameters(Map<String, String> params) {
        String token = params.get("token");
        int userID = dataBase.getIDForToken(token);
        return userID;
    }

    @VisibleForTesting
    void sendMessage(String recipient, String sender, String title, String body) {
        String paramEnc = String.format("recipient=%s&from_whom=%s&header=%s&text=%s", recipient, sender, title, body);
        try {
            sendMessage.execute(queryToMap(paramEnc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
