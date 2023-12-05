import javax.swing.*;
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
        // create list of arguments
        String[] args = new String[requestArray.length - 1];
        for (int i = 1; i < requestArray.length; i++) {
            args[i - 1] = requestArray[i];
        }

        switch (function) {
            case "login":
                int accountType = Integer.parseInt(args[0]);
                String email = args[1];
                String password = args[2];

                boolean success = login(accountType, email, password);
                printWriter.println(success);
                printWriter.flush();
                break;

            case "createAccount":
                accountType = Integer.parseInt(args[0]);
                email = args[1];
                password = args[2];
                success = createAccount(accountType, email, password);
                printWriter.println(success);
                printWriter.flush();
                break;

            case "getConversationHistory":
                boolean ifSelller = Boolean.parseBoolean(args[2]);
                String seller = args[0];
                String customer = args[1];
                ArrayList<String> messageList = getConversationHistory(seller, customer, ifSelller);
                for (String message : messageList) {
                    printWriter.println(message);
                    printWriter.flush();
                }
                //prints end string to represent end of conversation
                String endMarker = "!@#%#$!@#%^@#$";
                printWriter.println(endMarker);
                printWriter.flush();
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

        // verifies account does not already exist
        boolean accountExists = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("-");
                if (data[0].equals(email)) {
                    accountExists = true;
                    return false;
                }
            }
            // account must not exist -- was not found in files; add to file
            PrintWriter pw = new PrintWriter(new FileWriter(filename, true));
            pw.println(email + "-" + password);
            pw.close();
            return true;

        } catch (IOException e) {
            System.out.println("Error creating account");
            return false;
        }
    }

    // sends arraylist of messages to client
    public static synchronized ArrayList<String> getConversationHistory(String seller, String customer, boolean ifSeller) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> messageList = new ArrayList<>(); // list of messages to be sent to client
        String folderName = "conversation_data";
        String filename = folderName + "/" + seller + "_" + customer + "_Messages.csv";

        try (BufferedReader bfr = new BufferedReader(new FileReader( filename ))) {
            String line;
            while ( ( line = bfr.readLine()) != null) {
                list.add(line);
            }
            String[] messages = new String[ list.size() ];
            String[] senderList = new String[ list.size() ];
            String[] dateStamp = new String[ list.size() ];
            String[] timeStamp = new String[ list.size() ];
            for ( int i = 0; i < messages.length; i++ ) {
                String[] messageArray = list.get(i).split(",");
                messages[i] = messageArray[2];
                senderList[i] = messageArray[0];
                dateStamp[i] = messageArray[3];
                timeStamp[i] = messageArray[4];
            }

            for (int i = 0; i < senderList.length; i++) {
                String str;
                if (ifSeller) {
                    if (senderList[i].equals(seller)) {
                        str = "You: " + messages[i];

                    } else {
                        str = customer + ": " + messages[i];
                    }
                } else {
                    if (senderList[i].equals(seller)) {
                        str = seller + ": " + messages[i];
                    } else {
                        str = "You: " + messages[i];
                    }
                }

                // Adds time stamp above the message
                String timeStampStr = timeStamp[i] + " " + dateStamp[i] + "\n";
                str = timeStampStr + str + "\n\n";

                // write to client
                messageList.add(str);

            }
        } catch (FileNotFoundException e ) {
            messageList.add("You have no message history with this user");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error in Program, please refresh",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return messageList;
    }
    private static class clientManager implements Runnable {
        private Socket socket;

        public clientManager(Socket socket) {
            this.socket = socket;
        }



        @Override
        public void run() {
            while (true) {
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
    }


