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

        String jsonEnc5 =  URLEncoder.encode("{\"header\":\"registration\",\"login\":\"mail\",\"password\":\"pass\"}", StandardCharsets.UTF_8);
        sendInquiry(jsonEnc5);

        String jsonEnc =  URLEncoder.encode("{\"header\":\"person_data\"}", StandardCharsets.UTF_8);
        sendInquiry(jsonEnc);

        String jsonEnc2 =  URLEncoder.encode("{\"header\":\"schedule\"}", StandardCharsets.UTF_8);
        sendInquiry(jsonEnc2);

        String jsonEnc3 =  URLEncoder.encode("{\"header\":\"message\"}", StandardCharsets.UTF_8);
        sendInquiry(jsonEnc3);

        String jsonEnc4 =  URLEncoder.encode("{\"header\":\"news\"}", StandardCharsets.UTF_8);
        sendInquiry(jsonEnc4);
    }

    private void sendInquiry(String json) throws Exception {
        URL url = new URL("http://localhost:8080/api?token=test&json=" + json);
        HttpURLConnection connection = getResponseServer(url);
        String response = connectionResponseToString(connection);
        System.out.println(connection.getResponseCode());
        System.out.println(response);
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