package Server;

import com.vivt.Config;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientThreadTest {
    @Test
    void run() throws Exception {
        MockSocket socket = new MockSocket();
        Config config = Config.getInstance();
        ServerControl serverControl = new ServerControl(config.getLogConfPath());
        new Server(new ServerSocket());

        new ClientThread(socket);
        Thread.sleep(1000);
    }

    @Test
    public void serverAcceptsRequest() throws IOException {
        MockSocket mockSocket = new MockSocket();
        BufferedReader requestFrom = new BufferedReader(new InputStreamReader(mockSocket.getInputStream()));
      //  assertEquals("{\"header\":\"registration\",\"login\":\"test\",\"password\":\"Pass\"}", requestFrom.readLine());
    }

}

class MockSocket extends Socket {

    private final List<Byte> bytesList = new ArrayList<>();

    public MockSocket() {}

    public InputStream getInputStream() {
        String str = "{\"header\":\"registration\",\"login\":\"mail\",\"password\":\"pass\"}\n"
                + "{\"header\":\"person data\"}\n"
                + "{\"header\":\"schedule\"}\n"
                + "{\"header\":\"news\"}\n";
        return new ByteArrayInputStream(str.getBytes());
    }

    public OutputStream getOutputStream() {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                bytesList.add((byte) b);
            }
        };
    }

    public String output() {
        byte[] converted = toByteArray(bytesList);
        return new String(converted, StandardCharsets.UTF_8);
    }

    private byte[] toByteArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        int index = 0;
        for (byte b : byteList) {
            byteArray[index++] = b;
        }
        return byteArray;
    }

}