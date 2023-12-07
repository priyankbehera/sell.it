# Project-5
# Sell.it - A marketplace messaging platform

### Created by: Brayden Reimann, Shreya Gupta, Priyank Behera, and Matthew Allen.

Shreya Gupta -- Submitted Report on Brightspace;
Matthew Allen -- Submitted Vocareum workspace

## RUN:
- Compliling Instructions: `javac Account.java`
                          `javac Conversation.java`
                          `javac Customer.java`
                            `javac Main.java`
                           `javac Message.java`
                            `javac RunLocalTest.java`
                            `javac Seller.java`
                            `javac User.java`
  
- Run Instructions: `cd src/`, `java Main`

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

## 2. User Class

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

## 5. Conversation Class

Functionality:
- Containts constructors and getters for the users and messages
- the newFile method creates a file based on the username
- The writeToFile method appends the conversation to an already created file given a message. It will also throw an IOException if an error occurs while writing to the file.
- The readConversation method reads a conversation from the given filename and stores the data in an ArrayList of messages. It will also throw an IOException if an error occurs while reading from the file.
- The viewListOfCustomers method allows a seller to view the list of customers they can message.
- The viewListOfStores method allows a customer to view the list of stores they can message.
- The readCustomersFromFile method is used by the viewListOfCustomers method to read all of the customers from the file.
- The readSellersFromFile method is used by the viewListOfStores method to read all of the sellers from the file.
- The viewMessages method allows the user to see all of their messages.
  
Relationships:
- Utilizes the Message class to create, handle, and represent messages within the conversation.
- Utilizes PrintWriter and FileWriter to write messages to the conversation file.
- Interacts with external files to read and write conversation data.

## 6. Main Class

Functionality: 
- Acts as the control center for the Sell.it messaging platform.
- Contains the main method which serves as the entry point for all of the code.
- The checkLogin method checks the login details inputted by the user and returns true if the details match what is on file.
- The addLoginDetails method adds the user's login credentials to the specified file.
- The checkValidity method checks the validity of the email by searching the email files.

Relationships:
- Utilizes all of the classes

## Testing:
To create modular, specific tests for each feature of our program, we created multiple JUnit tests. These tests
ensured that all features worked properly with valid input, and did not crash the program given
invalid input.  
The tests are as follows:

1. **testCustomerCreation** creation: ensures that customer can be created
2. **testSellerCreation**: ensures that seller can be created
3. **testCustomerLogin**: ensures that customer can login
4. **testSellerLogin**: ensures that seller can login
5. **testMessageSeller**: verify that a customer can message a seller
6. **testMessageCustomer**: verify that a seller can message a customer
7. **testCustomerStatistics**: verify that a customer can view their statistics
8. **testSellerStatistics**: verify that a seller can view their statistics
9. **testCustomerDuplicate**: a customer cannot create an account with an email that already exists.
10. **testSellerDuplicate**: a seller cannot create an account with an email that already exists.
11. **testFindSeller**: verify that a customer can find a *valid* seller
12. **testFindCustomer**: verify that a seller can find a *valid* customer
13. **testGetStores**: ensures customer get get a list of stores
14. **testGetCustomers**: ensures seller can get a list of customers
15. **ErrorInvalidFindSeller**: If user tries to find a seller that does not exist, it must not crash
16. **ErrorInvalidFindCustomer**: If user tries to find a customer that does not exist, it must not crash
17. **InvalidCustomer**: User cannot create a customer with an invalid email
18. **InvalidSeller**: User cannot create a seller with an invalid email

