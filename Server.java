import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;


// Hostname: localhost
// Port: 4242

/**
 * PJ-05 -- Sell.it
 * Server class that handles requests from the client.
 * This allows all data handling to be done server-side, increasing
 * security and limiting the amount of data sent to the client.
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
        System.arraycopy(requestArray, 1, args, 0, requestArray.length - 1);

        switch (function) {
            case "login" -> {
                String email = args[0];
                String password = args[1];
                String[] successLogin = login(email, password).split(",");
                boolean successLoginString = Boolean.parseBoolean(successLogin[0]);
                if (!successLoginString) {
                    printWriter.println(false);
                    printWriter.flush();
                } else {
                    printWriter.println(true + "," + successLogin[1]);
                    printWriter.flush();
                }
            }
            case "createAccount" -> {
                int accountType = Integer.parseInt(args[0]);
                String email = args[1];
                String password = args[2];
                boolean success = createAccount(accountType, email, password);
                printWriter.println(success);
                printWriter.flush();
            }
            case "getConversationHistory" -> {
                boolean ifSeller = Boolean.parseBoolean(args[2]);
                String seller = args[0];
                String customer = args[1];
                ArrayList<String> messageList = getConversationHistory(seller, customer, ifSeller);
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

    }
    /*
     "logs in" the user by verifying their credentials are correct
    returns true if user successfully logs in, false otherwise
    accountType: 0 = customer, 1 = seller
    */
    // if login is invalid, returns false
    // if login is valid, the second term is the boolean ifSeller
    // e.g. if it is a valid customer login, returns "true,false"
    // if not a user, it returns false
    public static synchronized String login(String email, String password) {
        String toReturn = "";
        String preAppend = "";
        boolean isUser = isUser(email);
        if ( isUser ) {
            ArrayList<String> customers = new ArrayList<>();
            ArrayList<String> sellers = new ArrayList<>();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("customer_data/customerNames.txt"))) {
                String line;
                while ( (line = bufferedReader.readLine()) != null ) {
                    customers.add(line);
                }
                for ( int i = 0; i < customers.size(); i++) {
                    String customer = customers.get(i).split("-")[0];
                    String passwordToCheck = customers.get(i).split("-")[1];
                    if ( customer.equals(email) && passwordToCheck.equals(password)) {
                        return "true,false";
                    }
                }
            } catch (IOException e) {
                return "false";
            }
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("seller_data/sellerNames.txt"))) {
                String line;
                while ( (line = bufferedReader.readLine()) != null ) {
                    sellers.add(line);
                }
                for ( int i = 0; i < sellers.size(); i++) {
                    String seller = sellers.get(i).split("-")[0];
                    String passwordToCheck = sellers.get(i).split("-")[1];
                    if ( seller.equals(email) && passwordToCheck.equals(password)) {
                        return "true,true";
                    }
                }
            } catch (IOException e) {
                return "false";
            }
        }
        return "false";
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
    private static boolean isUser(String user) {
        ArrayList<String> customers = new ArrayList<>();
        ArrayList<String> sellers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("customer_data/customerNames.txt"))) {
            String line;
            while ( (line = bufferedReader.readLine()) != null ) {
                customers.add(line);
            }
            for ( int i = 0; i < customers.size(); i++) {
                String customer = customers.get(i).split("-")[0];
                if ( customer.equals(user) ) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("seller_data/sellerNames.txt"))) {
            String line;
            while ( (line = bufferedReader.readLine()) != null ) {
                sellers.add(line);
            }
            for ( int i = 0; i < sellers.size(); i++) {
                String seller = sellers.get(i).split("-")[0];
                if ( seller.equals(user) ) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
    // uses isUser to see if it is a user, then returns true if is a seller,
    // returns false if it is a customer
    private static boolean ifSeller(String user) {
        boolean isUser = isUser(user);
        if (isUser) {
            ArrayList<String> sellers = new ArrayList<>();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("seller_data/sellerNames.txt"))) {
                String line;
                while ( (line = bufferedReader.readLine()) != null ) {
                    sellers.add(line);
                }
                for ( int i = 0; i < sellers.size(); i++) {
                    String seller = sellers.get(i).split("-")[0];
                    if ( seller.equals(user) ) {
                        return true;
                    }
                }
            } catch (IOException e) {
                return false;
            }
            return false;
        }
        return false;
    }
}


