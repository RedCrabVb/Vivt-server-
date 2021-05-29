package com.vivt;

import Server.Server;
import Server.ServerControl;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws Exception {
	    Config config = Config.getInstance(args);
        ServerSocket serverSocket = new ServerSocket(config.getServerPort());
        ServerControl serverControl = new ServerControl(config.getLogConfPath());
        Server server = new Server(serverSocket);
    }
}
