package Server;

import com.vivt.Main;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    /*
    * {"header":"registration","login":"mail","password":"pass"}
    * {"header":"person data"}
    * {"header":"schedule"}
    * {"header":"message"}
    * {"header":"news"}
    * */
    @Test
    void server() throws Exception {
        Main.main(new String[]{});

        String api = "api/registration";
        String jsonEnc =  URLEncoder.encode("login=mail&password=pass", StandardCharsets.UTF_8);
        String res = sendInquiry(api, jsonEnc);
        System.out.println(res);

        Thread.sleep(500);//fix bug java, https://bugs.openjdk.java.net/browse/JDK-8214300
    }

    private String sendInquiry(String api, String json) throws Exception {
        URL url = new URL(String.format("http://localhost:8080/%s?token=test&%s", api, json));
        HttpURLConnection connection = getResponseServer(url);
        String response = connectionResponseToString(connection);
        System.out.println(url.toString());
        return response;
    }

    private HttpURLConnection getResponseServer(URL url) throws Exception {
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = (HttpURLConnection) urlConnection;

        return connection;
    }

    private String connectionResponseToString(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current;

        while((current = in.readLine()) != null) {
            urlString += current;
        }

        return urlString;
    }
}