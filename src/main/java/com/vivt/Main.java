package com.vivt;

import Server.Server;
import Server.ServerControl;
import java.net.ServerSocket;
import java.util.Date;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) throws Exception {
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);

	    Config config = Config.getInstance(args);
        ServerControl serverControl = new ServerControl(config.getLogConfPath());
        Server server = new Server(config.getServerPort());

        ServerControl.LOGGER.log(Level.INFO, "Server start " + new Date().toString());
    }
}
