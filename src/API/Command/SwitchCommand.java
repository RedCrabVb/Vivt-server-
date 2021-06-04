package API.Command;

import DataBase.CreationJson;
import Server.ClientThread;
import Server.ServerControl;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.logging.Level;

/**
 * Command pattern
 */
public class SwitchCommand {
    private HashMap<String, Command> commandMap = new HashMap<>();

    public SwitchCommand() {

    }

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public void execute(ClientThread client, JsonObject json) {
        String commandJs = json.get("header").getAsString();
        Command commandObj = commandMap.get(commandJs);

        if (commandObj == null) {
            throw new IllegalStateException("Not found command(old api?): " + commandJs);
        }

        try {
            JsonObject jsonResponse = commandObj.execute(client, json);
            client.sendMessage(CreationJson.templateData(commandJs, jsonResponse));
        } catch (Exception e) {
            ServerControl.LOGGER.log(Level.INFO, "Error: " + e.toString());
        }
    }
}
