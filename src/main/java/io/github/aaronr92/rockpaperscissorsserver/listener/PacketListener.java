package io.github.aaronr92.rockpaperscissorsserver.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.aaronr92.rockpaperscissorsserver.component.EventPublisher;
import io.github.aaronr92.rockpaperscissorsserver.packet.client.ClientboundConnectionPacket;
import io.github.aaronr92.rockpaperscissorsserver.packet.Packet;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class PacketListener extends Listener {

    private final Logger LOG = LoggerFactory.getLogger(PacketListener.class);

    private final EventPublisher eventPublisher;

    @Override
    public void received(Connection connection, Object receivedPacket) {
        LOG.info("Packet received");
        if (receivedPacket instanceof ClientboundConnectionPacket packet) {
            eventPublisher.publishConnectionEvent(
                    packet.login(),
                    packet.password(),
                    connection
            );
        } else if (receivedPacket instanceof Packet packet) {
            LOG.info(String.valueOf(packet));
        }
    }

    @Override
    public void connected(Connection connection) {
        LOG.info("New connection at {}", connection.getRemoteAddressTCP());
    }

}
