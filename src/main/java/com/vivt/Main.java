package com.vivt;

import com.server.Server;
import com.server.ServerControl;

import java.util.Date;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) throws Exception {
        Config config = Config.getInstance(args);
        Server server = Server.getInstance(config.getServerPort(), Config.databaseCreate(config));

        new ServerControl(config.getLogConfPath(), server);
        server.run();

        ServerControl.LOGGER.log(Level.INFO, "Server start " + new Date().toString());
    }
}
