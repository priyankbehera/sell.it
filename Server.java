import Objects.Customer;
import Objects.Message;
import Objects.Seller;

import javax.swing.*;
import java.lang.reflect.Array;
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
                ArrayList<String> censoredKeywords;

                // Checks to see if the user is a seller or a customer and retrieves censored keywords
                if (ifSeller) {
                    censoredKeywords = getCensoredKeywords(seller);
                } else {
                    censoredKeywords = getCensoredKeywords(customer);
                }

                if (censoredKeywords != null) {
                    for (String message : messageList) {
                        for (String censoredKeyword : censoredKeywords) {
                            if (message.equalsIgnoreCase("message")) {
                                message = message.replace(censoredKeyword, "[redacted]");
                            }
                        }
                        printWriter.println(message);
                        printWriter.flush();
                    }
                } else {
                    for (String message: messageList) {
                        printWriter.println(message);
                        printWriter.flush();
                    }
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
                String seller = args[0];
                String customer = args[1];
                boolean ifSeller = Boolean.parseBoolean(args[2]);
                String filename = args[3];
                boolean success = importFile(seller, customer, ifSeller, filename);
                printWriter.println(success);
                printWriter.flush();
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
            case "addStore" -> {
                String seller = args[0];
                String storeName = args[1];
                String description = args[2];

                boolean success = addStore(seller, storeName, description);
                printWriter.println(success);
                printWriter.flush();

            }
            case "getStores" -> {
                System.out.println("Getting stores");
                String seller = args[0];
                boolean success = getStores(seller, printWriter);
            }
            case "blockUser" -> {
                System.out.println("Blocking user: " + args[1]);
                String blocker = args[0]; // person blocking
                String toBlock = args[1]; //person getting blocked

                boolean success = blockUser(blocker, toBlock);
                printWriter.println(success);
                printWriter.flush();

            }
            case "getBlockedUsers" -> {
                System.out.println("Getting blocked users");
                String blocker = args[0];
                getBlockedUsers(blocker, printWriter);
            }
            case "getUsers" -> {
                boolean ifSeller = Boolean.parseBoolean(args[0]);
                getList(ifSeller, printWriter);
            }
            case "deleteAccount" -> {
                String email = args[0];
                boolean accountType = isUser(email);
                boolean success = deleteAccount(accountType, email);
                printWriter.println(success);
                printWriter.flush();

            }
            case "editAccount" -> {
                String email = args[0];
                String newEmail = args[1];
                String newPassword = args[2];
                boolean accountType = isUser(email);
                boolean success = editAccount(accountType, email, newEmail, newPassword);
                printWriter.println(success);
                printWriter.flush();

            }
            case "searchUser" -> {
                String name = args[0];
                boolean ifSeller = Boolean.parseBoolean(args[1]);
                boolean success = searchUser(name, ifSeller);
                printWriter.println(success);
                printWriter.flush();
            }
            case "setKeyword" -> {
                String user = args[0];
                String keyword = args[1];
                boolean success = setCensoredKeyword(user, keyword);
                printWriter.println(success);
                printWriter.flush();
            }
        }
    }

    public static synchronized boolean editAccount(boolean accountType, String email, String newEmail, String newPassword) {
        String filename1;
        String filename2;

        if (accountType) {
            filename1 = "seller_data/SellersList.csv";
            filename2 = "seller_data/sellerNames.txt";
        } else {
            filename1 = "customer_data/CustomersList.csv";
            filename2 = "customer_data/customerNames.txt";
        }
        //removes from file

        // copys file contents
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename1))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            return false;
        }
        // overwrites file
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename1, false))) {
            for (String fileContent : list) {
                String[] lineArray = fileContent.split(",");
                String emailToCheck = lineArray[0];
                if (!emailToCheck.equals(email)) {
                    pw.println(fileContent);
                } else {
                    pw.println(newEmail + ",false,null");
                }
            }
        } catch (IOException e) {
            return false;
        }
        //same thingfor other file
        ArrayList<String> list2 = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename2))) {
            String line;
            while ((line = br.readLine()) != null) {
                list2.add(line);
            }
        } catch (IOException e) {
            return false;
        }

        // overwrites file
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename2, false))) {
            for (String fileContent : list2) {
                String[] lineArray = fileContent.split("-");
                String emailToCheck = lineArray[0];
                if (!emailToCheck.equals(email)) {
                    pw.println(fileContent);
                } else {
                    pw.println(newEmail + "-" + newPassword);
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean deleteAccount(boolean accountType, String email) {
        String filename1;
        String filename2;

        if (accountType) {
            filename1 = "seller_data/SellersList.csv";
            filename2 = "seller_data/sellerNames.txt";
        } else {
            filename1 = "customer_data/CustomersList.csv";
            filename2 = "customer_data/customerNames.txt";
        }
        //removes from file

        // copys file contents
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename1))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            return false;
        }
        // overwrites file
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename1, false))) {
            for (String fileContent : list) {
                String[] lineArray = fileContent.split(",");
                String emailToCheck = lineArray[0];
                if (!emailToCheck.equals(email)) {
                    pw.println(fileContent);
                }
            }
        } catch (IOException e) {
            return false;
        }
        // same thing for other file
        ArrayList<String> list2 = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename2))) {
            String line;
            while ((line = br.readLine()) != null) {
                list2.add(line);
            }
        } catch (IOException e) {
            return false;
        }
        // overwrites file
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename2, false))) {
            for (String fileContent : list2) {
                String[] lineArray = fileContent.split("-");
                String emailToCheck = lineArray[0];
                if (!emailToCheck.equals(email)) {
                    pw.println(fileContent);
                }
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static synchronized boolean searchUser(String name, boolean ifSeller) {
        ArrayList<String> list = new ArrayList<>();
        boolean isPresent = false;
        String folderName = ifSeller ? "customer_data" : "seller_data";
        String filename = ifSeller ? "/CustomersList.csv" : "/SellersList.csv";
        filename = folderName + filename;
        try (BufferedReader bfr = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                list.add(line);
            }
            String[] usernames = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                String username = list.get(i).split(",")[0];
                usernames[i] = username;
            }
            for (String username : usernames) {
                //TODO: add invisible users list
                if (username.equals(name)) {
                    isPresent = true;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error - IOException");
        }
        return isPresent;
    }

    public static synchronized boolean getList(boolean ifSeller, PrintWriter pw) {
        ArrayList<String> menuList = new ArrayList<>();
        String folderName = ifSeller ? "customer_data" : "seller_data";
        String filename;
        if (ifSeller) {
            filename = folderName + "/CustomersList.csv";
        } else {
            filename = folderName + "/SellersList.csv";
        }
        try (BufferedReader bfr = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                menuList.add(line);
            }
            String[] menuArray = new String[menuList.size()];
            for (int i = 0; i < menuArray.length; i++) {
                String customerName = menuList.get(i).split(",")[0];
/*                if (!isVisible && isUserBlocked(customerName, currentUser)) {
                    continue;
                }*/
                menuArray[i] = customerName;
            }

            // send to client
            try {
                String list = "";
                for (String s : menuArray) {
                    list += s + ",";
                }
                list = list.substring(0, list.length() - 1); //removes trailing comma
                pw.println(list);
                pw.flush();
            } catch (Exception e) {
                return false;
            }

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean addStore(String seller, String storeName, String description) {
        String filename = "seller_data/stores/" + seller + "_Stores.csv";
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) {
            pw.println(storeName + "," + description);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // makes sure user is not blocked before sending message
    // returns false if blocked, true if not
    public static synchronized boolean checkBlocked(String toCheck, String sender) {

        String filename = "block_data/blocked/" + toCheck + "_Blocked.csv";
        ArrayList<String> blockedUsers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                blockedUsers.add(line);
            }
        } catch (IOException e) {
            return true;
        }
        for (String blockedUser : blockedUsers) {
            if (blockedUser.equals(sender)) {
                return false;
            }
        }
        return true;
    }

    public static synchronized boolean getBlockedUsers(String blocker, PrintWriter pw) {
        String filename = "block_data/blocked/" + blocker + "_Blocked.csv";
        // ensure file exists

        ArrayList<String> blockedUsers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                blockedUsers.add(line);
            }
        } catch (IOException e) {
            pw.println("No blocked users");
            pw.flush();
            return true;
        }
        // write to client
        String blocked = "";
        try {
            for (String blockedUser : blockedUsers) {
                blocked += blockedUser + ",";
            }
            blocked = blocked.substring(0, blocked.length() - 1); //removes trailing comma
            pw.println(blocked);
            pw.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean getStores(String seller, PrintWriter pw) {
        String filename = "seller_data/stores/" + seller + "_Stores.csv";
        ArrayList<String> stores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                stores.add(line);
            }
        } catch (IOException e) {
            return false;
        }

        try {
            for (String store : stores) {
                pw.println(store);
                pw.flush();
            }
            pw.println("end");
            pw.println("end");
            pw.flush();
            pw.println("sent stores");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static synchronized boolean sendFile(String selectedFile, String seller, String customer) {
        selectedFile += ".csv";
        ArrayList<String> list = new ArrayList<>();
        String contents = "Hello! Thank you for exporting a file through Sell.it!\n" +
                "This conversation file will take the following format:\n" +
                "(sender),(receiver),(message contents),(date),(time)\n";
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
        for (int i = 0; i < list.size(); i++) {
            String messageParts = list.get(i);
            contents += messageParts + "\n";
        }
        try (OutputStream outputStream = new FileOutputStream(selectedFile)) {
            outputStream.write(contents.getBytes());
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File could not be exported",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static synchronized boolean importFile(String seller, String customer, boolean ifSeller, String filename) {
        String fileContent = readFile(new File(filename)).trim();
        return sendMessage(seller, customer, ifSeller, fileContent);
    }

    private synchronized static String readFile(File file) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately based on your application's requirements
        }

        return content.toString();
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
        boolean success;
        if (!ifSeller) {
            Seller existingSeller = new Seller(seller);
            success = messageCustomer(seller, customer, message);
        } else {
            Customer existingCustomer = new Customer(customer);
            success = messageSeller(seller, customer, message);
        }
        return success;
    }

    public static synchronized boolean messageSeller(String seller, String customer, String message) {

        // wont send message if receipient blocked sender
        boolean blocked = checkBlocked(customer, seller);
        if (!blocked) {
            return false;
        }

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
            return false;
        }
        return true;
    }

    // manages each client thread
    public static synchronized boolean createAccount(int accountType, String email, String password) {
        // Sets filename based on account type
        boolean isUser = isUser(email);
        if (!isUser) {
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

    // blocks user
    public static synchronized boolean blockUser(String blocker, String toBlock) {
        String filename = "block_data/blocked/" + blocker + "_Blocked.csv";
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) {
            pw.println(toBlock);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // Writes the user's censored keyword to a file
    public static synchronized boolean setCensoredKeyword(String user, String keyword) {
        String filename = "keywords_data/" + user + "_censoredKeywords.csv";
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) {
            pw.println(keyword);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // Retrieves the user's censored keywords from a file
    public static ArrayList<String> getCensoredKeywords(String user) {
        ArrayList<String> censoredKeywords = new ArrayList<String>();
        String filename = "keywords_data/" + user + "_censoredKeywords.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                censoredKeywords.add(line);
            }
        } catch (IOException e) {
            return null;
        }
        return censoredKeywords;
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

    public static synchronized boolean messageCustomer(String seller, String customer, String message) {

        // wont send message if receipient blocked sender
        boolean blocked = checkBlocked(customer, seller);
        if (!blocked) {
            return false;
        }
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
            return false;
        }
        return true;
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