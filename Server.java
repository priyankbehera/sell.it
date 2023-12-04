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
    private static ServerSocket serverSocket;
    private static clientManager clientManager;
    private static Thread thread;
    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started on port " + portNumber);

            while (true) {
                clientManager = new clientManager(serverSocket.accept());
                thread = new Thread(clientManager);
                thread.start();
                // Creates a new thread for each client
            }
        }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }



    public static void requests(String request, PrintWriter printWriter) {
        String [] requestArray = request.split(" ");
        String functionRequest = requestArray[0];
        String [] functionArgs = new String[requestArray.length - 1];
        for (int i = 1; i < requestArray.length; i++) {
            functionArgs[i - 1] = requestArray[i];
        }
        switch (functionRequest) {
            case "login":
                int accountType = Integer.parseInt(functionArgs[0]);
                String email = functionArgs[1];
                String password = functionArgs[2];

                boolean success = login(accountType, email, password);
                printWriter.println(success);
                printWriter.flush();
                break;

            case "createAccount":
                accountType = Integer.parseInt(functionArgs[0]);
                email = functionArgs[1];
                password = functionArgs[2];

                success = createAccount(accountType, email, password);
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
            PrintWriter printWriter = null;
            BufferedReader br = null;

            try {
                printWriter = new PrintWriter(socket.getOutputStream(), false);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter.println("Connected to server thread.");
                printWriter.flush();
                String line;
                System.out.println(br.readLine());
                while ((line = br.readLine()) != null) {
                    System.out.println("Received request: " + line);
                    printWriter.println("Received request: " + line);
                    printWriter.flush();
                    requests(line, printWriter);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    if (br != null) {
                        br.close();
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}

