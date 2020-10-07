package servermaintenance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

public class CustomRcon extends Rcon {

    private CustomRcon(String host, int port, byte[] password) throws IOException, AuthenticationException {
        super(host, port, password);
    }

    public static CustomRcon getConnection() {
        CustomRcon crcon = null;

        try {
            File config = new File("config.properties");
            Properties prop = new Properties();

            if (!config.exists()) {
                try (OutputStream output = new FileOutputStream(config)) {
                    prop.setProperty("rcon.address", "localhost");
                    prop.setProperty("rcon.port", "25575");
                    prop.setProperty("rcon.password", "password");
                    prop.store(output, "ServerMaintenance configuration file");
                }
            }
            try (InputStream input = new FileInputStream(config)) {
                prop.load(input);
                String rconAddress = prop.getProperty("rcon.address");
                int rconPort = Integer.valueOf(prop.getProperty("rcon.port"));
                byte[] rconPassword = prop.getProperty("rcon.password").getBytes();
                crcon = new CustomRcon(rconAddress, rconPort, rconPassword);
            }

        } catch (IOException | NumberFormatException | AuthenticationException ex) {
            ServerMaintenance.errorMessage(ex, "Something went wrong while connecting to the server!");
        }

        return crcon;
    }

}
