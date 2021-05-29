package Server;

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
    final int maxNumberAttempts;

    public ClientThread(Socket socket, int maxNumberAttempts) {
        try {
            socket.setSoTimeout(1000 * 1000);
        } catch (Exception e) {
            ServerControl.LOGGER.log(Level.WARNING, "Error receiving flow " + e);
        }

        //this.dataTransfer = new DataTransfer(socket);
        this.maxNumberAttempts = maxNumberAttempts;
        start();
    }

    @Override
    public void run() {
        System.out.println("Run");
    }

    public void close(String reason) {
        System.out.println("Close, reason: " + reason);
    }
}
