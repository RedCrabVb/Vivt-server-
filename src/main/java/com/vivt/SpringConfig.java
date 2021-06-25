package com.vivt;


import com.api.*;
import com.dataBase.DataBase;
import com.dataBase.JsonDataBase;
import com.dataBase.MySqlDataBase;
import com.server.HandlerAPI;
import com.server.Server;
import com.server.ServerControl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.vivt")
@PropertySource("classpath:config.properties")
public class SpringConfig {
    private DataBase dataBase;
    private Server server;

    @Value("${serverPort}") int serverPort;
    @Value("${logConfPath}") String logConfig;

    @Value("${typeBase}") String typeBase;
    @Value("${pathJsonDataBase}") String pathJsonDataBase;
    @Value("${userParameterDB}") String userParameterDB;
    @Value("${passwordParameterDB}") String passwordParameterDB;
    @Value("${portParameterDB}") String portParameterDB;
    @Value("${serverNameDB}") String serverNameDB;
    @Value("${databaseNameParameterDB}") String databaseNameParameterDB;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataBase dataBase() throws Exception {
        if (dataBase == null) {
            if (typeBase.equals("mysql")) {
                return new MySqlDataBase(serverNameDB, portParameterDB,
                        databaseNameParameterDB,
                        userParameterDB, passwordParameterDB);
            } else if (typeBase.equals("json")) {
                return new JsonDataBase(pathJsonDataBase);
            } else {
                throw new Exception("Config error, type base = " + typeBase);
            }
        } else {
            return dataBase;
        }
    }

    @Bean
    public Server server() throws Exception {
        if (server == null) {
            return new Server(serverPort, dataBase());
        } else {
            return server;
        }
    }

    @Bean
    public ServerControl serverControl() throws Exception {
        return new ServerControl(logConfig, server());
    }

    @Bean
    public HandlerAPI apiGetMessage() throws Exception {
        return new HandlerAPI(new GetMessage(dataBase(), server()), server());
    }

    @Bean
    public HandlerAPI apiGetPersonData() throws Exception {
        return new HandlerAPI(new GetPersonData(dataBase(), server()), server());
    }

    @Bean
    public HandlerAPI apiGetNews() throws Exception {
        return new HandlerAPI(new GetNews(dataBase()), server());
    }

    @Bean
    public HandlerAPI apiGetSchedule() throws Exception {
        return new HandlerAPI(new GetSchedule(dataBase(), server()), server());
    }

    @Bean
    public HandlerAPI apiAuthorization() throws Exception {
        return new HandlerAPI(new Authorization(dataBase()), server());
    }

    @Bean
    public HandlerAPI apiRegistration() throws Exception {
        return new HandlerAPI(new Registration(dataBase()), server());
    }

    @Bean
    public HandlerAPI apiSendMessage() throws Exception {
        return new HandlerAPI(new SendMessage(dataBase()), server());
    }
}
