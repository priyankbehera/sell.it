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

    /*
     "logs in" the user by verifying their credentials are correct
    returns true if user successfully logs in, false otherwise
    accountType: 0 = customer, 1 = seller
    */
    public static synchronized boolean login (int accountType, String email, String password) {
        // Sets filename based on account type
        String filename = "";
        if (accountType == 0) {
            filename = "customer_data/customerNames.txt";
        } else if (accountType == 1) {
            filename = "seller_data/sellerNames.txt";
        } else {
            return false;
        }
        // Read from user data file and check if username and password match
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(email) && data[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}