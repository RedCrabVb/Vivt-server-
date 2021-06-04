package Server;

import API.Command.*;
import DataBase.DataBase;

import com.vivt.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;

/**
 * Creates streams for new customers, stores some important objects
 */
public class Server {
    private static ServerSocket serverSocket;
    private static Boolean isServerRun = true;

    protected static LinkedList<ClientThread> serverList = new LinkedList<>();
    public static SwitchCommand switchCommand = new SwitchCommand();
    public static DataBase dataBase;

    public Server(ServerSocket serverSocket) throws Exception {
        Server.switchCommand.register("registration", new Registration());
        Server.switchCommand.register("person data", new PersonData());
        Server.switchCommand.register("message", new Message());
        Server.switchCommand.register("news", new News());
        Server.switchCommand.register("schedule", new Schedule());
        Server.switchCommand.register("null", new CloseConnection());

        Server.dataBase = Config.databaseCreate(Config.getInstance());

        Server.serverSocket = serverSocket;
        Server.run();
    }

    public static void shutdowns() throws IOException {
        for (ClientThread ct : serverList) {
            ct.clientInfo.setReasonDownService("shutdowns server");
            ct.interrupt();
        }

        serverSocket.close();
        isServerRun = false;
    }

    private static void run() {
        try {
            ServerControl.LOGGER.log(Level.INFO, "Server start " + new Date().toString());
            while (isServerRun) {
                Socket socket = serverSocket.accept();
                serverList.add(new ClientThread(socket));
            }
        } catch (Exception e) {
            ServerControl.LOGGER.log(Level.WARNING, "The server crashes when adding a client " + e);
        } finally {
            ServerControl.LOGGER.log(Level.INFO, "Server stop " + new Date().toString());
        }
    }
}
