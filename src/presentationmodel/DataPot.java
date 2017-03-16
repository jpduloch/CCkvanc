package presentationmodel;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jp on 11.03.2017.
 */
public class DataPot {

    private StringProperty applTitle;
    private ObservableList<users> onlineClients;
    private IntegerProperty discoveryPort;
    private IntegerProperty messagePort;
    private StringProperty discoveryString;
    private StringProperty username;
    private StringProperty history;

    public DataPot() {
        initializeValues();
        startDicovery();

    }

    private void initializeValues() {
        applTitle = new SimpleStringProperty("CCkvanc");
        discoveryPort = new SimpleIntegerProperty(7016);
        messagePort = new SimpleIntegerProperty(7015);
        discoveryString = new SimpleStringProperty("HelloFromTheOtherSide");
        username = new SimpleStringProperty("captain_youllneverforgive");
        history = new SimpleStringProperty();
        onlineClients = FXCollections.observableArrayList();
    }


    public String getApplTitle() {
        return applTitle.get();
    }

    public StringProperty applTitleProperty() {
        return applTitle;
    }

    public void setApplTitle(String applTitle) {
        this.applTitle.set(applTitle);
    }

    public int getDiscoveryPort() {
        return discoveryPort.get();
    }

    public IntegerProperty discoveryPortProperty() {
        return discoveryPort;
    }

    public void setDiscoveryPort(int discoveryPort) {
        this.discoveryPort.set(discoveryPort);
    }

    public int getMessagePort() {
        return messagePort.get();
    }

    public IntegerProperty messagePortProperty() {
        return messagePort;
    }

    public void setMessagePort(int messagePort) {
        this.messagePort.set(messagePort);
    }

    public String getDiscoveryString() {
        return discoveryString.get();
    }

    public String getDiscoveryStringNumberd() {
        int r = ThreadLocalRandom.current().nextInt(0, 101);
        return new StringBuilder().append(discoveryString).append(r).toString();
    }

    public String getHistory() {
        return history.get();
    }

    public StringProperty historyProperty() {
        return history;
    }

    public void setHistory(String history) {
        this.history.set(history);
    }

    public void appendToHistory(String appen) {
        String temp = new StringBuilder().append(getHistory() != null ? getHistory() : "").append(appen).append("\n").toString();
        this.history.set(temp);
    }

    public StringProperty discoveryStringProperty() {
        return discoveryString;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    private void startDicovery() {
        startlisteners();

        discoverer b = new discoverer(this);

        b.start();

    }

    private void startlisteners() {
        try {
            InetAddress addr = InetAddress.getByName("0.0.0.0");
            listener lstDiscovery = new listener(this, addr, true);
            listener lstMessage = new listener(this, addr, false);
            lstDiscovery.start();
            lstMessage.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendTextMessage(String ip, String message) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            airmessage(addr, this.getMessagePort(), message);
            Platform.runLater(() -> this.appendToHistory(message));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendDiscoveryAcknowleadge(InetAddress receiver) {
        airmessage(receiver, this.getDiscoveryPort(), "CCkvanc" + this.getUsername());
    }

    private static final int BUFFER_SIZE = 256;

    private void airmessage(InetAddress receiver, int port, String message) {

        try {
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.getBytes().length, receiver, port);
            DatagramSocket ds = new DatagramSocket();
            ds.setBroadcast(false);
            ds.send(dp);
            System.out.println("Aired message: " + message + " to " + receiver.toString() + ":" + this.getMessagePort());
            ds.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void addOnlineClient(DatagramPacket dp, String msg) {
        Date date = new Date(System.currentTimeMillis());
        users a = new users(msg.substring(15, 30), dp.getAddress().toString().replace("/", ""), "online", date.toString());

        users client = onlineClients.stream().filter(x -> x.getIp().equals(dp.getAddress().toString().replace("/", ""))).findFirst().orElse(null);
        if (client == null) {
            onlineClients.add(a);
        } else {
            client.setLastupdate(date.toString());
            client.setStatus("Online");
        }

    }

    public ObservableList<users> getOnlineClients() {
        return onlineClients;
    }
}
