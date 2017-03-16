package presentationmodel;

import javafx.application.Platform;

import java.io.IOException;
import java.net.*;

/**
 * Created by jp on 06.03.2017.
 */
public class listener extends Thread {

    private static final int BUFFER_SIZE = 256;
    private InetAddress address;
    private DataPot model;
    private boolean isDiscovery;

    public listener(DataPot model, InetAddress address, boolean isDiscovery) {
        this.model = model;
        this.address = address;
        this.isDiscovery = isDiscovery;
    }

    public void run(){
        System.out.println("Listen on "+address.getHostAddress()+":"+(isDiscovery ? model.getDiscoveryPort() : model.getMessagePort()));
        listn();
    }

    private void listn() {
        try {

            DatagramSocket ds = new DatagramSocket(isDiscovery ? model.getDiscoveryPort() : model.getMessagePort(), address);
            ds.setBroadcast(true);

            while (true) {
                byte[] buf = new byte[BUFFER_SIZE];
                DatagramPacket dp = new DatagramPacket(buf, BUFFER_SIZE);
                ds.receive(dp);
                String msg = new String(buf, 0, dp.getLength());
                System.out.println(msg);
                if(msg.equals(model.getDiscoveryString())){
                    model.sendDiscoveryAcknowleadge(dp.getAddress());
                }else if(msg.length() == 32 && msg.substring(0,7).toLowerCase().equals("cckvanc")){
                    Platform.runLater(() -> model.addOnlineClient(dp, msg));
                }else{
                    Platform.runLater(() -> model.appendToHistory(msg));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
