package Server;

import DataBase.ClientInfo;
import DataBase.CreationJson;
import com.google.gson.JsonObject;

import java.net.Socket;
import java.util.logging.Level;

/*
* The thread that communicates with
* the client handles its requests.
*
* If you know how best to solve this problem,
* please let the author know.
* */
public class ClientThread extends Thread {
    public ClientInfo clientInfo = new ClientInfo();
    private final DataTransfer dataTransfer;

    public ClientThread(Socket socket) {
        try {
            socket.setSoTimeout(1000 * 1000);
        } catch (Exception e) {
            ServerControl.LOGGER.log(Level.WARNING, "Error receiving flow " + e);
        }

        this.dataTransfer = new DataTransfer(socket);
        start();
    }

    @Override
    public void run() {
        try {
            while (!dataTransfer.isClosed()) {
                JsonObject jsonFromClient = dataTransfer.getJson();
                Server.switchCommand.execute(this, jsonFromClient);
            }
        } catch (IllegalStateException e) {
            ServerControl.LOGGER.log(Level.WARNING, "Error during connection " + e);
            close(e.toString());
        } finally {
            downService(clientInfo.getReasonDownService());
        }
    }

    private void downService(String reason) {
        JsonObject reasonDisconnect = CreationJson.message("downService", reason);

        dataTransfer.sendJson(reasonDisconnect.toString());
        dataTransfer.close();
        //Server.removeUser(this);
        Thread.currentThread().interrupt();
    }

    public void sendMessage(JsonObject message){
        dataTransfer.sendJson(message.toString());
    }

    public void close(String reason) {
        System.out.println("Close, reason: " + reason);
    }
}
