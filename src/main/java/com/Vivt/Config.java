package com.Vivt;

import com.DataBase.DataBase;
import com.DataBase.JsonDataBase;
import com.DataBase.MySqlDataBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Contains the data required to connect to the database,
 * the version of the program,
 * the paths to the files necessary
 * for the program, the port number
 * */
public class Config {
    private String typeBase = "";
    private String pathJsonDataBase = "";
    private String userParameterDB = "";
    private String passwordParameterDB = "";
    private String portParameterDB = "";
    private String serverNameDB = "";
    private String databaseNameParameterDB = "";
    private int serverPort = 0;

    private String logConfPath = "";
    private String configPath = "";

    private static Config config;

    private Config() {
    }

    private static Config createConfig(String jsonConfig, String configPath) {
        Config config = new Gson().fromJson(jsonConfig, Config.class);
        config.configPath = configPath;

        return config;
    }

    private static String fileToString(String path) throws Exception {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getLogConfPath() {
        return logConfPath;
    }

    public static DataBase databaseCreate(Config config) throws Exception {
        String typeBase = config.typeBase;
        if (typeBase.equals("mysql")) {
            return new MySqlDataBase(config.serverNameDB, config.portParameterDB,
                    config.databaseNameParameterDB,
                    config.userParameterDB, config.passwordParameterDB);
        } else if (typeBase.equals("json")) {
            return new JsonDataBase(config.pathJsonDataBase);
        } else {
            throw new Exception("Config error");
        }
    }

    public void save() {
        Gson gson = new GsonBuilder().create();

        try (Writer writer = new FileWriter(configPath)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() throws Exception {
        if (config == null) {
            config = getInstance(new String[]{});
        }

        return config;
    }

    public static Config getInstance(String[] args) throws Exception {
        if (config == null) {
            String configPath = null;
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-configPath":
                        configPath = args[i + 1];
                        break;
                }
            }
            if (configPath == null) {
                configPath = "src/main/resources/Config.json";
            }

            String jsonConfig = fileToString(configPath);
            config = Config.createConfig(jsonConfig, configPath);
        }

        return config;
    }
}
