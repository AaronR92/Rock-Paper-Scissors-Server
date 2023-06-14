package io.github.aaronr92.rockpaperscissorsserver.component;

import com.esotericsoftware.kryonet.Connection;
import io.github.aaronr92.rockpaperscissorsserver.event.ConnectionEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishConnectionEvent(
            final String login,
            final String password,
            final Connection connection
            ) {
        var event = new ConnectionEvent(login, password, connection);
        eventPublisher.publishEvent(event);
    }

}
