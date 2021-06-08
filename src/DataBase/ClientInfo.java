package DataBase;

import Server.Server;

import java.util.ArrayList;
import java.util.Random;

public class ClientInfo {
    private String token;
    private int ID;
    private boolean isAuthorized = false;
    private boolean isRealAccount = false;
    private String reasonDownService = "test";

    public void setIsRealAccount(String login, String password) {
        this.ID = Server.dataBase.realAccount(login, password);
        this.isAuthorized = ID != -1;
        //this.token = "asdfasdf342" + new Random().nextLong();
    }

    public boolean isAuthorized() { return isAuthorized; }

    public int getID() { return ID; }

    public String getReasonDownService() {
        return reasonDownService;
    }

    public void setReasonDownService(String reasonDownService) {
        this.reasonDownService = reasonDownService;
    }

    public String getToken() { return  token; }

    public void setToken(String token) {this.token = token;}
}
