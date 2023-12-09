import Objects.Customer;
import Objects.Message;
import Objects.Seller;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * PJ-05 -- Sell.it
 * Server class that handles requests from the client.
 * This allows all data handling to be done server-side, increasing
 * security and limiting the amount of data sent to the client.
 * NOTE: To handle requests, the server is sent a string.
 * The first word is the name of the request (eg, "login").
 * The other words are function arguments, delimited by commas.
 * The hostname is localhost
 * The serverSocket is 4242
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void requests(String request, PrintWriter printWriter) {
        String[] requestArray = request.split(",");
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
            case "sendMessage" -> {
                String seller = args[0];
                String customer = args[1];
                boolean ifSeller = Boolean.parseBoolean(args[2]);
                String message;
                try {
                    message = args[3];
                } catch (Exception e) {
                    System.out.println("Please enter a message");
                    printWriter.println(false);
                    printWriter.flush();

                    return;
                }

                boolean success = sendMessage(seller, customer, ifSeller, message);
                printWriter.println(success);
                printWriter.flush();
            }
            case "exportFile" -> {
                String selectedFile = args[0];
                String seller = args[1];
                String customer = args[2];
                boolean success = sendFile(selectedFile, seller, customer);
                printWriter.println(success);
                printWriter.flush();
            }
            case "importFile" -> { // appends .txt file to chosen conversation
                String filename = args[0];
                String seller = args[1];
                String customer = args[2];
                String conversationFile = seller + "_" + customer + "_Messages.csv";
                boolean success = importFile(filename, conversationFile);
            }
            case "deleteMessage" -> {
                String seller = args[0];
                String customer = args[1];
                String message = args[2];
                boolean success = deleteMessage(seller, customer, message);
                printWriter.println(success);
                printWriter.flush();
            }
            case "editMessage" -> {
                String seller = args[0];
                String customer = args[1];
                String messageToEdit = args[2];
                String editedMessage = args[3];
                boolean success = editMessage(seller, customer, messageToEdit, editedMessage);
                printWriter.println(success);
                printWriter.flush();
            }
        }
    }

    public static synchronized boolean sendFile(String selectedFile, String seller, String customer) {
        ArrayList<String> list = new ArrayList<>();
        String contents = "";
        String filename = "conversation_data/" + seller + "_" + customer + "_Messages.csv";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        for ( int i = 0; i < list.size(); i++ ) {
            String[] messageParts = list.get(i).split(",");
            String message = messageParts[2];
            String time = messageParts[4];
            String date = messageParts[3];
            String sender = messageParts[0];
            String toAdd = time + "," + date + "\n" + sender + ": " + message + "\n";
            contents += toAdd;
        }
        try (OutputStream outputStream = new FileOutputStream(selectedFile)) {
            outputStream.write(contents.getBytes());
            JOptionPane.showMessageDialog(null, "File exported successfully",
                    "Error", JOptionPane.INFORMATION_MESSAGE );
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File could not be exported",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static synchronized boolean importFile(String filename, String conversationFile) {
        // reads in file
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            ArrayList<String> importFile = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                importFile.add(line);
            }
            // writes to conversation file
            PrintWriter pw = new PrintWriter(new FileWriter(conversationFile, true));
            for (int i = 0; i < importFile.size(); i++) {
                pw.println(importFile.get(i));
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // if login is invalid, returns false
    // if login is valid, the second term is the boolean ifSeller
    // e.g. if it is a valid customer login, returns "true,false"
    // if not a user, it returns false
    public static synchronized String login(String email, String password) {
        boolean isUser = isUser(email);
        if (isUser) {
            ArrayList<String> customers = new ArrayList<>();
            ArrayList<String> sellers = new ArrayList<>();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("customer_data/customerNames.txt"))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    customers.add(line);
                }
                for (String s : customers) {
                    String customer = s.split("-")[0];
                    String passwordToCheck = s.split("-")[1];
                    if (customer.equals(email) && passwordToCheck.equals(password)) {
                        return "true,false";
                    }
                }
            } catch (IOException e) {
                return "false";
            }
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("seller_data/sellerNames.txt"))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sellers.add(line);
                }
                for (String s : sellers) {
                    String seller = s.split("-")[0];
                    String passwordToCheck = s.split("-")[1];
                    if (seller.equals(email) && passwordToCheck.equals(password)) {
                        return "true,true";
                    }
                }
            } catch (IOException e) {
                return "false";
            }
        }
        return "false";
    }

    public static synchronized boolean deleteMessage(String seller, String customer, String message) {
        String filename = "conversation_data/" + seller + "_" + customer + "_Messages.csv";
        // has to copy file contents, then delete file, then rewrite file
        ArrayList<String> fileContents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            return false;
        }

        // overwrites file
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, false))) {
            for (String fileContent : fileContents) {
                // if the line is not the message, then it is written to the file

                // parses message out of string
                String[] messageParts = fileContent.split(",");
                String messageContent = messageParts[2];

                if (!messageContent.equals(message)) {
                    pw.println(fileContent);
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean sendMessage(String seller, String customer, boolean ifSeller, String message) {
        String folderName = "conversation_data";
        String filename = folderName + "/" + seller + "_" + customer + "_Messages.csv";
        String sender;
        if (!ifSeller) {
            Seller existingSeller = new Seller(seller);
            messageCustomer(seller, customer, message);
        } else {
            Customer existingCustomer = new Customer(customer);
            messageSeller(seller, customer, message);
        }
        return true;
    }

    public static synchronized void messageSeller(String seller, String customer, String message) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = dtf.format(now);
        String date = currentDateTime.substring(0, 10);
        String time = currentDateTime.substring(11);
        Message message1 = new Message(seller, customer, message, date, time);

        try (PrintWriter pw = new PrintWriter(new FileWriter("conversation_data/" + seller + "_" + customer + "_Messages.csv", true))) {
            pw.println(message1.toString());
        } catch (IOException e) {
            System.out.println("That does not work!");
        }
    }

    // manages each client thread
    public static synchronized boolean createAccount(int accountType, String email, String password) {
        // Sets filename based on account type
        boolean isUser = isUser(email);
        if (isUser) {
            String filenameTxt;
            String filenameCsv;
            if (accountType == 0) {
                filenameCsv = "customer_data/CustomersList.csv";
                filenameTxt = "customer_data/customerNames.txt";
            } else if (accountType == 1) {
                filenameCsv = "seller_data/SellersList.csv";
                filenameTxt = "seller_data/sellerNames.txt";
            } else {
                return false;
            }
            // makes sure customer is not a duplicate
            try {
                BufferedReader br = new BufferedReader(new FileReader(filenameTxt));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] lineArray = line.split("-");
                    String emailToCheck = lineArray[0];
                    if (emailToCheck.equals(email)) {
                        return false;
                    }
                }
            } catch (IOException e) {
                return false;
            }
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(filenameTxt, true));
                pw.println(email + "-" + password);
                pw.close();
                PrintWriter printWriter = new PrintWriter(new FileWriter(filenameCsv, true));
                printWriter.println(email + ",false,null");
                printWriter.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    // sends arraylist of messages to client
    public static synchronized ArrayList<String> getConversationHistory(String seller, String customer, boolean ifSeller) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> messageList = new ArrayList<>(); // list of messages to be sent to client
        String folderName = "conversation_data";
        String filename = folderName + "/" + seller + "_" + customer + "_Messages.csv";

        try (BufferedReader bfr = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                list.add(line);
            }
            String[] messages = new String[list.size()];
            String[] senderList = new String[list.size()];
            String[] dateStamp = new String[list.size()];
            String[] timeStamp = new String[list.size()];
            for (int i = 0; i < messages.length; i++) {
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
        } catch (FileNotFoundException e) {
            messageList.add("You have no message history with this user");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error in Program, please refresh", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return messageList;
    }

    public static synchronized void messageCustomer(String seller, String customer, String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = dtf.format(now);
        String date = currentDateTime.substring(0, 10);
        String time = currentDateTime.substring(11);
        Message message1 = new Message(customer, seller, message, date, time);

        String fileName = "conversation_data/" + seller + "_" + customer + "_Messages.csv";
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println(message1);
        } catch (IOException e) {
            System.out.println("That does not work!");
        }
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
            while ((line = bufferedReader.readLine()) != null) {
                customers.add(line);
            }
            for (String s : customers) {
                String customer = s.split("-")[0];
                if (customer.equals(user)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("seller_data/sellerNames.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sellers.add(line);
            }
            for (String s : sellers) {
                String seller = s.split("-")[0];
                if (seller.equals(user)) {
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
                while ((line = bufferedReader.readLine()) != null) {
                    sellers.add(line);
                }
                for (String s : sellers) {
                    String seller = s.split("-")[0];
                    if (seller.equals(user)) {
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

    public static synchronized boolean editMessage(String seller, String customer, String messageToEdit, String editedMessage) {
        String filename = "conversation_data/" + seller + "_" + customer + "_Messages.csv";
        // has to copy file contents, then delete file, then rewrite file
        ArrayList<String> fileContents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            return false;
        }
        for (int i = 0; i < fileContents.size(); i++) {
            String[] messageParts = fileContents.get(i).split(",");
            String messageContent = messageParts[2];
            if (messageContent.equals(messageToEdit)) {
                messageParts[2] = editedMessage;
                String toAdd = "";
                toAdd += messageParts[0] + "," + messageParts[1] + "," + messageParts[2] + "," + messageParts[3] + "," + messageParts[4];
                fileContents.set(i, toAdd);
            }
        }
        // overwrites file
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, false))) {
            for (String fileContent : fileContents) {
                pw.println(fileContent);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}