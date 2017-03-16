package presentationmodel;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by jp on 3/13/17.
 */


//nameCol, ipCol, statusCol, lastupdateCol
public class users {
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty ip = new SimpleStringProperty();
    private final SimpleStringProperty status = new SimpleStringProperty();
    private final SimpleStringProperty lastupdate = new SimpleStringProperty();

    public users(String name, String ip, String status, String lastupdate) {
        this.name.set(name);
        this.ip.set(ip);
        this.status.set(status);
        this.lastupdate.set(lastupdate);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getIp() {
        return ip.get();
    }

    public SimpleStringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getLastupdate() {
        return lastupdate.get();
    }

    public SimpleStringProperty lastupdateProperty() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate.set(lastupdate);
    }
}
