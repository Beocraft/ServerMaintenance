package servermaintenance;

import java.io.IOException;
import java.util.Scanner;

public class ServerMaintenance {

    public static void main(String[] args) {
        System.out.println("Connecting to the server...");
        CustomRcon rcon = CustomRcon.getConnection();

        if (rcon != null) {
            System.out.println("Connected!");
            Scanner scn = new Scanner(System.in);

            System.out.println("Do you want to stop the server?");
            System.out.print("Answer: Y/N >> ");
            String choice = scn.nextLine().trim();

            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                System.out.println("Enter a custom message or press ENTER for default!");
                System.out.print("Message: >> ");

                String message = scn.nextLine().trim();
                if (message != null) {
                    if (message.isEmpty()) {
                        System.out.println("You selected the default message!");
                        message = "Server maintenance";
                    }
                    System.out.println("Message set to: " + message);
                    try {
                        rcon.command("kick @a " + message);
                        rcon.command("stop");
                    } catch (IOException ex) {
                        errorMessage(ex, "Couldn't send commands to the server, is the server online?");
                    }
                }
            } else {
                System.out.println("Exiting!");
            }
            try {
                rcon.disconnect();
            } catch (IOException ex) {
                errorMessage(ex, "Couldn't close the connection to the server!");
            }
        }
    }

    public static void errorMessage(Exception ex, String message) {
        System.err.println(message);
        System.err.println("Error message: " + ex.getMessage());
    }

}
