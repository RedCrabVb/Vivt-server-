package com.Server;

import com.API.SendMessage;
import com.DataBase.JsonDataBase;
import com.Vivt.Config;
import com.Vivt.Main;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/*
* Responsible for entering data for the administrator.
* Responsible for logging.
* The single-responsibility principle may be violated
* */
public class ServerControl extends Thread {
    private static boolean showLog = false;
    private final String cls = "/cls",
            info = "/info", sendMessage = "/msg", logShow = "/logShow", close = "/close",
            help = "/help";

    public static Logger LOGGER;

    public ServerControl(String configPath) {
        try (FileInputStream ins = new FileInputStream(configPath)) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        LOGGER.setLevel(!showLog ? Level.OFF : Level.ALL);
        start();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String login;

        String _help = " /cls - clear terminal \n /logShow \n /msg [title] [body] [sender] [recipient]- send message " +
                "/info - server status information \n /close \n /help - it is reference\n";
        System.out.println(_help);

        while (true) {
            try {
                switch (scanner.next()) {
                    case cls:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        break;
                    case info:
                        System.out.println("Active streams: " + Thread.getAllStackTraces().keySet().size());
                        System.out.println("Memory: " + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
                        break;
                    case logShow:
                        showLog = !showLog;
                        LOGGER.setLevel(!showLog ? Level.OFF : Level.ALL);
                        break;
                    case help:
                        System.out.println(_help);
                        break;
                    case close:
                        Server.getInstance(1, Config.databaseCreate(Config.getInstance())).close();
                        return;
                    case sendMessage:
                        String title = scanner.next();
                        String body = scanner.next();
                        String sender = scanner.next();
                        String recipient = scanner.next();

                       // Server.sendPushNotifications(Arrays.asList(new String[]{server.tokenTest}), title, body);
                        String request = String.format("recipient=%s&from_whom=%s&header=%s&text=%s", recipient, sender, title, body);
                        Map<String, String> params = Server.queryToMap(request);
                        new SendMessage(new JsonDataBase("")).execute(params);
                        break;
                    default:
                        System.out.println("What does this command mean?");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error enter data");
            }
        }
    }

}
