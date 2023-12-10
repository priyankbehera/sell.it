# Project-5
# Sell.it - A marketplace messaging platform

### Created by: Brayden Reimann, Shreya Gupta, Priyank Behera, and Matthew Allen.

Shreya Gupta -- Submitted Report on Brightspace;
Matthew Allen -- Submitted Vocareum workspace

## RUN:
- Compliling Instructions: `mkdir out && javac -d out -sourcepath src src/*.java`
  - This will make a directory called `out` for the .class files, and then compile all .java files.
  
- Run Instructions: `cd out/`, `java Main`


## Objects Folder

## 1. Customer Class

Functionality:
- The class provides multiple constructors for creating instances of the Customer class. These constructors seem to allow different ways of initializing a Customer object, including specifying email, conversation log information, and password.

Relationships:
- Extends the User class and represents a customer user.

## 2. Message Class

Functionality:
- Represents a message with attributes such as sender, receiver, message content, date sent, and time sent.
- Provides getters and setters for these attributes.
  
Relationships:
- Used by Customer and Seller classes to create and store messages in their conversations.

## 3. Seller Class

Functionality:
- The class provides constructors for creating instances of the Seller class. These constructors allow different ways of initializing a Seller object, including specifying email, conversation log information, store, and password.
- The getStores method is a getter method for retrieving the value of the store field.
- The getEmail gets the email associated with the seller.
  
Relationships:
- Extends the User class and represents a seller user.

## 4. User Class

Functionality: 
- Makes a generic user with the attributes of email, conversation log information, and password.
- Getter methods getEmail, hasConversationHistory, and getConversationHistoryFile allow access to the values of the corresponding fields.

Relationships:
- Inherited by Customer and Seller classes, sharing common functionality.
- Used by Main class for user authentication and interaction.

## Panels Folder

## 5. CreateAccPanel
- Panel for creating a new account
- Continue Button: Initiates the account creation process.
- Return to Sign in Button: Allows users to navigate back to the sign-in page.
- The resetPanel method clears the text fields and resets the drop-down menu, allowing the panel to be reused for multiple account creation attempts.
- The accessor methods getEmail, getPassword, and getAccountType provides methods to retrieve user input from the email, password, and account type fields.
- The accessor methods getContinueButton, getReturnLoginButton, and getSuccessMessage provides methods to access the buttons and success message label from other classes.

Relationships:
- extends JPanel

## 6. DisplayMessagesPanel

Functionality: 
- Panel designed for displaying and interacting with messages
- Includes various buttons for actions such as viewing stores, adding a store, editing messages, deleting messages, exporting messages, and importing messages.
- The requestConversationHistory method sends a request to the server for the conversation history between the seller and customer.
- The sendMessageRequest method sends a message request to the server which includes the sender, recipient, and the message content.
- The exportFileAction method prompts the user to select a location to export the messages and sends a request to the server for exporting messages.
- The importFileAction method prompts the user to select a file to import messages and sends a request to the server for importing messages.
- Uses a timer to periodically update the conversation area by requesting the latest conversation history from the server.

Relationships: 
- extends JPanel

## 7. HomePanel

Functionality:
- Responsible for displaying messages and interacting with users.
- Creates an instance of MenuPanel and adds it to the west side of the layout.
- Creates instances of DisplayMessagesPanel for each user and adds them to the card panel.
- The getList method retrieves the list of users from the CSV file and then reads the user data from the appropriate file (CustomersList.csv or SellersList.csv).

Relationships: 
- MenuPanel
- DisplayMessagesPanel

## 8. MenuPanel

Functionality:
- Provides functionality for displaying a list of users, searching for users, and accessing various user-related actions through a popup menu. 
- Uses a Timer to periodically refresh the user list.
- The showMenuPopup method creates and displays a popup menu when the "More" button is clicked.
- The blockSelectedUser method blocks the selected user and removes them from the conversation history
- The removeFromConversationHistory method removes the blocked user's conversation history files.
- The searchUser method searches for a user with the given name in the customer or seller data file.
- The getList method retrieves a list of users from the respective data file.
- The createJList method creates a JList with the given array of people and sets it as the model for the messageList
- The setCensoredKeyword method sets a censored keyword for the current user.
- The requestBlock method sends a request to the server to block a user.
- The requestBlockedUsers method sends a request to the server to get a list of blocked users for the current user.

Relationship:
- Extends JPanel

## 10. WelcomePanel

Functionality: 
- Initializes the welcome panel with components such as logo, login fields, buttons, and a success message label.
- The getEmail method provides access to the entered email in the text field.
- The getPassword method provides access to the entered password in the password field.
- The getContinueButton method provides access to the "Continue" button.
- The getCreateAccButton method provides access to the "Create An Account" button.
- The getSuccessMessage method provides access to the success message label.

Relationships: 
- extends JPanel

## Other Classes

## 6. Main Class

Functionality: 
- Acts as the control center for the Sell.it messaging platform.
- Contains the main method which serves as the entry point for all of the code.

Relationships:
- Utilizes all of the classes

## 7. Message 

Functionality: 
- Represents a message with attributes such as sender, receiver, message content, date sent, and time sent.
- The constructor initializes a Message object with the provided sender, receiver, message content, date sent, and time sent.

Relationships: 
- none

## 8. Server

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
- Utilizes all of the classes


