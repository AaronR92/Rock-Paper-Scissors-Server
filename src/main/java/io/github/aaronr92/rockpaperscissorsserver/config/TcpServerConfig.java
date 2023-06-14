package io.github.aaronr92.rockpaperscissorsserver.config;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import io.github.aaronr92.rockpaperscissorsserver.listener.PacketListener;
import io.github.aaronr92.rockpaperscissorsserver.packet.Packet;
import io.github.aaronr92.rockpaperscissorsserver.packet.client.ClientboundConnectionPacket;
import io.github.aaronr92.rockpaperscissorsserver.packet.server.ServerboundConnectionPacket;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class TcpServerConfig {

    @Value("${application.tcp}")
    private int tcpPort;

    private final PacketListener packetListener;

    public TcpServerConfig(PacketListener packetListener) {
        this.packetListener = packetListener;
    }

    @Bean
    public Server getServer() {
        Server server = new com.esotericsoftware.kryonet.Server();
        Kryo kryo = server.getKryo();

        kryo.register(ClientboundConnectionPacket.class);
        kryo.register(ServerboundConnectionPacket.class);

        kryo.register(Packet.class);

        server.addListener(packetListener);

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
