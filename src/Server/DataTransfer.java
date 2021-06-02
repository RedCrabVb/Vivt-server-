package Server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;

public class DataTransfer {
    private BufferedReader br;
    private PrintWriter toClient;
    private final Socket socket;

    protected DataTransfer(Socket socketClient) {
        try {
            this.br = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            this.toClient = new PrintWriter(socketClient.getOutputStream(), true);
        } catch (Exception e) {
            ServerControl.LOGGER.log(Level.WARNING, "Error receiving flow " + e);
        }
        this.socket = socketClient;
    }

    public void close() {
        try {
            toClient.flush();
            br.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendJson(String msgJson) {
        ServerControl.LOGGER.log(Level.INFO, "send json: " + msgJson);
        toClient.println(msgJson);
    }

    public JsonObject getJson() {
        try {
            JsonObject jsonPack = JsonParser.parseString(br.readLine()).getAsJsonObject();

            ServerControl.LOGGER.log(Level.INFO, "get json: " + jsonPack.toString());
            return jsonPack;
        } catch (Exception e) { ;
            JsonObject jNull = new JsonObject();
            jNull.addProperty("header", "null");
            return jNull;
        }
    }

    protected String acceptFile(String path, String failName, int sizeFile) throws Exception {
        InputStream is = socket.getInputStream();

        String pathDwFile = new File(path, failName).getAbsolutePath();
        FileOutputStream fos = new FileOutputStream(pathDwFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int count;
        byte[] buffer = new byte[1024];

        int read = 0;
        while ((count = is.read(buffer)) > 0) {
            bos.write(buffer, 0, count);
            if ((read += count) >= sizeFile) {
                break;
            }
        }

        bos.flush();
        bos.close();
        fos.close();

        return pathDwFile;
    }

    protected String sendFile(String path) throws Exception {
        File transferFile = new File(path);

        byte[] byteArray = new byte[(int) transferFile.length()];
        int bytesRead;


        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(transferFile));
        OutputStream os = socket.getOutputStream();

        while (-1 != (bytesRead = bin.read(byteArray, 0, byteArray.length))) {
            os.write(byteArray, 0, bytesRead);
        }

        bin.close();
        os.flush();

        return path;
    }


    public boolean isClosed() {
        return socket.isClosed();
    }

}
