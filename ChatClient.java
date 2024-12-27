import java.io.*;
import java.net.*;
public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 10112;
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Connected to chat server!");
            // Thread for receiving messages
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println(message); // Display received messages
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Sending messages
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
                String userInput;
                System.out.println("Enter your messages:");
                while ((userInput = consoleReader.readLine()) != null) {
                    writer.println(userInput); // Send message to server
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
