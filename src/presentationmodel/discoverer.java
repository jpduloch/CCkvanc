package presentationmodel;

import java.net.*;
import java.util.Enumeration;

/**
 * Created by jp on 06.03.2017.
 */
public class discoverer extends Thread {

    private static final int BUFFER_SIZE = 256;
    private String message;
    private DataPot model;

    public discoverer(DataPot model) {
        this.model = model;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    if (networkInterface.isLoopback())
                        continue;    // Don't want to broadcast to the loopback interface
                    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                        InetAddress broadcast = interfaceAddress.getBroadcast();
                        if (broadcast == null) {
                            continue;
                        } else {
                            discoverBC(broadcast);
                        }
                    }
                }
                sleep(1000);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void discoverBC(InetAddress bcast) {
        try {
            DatagramPacket dp = new DatagramPacket(model.getDiscoveryString().getBytes(), model.getDiscoveryString().getBytes().length, bcast, model.getDiscoveryPort());
            DatagramSocket ds = new DatagramSocket();
            ds.setBroadcast(true);
            ds.send(dp);
            //System.out.println("Sent Discovery Broadcast: " + model.getDiscoveryString() + " to " + bcast.toString());
            ds.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
