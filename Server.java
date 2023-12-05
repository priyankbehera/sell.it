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

    private static final int portNumber = 4242;
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started on port " + portNumber);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected at: " + socket.getInetAddress().getHostAddress());

                // Creates client object and thread
                clientManager client = new clientManager(socket);
                Thread thread = new Thread(client);
                thread.start();


            }

        }
            catch (IOException e) {
                System.out.println(e.getMessage());

            }
        }



    public static void requests(String request, PrintWriter printWriter) {
        String [] requestArray = request.split(",");
        String function = requestArray[0];
        System.out.println("Function: " + function);
        String account = requestArray[1];
        String email = requestArray[2];
        String password = requestArray[3];
        int accountType = Integer.parseInt(account);

        switch (function) {
            case "login":
                boolean success = login(accountType, email, password);
                printWriter.println(success);
                printWriter.flush();
                break;

            case "createAccount":
                success = createAccount(accountType, email, password);
                printWriter.println(success);
                printWriter.flush();
                break;
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
                String[] data = line.split("-");
                if (data[0].equals(email) && data[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    // manages each client thread

    public static synchronized boolean createAccount(int accountType, String email, String password) {
        // Sets filename based on account type
        String filename = "";
        if (accountType == 0) {
            filename = "customer_data/customerNames.txt";
        } else if (accountType == 1) {
            filename = "seller_data/sellerNames.txt";
        } else {
            return false;
        }

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
    private static class clientManager implements Runnable {
        private Socket socket;

        public clientManager(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), false);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read request from  client
            String request;
            while (true) {
                request = br.readLine();
                if (request != null) {
                    if (!request.isEmpty()) break;
                }
            }
            System.out.println("Received request: " + request);
            requests(request, printWriter);
        } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    }


