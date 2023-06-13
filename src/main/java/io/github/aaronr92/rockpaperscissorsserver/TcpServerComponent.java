package io.github.aaronr92.rockpaperscissorsserver;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import io.github.aaronr92.rockpaperscissorsserver.packet.Packet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TcpServerComponent {

    @Value("${application.tcp}")
    private int tcpPort;

    @Bean
    public Server getServer() {
        Server server = new com.esotericsoftware.kryonet.Server();
        Kryo kryo = server.getKryo();

        kryo.register(Packet.class);

        server.addListener(new PacketListener());

        try {
            server.bind(tcpPort);
            server.start();
            System.out.println("TCP server started!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return server;
    }

}
