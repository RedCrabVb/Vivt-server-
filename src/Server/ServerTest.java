package Server;

import com.google.gson.JsonParser;
import com.vivt.Main;
import org.junit.Before;
import org.junit.jupiter.api.*;

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
    private static String token;

    /*
    * {"header":"registration","login":"mail","password":"pass"}
    * {"header":"person data"}
    * {"header":"schedule"}
    * {"header":"message"}
    * {"header":"news"}
    * */
    @BeforeEach
    void serverStart() throws Exception {

        if(!Server.isActive()) {
            Main.main(new String[]{});
        }
    }

    @AfterEach
    void endTest() throws Exception {
        Thread.sleep(500);//fix bug java, https://bugs.openjdk.java.net/browse/JDK-8214300
    }

    @Test
    void serverAuthorizationTest() throws Exception {
        String api = "api/authorization";
        String paramEnc =  URLEncoder.encode(String.format("login=%s&password=%s", "mail", "pass"), StandardCharsets.UTF_8);
        String jsonToken = sendInquiry(api, paramEnc);
        this.token = JsonParser.parseString(jsonToken).getAsJsonObject().get("token").getAsString();

        System.out.println("Json get: " + jsonToken);
    }

    @Test
    void serverPersonDataGet() throws Exception {
        String api = "api/news";
        String result = sendInquiry(api, "");
        System.out.println(result);
    }

    @Test
    void serverSchedule() throws Exception {
        String api = "api/schedule";
        String paramEnc =  URLEncoder.encode(String.format("token=%s", token), StandardCharsets.UTF_8);
        String result = sendInquiry(api, paramEnc);

        System.out.println("Json get: " + result);
    }

    @Test
    void serverMessage() throws Exception {
        String api = "api/message";
        String paramEnc =  URLEncoder.encode(String.format("token=%s", token), StandardCharsets.UTF_8);
        String result = sendInquiry(api, paramEnc);

        System.out.println("Json get: " + result);
    }

    private String sendInquiry(String api, String json) throws Exception {
        URL url = new URL(String.format("http://localhost:8080/%s?token=test&%s", api, json));
        HttpURLConnection connection = getResponseServer(url);
        String response = connectionResponseToString(connection);

        System.out.println("URL: " + url.toString());
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