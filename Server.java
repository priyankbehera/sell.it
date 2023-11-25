import java.net.*;
import java.io.*;

// Hostname: localhost
// Port: 4242

public class Server {



    public static void main(String[] args) {
        do {
            try {
                ServerSocket serverSocket = new ServerSocket(4242);
                System.out.println("Waiting for the client to connect...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Client: " + line);
                    out.println("Server: " + line);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}