package io.github.aaronr92.rockpaperscissorsserver.event;

import com.esotericsoftware.kryonet.Connection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ConnectionEvent {

    private String login;
    private String password;
    private boolean needsRegistration;
    private Connection connection;

}
