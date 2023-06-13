package io.github.aaronr92.rockpaperscissorsserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.aaronr92.rockpaperscissorsserver.packet.Packet;

public class PacketListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        System.out.println("Packet received");
        if (object instanceof Packet packet) {
            System.out.println("Received " + packet);

            Packet responsePacket = new Packet("Pong!");
            connection.sendTCP(responsePacket);
        }
    }

    @Override
    public void connected(Connection connection) {
        System.out.println("New connection at " + connection.getRemoteAddressTCP());
    }

}
