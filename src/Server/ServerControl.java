package Server;

import com.vivt.Main;

import java.io.FileInputStream;
import java.util.Locale;
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
    private static boolean showLog = true;
    private final String cls = "/cls",
            info = "/info", logShow = "/logShow", shutdowns = "/shutdowns",
            help = "/help";

    public static Logger LOGGER;

    public ServerControl(String configPath) {
        try (FileInputStream ins = new FileInputStream(configPath)) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        start();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String login;

        String _help = " /cls - clear terminal \n /logShow \n " +
                "/info - server status information \n /shutdowns \n /help - it is reference";
        System.out.println(_help);

        while (true) {
            try {
                switch (scanner.next()) {
                    case cls:
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        break;
                    case info:
                        System.out.println("Active compounds: " + Server.serverList.size());
                        System.out.println("Active streams: " + Thread.getAllStackTraces().keySet().size());
                        break;
                    case logShow:
                        showLog = !showLog;
                        LOGGER.setLevel(!showLog ? Level.OFF : Level.ALL);
                        break;
                    case help:
                        System.out.println(_help);
                        break;
                    case shutdowns:
                        Server.shutdowns();
                        return;
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
