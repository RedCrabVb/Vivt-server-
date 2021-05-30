package DataBase;

import Server.Server;

import java.util.ArrayList;

public class ClientInfo {
    private String version = "None";
    private int ID;
    private boolean isAuthorized = false;
    private boolean isRealAccount = false;
    private String reasonDownService = "test";

    public void setVersion(String version) {
        this.version = version;
    }

    public void setIsRealAccount(String login, String password) {
        this.ID = Server.dataBase.realAccount(login, password);
        this.isAuthorized = ID != -1;
    }

    public void authorized() {
        isAuthorized = true;
    }

    public boolean isAuthorized() { return isAuthorized; }

    public String getVersion() {
        return version;
    }

    public int getID() { return ID; }

    public boolean isRealAccount() {
        return isRealAccount;
    }

    public String getReasonDownService() {
        return reasonDownService;
    }

    public void setReasonDownService(String reasonDownService) {
        this.reasonDownService = reasonDownService;
    }
}
