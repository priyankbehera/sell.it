import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Hostname: localhost
// Port: 4242

/**
 * PJ-05 -- Sell.it
 *
 * Server class that handles requests from the client.
 * This allows all data handling to be done server-side, increasing
 * security and limiting the amount of data sent to the client.
 *
 * NOTE: To handle requests, the server is sent a string.
 * The first word is the name of the request (eg, "login").
 * The other words are function arguments, delimited by commas.
 *
 **/
public class Server {


    public static void main(String[] args) {
        do {
            try {
                ServerSocket serverSocket = new ServerSocket(4242);
                System.out.println("Waiting for the client to connect...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                // Handles requests from client
                requests(socket);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private synchronized static void requests(Socket socket) {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            // Reads request from client
            String requestString = scanner.nextLine();
            System.out.println("Request received: " + requestString);
            // Split requests into parts
            String[] requestParts = requestString.split(",");
            String function = requestParts[0];
            String[] args = new String[requestParts.length - 1];

            // Performs different actions based on request
            if (function.equals("checkLogin")) {  // Logs in user
            // args[0] = accountType, args[1] = email, args[2] = password
                int accountType = Integer.parseInt(args[0]);
                boolean loginSuccess = login(accountType, args[1], args[2]);

                // send response to client
                if (loginSuccess) {
                    printWriter.println("true");
                } else {
                    printWriter.println("false");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /*
     "logs in" the user by verifying their credentials are correct
    returns true if user successfully logs in, false otherwise
    accountType: 0 = customer, 1 = seller
    */
    public static synchronized boolean login(int accountType, String email, String password) {
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

    private synchronized void handleRequest(Request request, Socket socket) {
        String method = request.getMethod();
        Object[] args = request.getArgs();

        // Checks name of methods
        try {
            if (method.equals("login")) {
                // args[0] = accountType, args[1] = email, args[2] = password
                boolean loginSuccess = login((int) args[0], (String) args[1], (String) args[2]);
                System.out.println("Login successful: " + loginSuccess);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(loginSuccess);

            } else if (method.equals("test")) {
                System.out.println("Test successful");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject("Test successful");
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


