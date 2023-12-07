# Project-5
# Sell.it - A marketplace messaging platform

### Created by: Brayden Reimann, Shreya Gupta, Priyank Behera, and Matthew Allen.

Shreya Gupta -- Submitted Report on Brightspace;
Matthew Allen -- Submitted Vocareum workspace

## RUN:
- Compliling Instructions: 
  
- Run Instructions: `cd src/`, `java Main`


## Objects Folder

## 1. Customer Class

Functionality:
- Contains methods for messaging sellers, adding customers to the customer list, retrieving the list of stores, and determining if an email belongs to a seller.
- Manages customer-related functionalities like interacting with sellers and maintaining a list of stores.
- The messageSeller method allows the customer to send a message to a seller. It appends the message to the conversation history file associated with the seller.
- The addCustomers method takes in a customer as a parameter and adds it to the file "CustomersList.txt"
- The getStoresList method retrieves a list of stores from the "SellersList.csv" file.
- The getStoresSeller method searches the "SellersList.csv" file to find the seller whose store matches the provided store name and then returns the email of the corresponding seller.
- The isSeller method checks the "SellerList.txt" file to determine if the provided email belongs to a seller. It returns true if the email corresponds to a seller, otherwise false.
- The viewSentStatistics method retrieves and displays statistics related to messages sent by the customer.
- The viewRecievedStatistics method counts counts the number of messages received in each conversation and sorts the results based on the specified order.
- The readCustomerConversation method reads and formats the conversation history from a given file.
- The getConversation method retrieves the entire conversation history of the customer.
- The findCommonWords method analyzes the conversation history to find the most common words.


Relationships:
- Extends the User class and represents a customer user.
- Uses Message class for sending messages to sellers.

## 2. Message Class

Functionality:
- Represents a message with attributes such as sender, receiver, message content, date sent, and time sent.
- Provides getters and setters for these attributes.
  
Relationships:
- Used by Customer and Seller classes to create and store messages in their conversations.

## 3. Seller Class

Functionality:
- Contains methods for messaging customers, adding sellers to the seller list, retrieving the list of customers, and determining if an email belongs to a customer.
- Manages seller-related functionalities like interacting with customers and maintaining a list of customers.
- The mess method allows the seller to send a message to a customer. It does this by appending the message to the conversation history file associated with the customer.
- The addSellerList method takes in a customer as a parameter and adds it to the file "CustomersList.txt"
- The getCustomersList reads the "CustomersList.csv" file to extract customer emails and returns an ArrayList<String> containing the emails of the customers.
- The messageCustomer method creates a new message object, using date and time as retrieved by LocalDateTime. After creating the method, a String representation of the message is added to the file "customer.getemail() + '_Messages'", which contains all the customers' messages.
- The isCustomer method checks the "CustomersList.txt" file to determine if the provided email belongs to a customer and returns true if the email corresponds to a customer, otherwise false.
- The readSellerConversation method reads the conversation history from the file seller_customer_Messages.csv
- The viewSentStatistics method retrieves and displays statistics related to messages sent by the seller.
- The viewRecievedStatistics method counts counts the number of messages received in each conversation and sorts the results based on the specified order.
- The findCommonWords method analyzes the conversation history to find the most common words.
  
Relationships:
- Extends the User class and represents a seller user.
- Uses Message class for sending messages to customers.

## 4. User Class

Functionality: 
- Makes a generic user with the attributes of email, conversation history, and a list of messages.
- Handles user login: checking whether the user is a customer or seller, and viewing received messages.
- Manages the user's conversation history, including logging conversations and reading from conversation history files.
- The LogConversation method allows you to log a user's conversation, pass in the file and email as parameters. The method will then print the name of the logFile to the user's src.Conversation History in append mode. If the conversation is successfully logged, hasConversationHistory will be set to true.
- The isCustomerOrSeller method takes in a user object as a parameter, and then searches for the users' email in the list of customers and list of sellers. The method returns "src.Customer" if the user is a customer and "src.Seller" if the user is a seller. Otherwise, it returns null.
- The readConversation method is responsible for reading and parsing the content of a conversation file associated with the user.
- The viewReceivedMessages method in the User class is responsible for displaying messages that the user has received. It does this by checking whether the user has an existing conversation history. If the user does not have a conversation history, it prints an error message indicating that no conversation history is available.
- The blockUser function is used to block another user. It takes the email of the user to be blocked.
- The isBlocked method checks if a given user with the specified email is blocked by the current user.
- The removeFromConversationHistory method removes the conversation history associated with a blocked user. It takes the email of the blocked user, searches for conversation history files containing that email, and deletes them.
- The becomeInvisible method and the becomeVisible method allow the user to either become visible or invisible to the other user.
- The isInvisible method verifies whether the user is currently marked as invisible and false otherwise. 

Relationships:
- Inherited by Customer and Seller classes, sharing common functionality.
- Used by Main class for user authentication and interaction.

## Panels Folder

## 5. DisplayMessagesPanel

Functionality: 
- The requestConversationHistory method sends a request to the server for the conversation history between the seller and customer.
- The sendMessageRequest method sends a message request to the server which includes the sender, recipient, and the message content.

Relationships: 

## 6. MenuPanel

Functionality:
- The showMenuPopup method creates and displays a popup menu when the "More" button is clicked.
- The displayMessageStatistics method gets selected user's message statistics (sent, received, common words) and displays them in a JOptionPane
- The blockSelectedUser method blocks the selected user and removes them from the conversation history
- The removeFromConversationHistory method removes the blocked user's conversation history files.
- The searchUser method searches for a user with the given name in the customer or seller data file.
- The getList method retrieves a list of users from the respective data file.
- The createJList method creates a JList with the given array of people and sets it as the model for the messageList
- The sortSentMessages method retrieves and displays sorted sent or received messages statistics.

Relationship:

## Other Classes

## 6. Main Class

Functionality: 
- Acts as the control center for the Sell.it messaging platform.
- Contains the main method which serves as the entry point for all of the code.

Relationships:
- Utilizes all of the classes

## 7. Server

Functionality:
- Server class that handles requests from the client. This allows all data handling to be done server-side, increasing security and limiting the amount of data sent to the client.
- The hostname is localhost
- The serverSocket is 4242
- The requests method parses the client request and dispatches the appropriate method based on the request type (login, createAccount, getConversationHistory, sendMessage).
- The login method returns false if login is invalid. If login is valid, the second term is the boolean ifSeller. If it is not a user, it returns false
- The sendMessage method sends a message from a seller to a customer or vice versa.
- The messageSeller method appends the message to the conversation file between the seller and the customer.
- The createAccount method creates a new account and adds the user to the respective data files.
- The getConversationHistory method retrieves the conversation history between a seller and a customer.
- The messageCustomer method appends the message to the conversation file between the customer and the seller.
- The clientManager class implements the Runnable to handle communication with a specific client in a separate thread.
- The isUser method checks if the email is present in either the customer or seller data files.
- The ifSeller method uses isUser to determine if the user is a seller.

Relationships: 

## Testing:


