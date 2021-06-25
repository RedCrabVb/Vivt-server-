package com.vivt;

import com.server.Server;
import com.server.ServerControl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        context.getBean(Server.class).run();
        context.getBean(ServerControl.class);

        ServerControl.LOGGER.log(Level.INFO, "Server start " + new Date().toString());
    }
}
