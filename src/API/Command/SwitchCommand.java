package API.Command;

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
        register("registration", new Registration());
    }

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public void execute(ClientThread client, JsonObject json) {
        String commandJs = json.get("header").getAsString();
        Command commandObj = commandMap.get(commandJs);

        if (commandObj == null) {
            throw new IllegalStateException("Not found command(old api?): " + json.get("header").getAsString());
        }

        try {
            JsonObject jsonResponse = commandObj.execute(client, json);
            client.sendMessage(jsonResponse);
        } catch (Exception e) {
            ServerControl.LOGGER.log(Level.INFO, "Error: " + e.toString());
        }
    }
}
