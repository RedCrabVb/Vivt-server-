package com.vivt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {
    private String userParameterDB = "";
    private String passwordParameterDB = "";
    private String portParameterDB = "";
    private String serverNameDB = "";
    private String databaseNameParameterDB = "";
    private String versionProgram = "0.4";
    private int serverPort = 0;

    private String logConfPath = "./log.config";
    private String configPath = "./Config.json";

    public int getServerPort() {
        return serverPort;
    }

    public String getVersionProgram() {
        return versionProgram;
    }

    public String getLogConfPath() {
        return logConfPath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public String getUserParameterDB() {
        return userParameterDB;
    }

    public String getPasswordParameterDB() {
        return passwordParameterDB;
    }

    public String getPortParameterDB() {
        return portParameterDB;
    }

    public String getServerNameDB() {
        return serverNameDB;
    }


    public void save() {
        Gson gson = new GsonBuilder().create();

        try (Writer writer = new FileWriter(configPath)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDatabaseNameParameterDB() {
        return databaseNameParameterDB;
    }

    private static Config config;

    private Config() { }

    public static Config getInstance() throws Exception {
        if (config == null) {
            config = getInstance(new String[]{});
        }

        return config;
    }

    public static Config getInstance(String[] args) throws Exception {
        if (config == null) {
            String configPath = null, logConfPath = null;
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-configPath":
                        configPath = args[i+1];
                        break;
                    case "-logPath":
                        logConfPath = args[i+1];
                        break;
                }
            }
            if (configPath == null || logConfPath == null) {
                 logConfPath = "log.config";
                 configPath = "Config.json";
            }

            String jsonConfig = fileToString(configPath);
            config = Config.createConfig(jsonConfig, configPath, logConfPath);
            //config.save();
        }

        return config;
    }

    private static Config createConfig(String jsonConfig, String configPath, String logConfPath) {
        Config config = new Gson().fromJson(jsonConfig, Config.class);
        config.configPath = configPath;
        config.logConfPath = logConfPath;

        return config;
    }

    private static String fileToString(String path) throws Exception {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
