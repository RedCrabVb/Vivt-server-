package Server;

import API.Command.SwitchCommand;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;

/**
 * Creates streams for new customers, stores some important objects
 */
public class Server {
    //private static DataChecking dataChecking;
    private static ServerSocket serverSocket;
    private static Boolean isServerRun = true;

    protected static LinkedList<ClientThread> serverList = new LinkedList<>();
    public static SwitchCommand switchCommand = new SwitchCommand();

    public Server(ServerSocket serverSocket) throws Exception {
        Server.serverSocket = serverSocket;
        Server.main();
    }

    public static boolean loginIn(ClientThread client) {
        return false;//dataChecking.check(client);
    }

    public static void shutdowns() throws IOException {
        for (ClientThread ct : serverList) {
            ct.close("shutdowns server");
            ct.interrupt();
        }

        serverSocket.close();
        isServerRun = false;
    }

    private static void main() {
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
