package API;

import com.google.gson.JsonObject;

import java.util.Map;

public interface Command {
    JsonObject execute(Map<String, String> params);
}
