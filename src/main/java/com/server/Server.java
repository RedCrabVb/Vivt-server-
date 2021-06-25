package com.server;

import com.api.*;
import com.dataBase.DataBase;

import com.google.api.client.repackaged.com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.logging.Level;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/*
 * The class is responsible for storing data, and application state,
 * by the way, you are a cool hacker, hold my cup of coffee, axaxa
 */
public class Server {
    private static Server server = null;
    private HttpServer serverHttp;
    private DataBase dataBase;

    @Autowired
    @Qualifier("apiGetMessage")
    private HandlerAPI apiGetMessage;
    @Autowired
    @Qualifier("apiGetPersonData")
    private HandlerAPI apiGetPersonData;
    @Autowired
    @Qualifier("apiGetNews")
    private HandlerAPI apiGetNews;
    @Autowired
    @Qualifier("apiGetSchedule")
    private HandlerAPI apiGetSchedule;
    @Autowired
    @Qualifier("apiAuthorization")
    private HandlerAPI apiAuthorization;
    @Autowired
    @Qualifier("apiRegistration")
    private HandlerAPI apiRegistration;
    @Autowired
    @Qualifier("apiSendMessage")
    private HandlerAPI apiSendMessage;

    public final static String tokenTest = "dG8qfPtBD6r60NQw-6r-qa:APA91bGeR0IHj7QqxO3lr5uGtPdd4ix_0VQqGRMjz8tcGyLJWqZstjmyzWGZUPubhmsnzEwYJvZB-XjesbLyd6FlMhzIvqkbm5dJqgRv0BVGzo2FjL1e9IlU543PNcEx3zB_JagdFA2K";
    private final static String apiKey = "AAAAHf4SliA:APA91bHpj0yrmmlORa4Zt_8-zx8ROEJutjfghnvdYGxDxQC0itHMOoOQf31XzsY6vUIffKb9tZU1A_xKq7etdCm6xIpmpm4cwjugerxzOENPFY8jm8o_D95Dnhvh7n-Sv1oz24uwKjax";

    private SendMessage sendMessage;


    public Server(int port,
                  DataBase dataBase) throws Exception {
        this.dataBase = dataBase;
        this.serverHttp = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void run() {
        this.sendMessage = new SendMessage(dataBase);

        serverHttp.createContext("/api/message", apiGetMessage);
        serverHttp.createContext("/api/person_data", apiGetPersonData);
        serverHttp.createContext("/api/news", apiGetNews);
        serverHttp.createContext("/api/schedule", apiGetSchedule);
        serverHttp.createContext("/api/authorization", apiAuthorization);
        serverHttp.createContext("/api/registration", apiRegistration);
        serverHttp.createContext("/api/send_message", apiSendMessage);
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
        if (query == null) {
            return null;
        }

        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
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
