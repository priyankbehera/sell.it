package Panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SellerStoreTestPanel extends JPanel {
    // important buttons to add action listeners
    public static CircularButton addButton;
    public static JButton editButton;
    public static JButton marketplace;
    public static JButton backButton;
    public static JButton yourAccountButton;
    public static JButton addLinearButton;
    public static JButton editLinearButton;
    public static JButton deleteLinearButton;
    public static JButton productButton;
    public static JButton valuesButton;
    public static JButton customerButton;
    public static CircularButton circularProductButton;
    public static CircularButton circularCustomerButton;
    public static CircularButton circularValueButton;
    public static JTextArea storeName;
    public static JTextArea storeDescription;
    public static JButton confirmButton;
    public static CircularButton deleteButton;
    public static CircularButton editProductButton;
    public static CircularButton logoButton;
    public static CircularButton circularYourAccountButton;
    public static CircularButton circularBackButton;

    // getters to add action listeners
    public static CircularButton getLogoButton() {
        return logoButton;
    }
    public static CircularButton getCircularYourAccountButton() {
        return circularYourAccountButton;
    }
    public static CircularButton getCircularBackButton() {
        return circularBackButton;
    }
    public static CircularButton getAddButton() {
        return addButton;
    }

    public static JButton getMarketplace() {
        return marketplace;
    }

    public static JButton getBackButton() {
        return backButton;
    }

    public static JButton getYourAccountButton() {
        return yourAccountButton;
    }

    public static JButton getAddLinearButton() {
        return addLinearButton;
    }

    public static JButton getEditLinearButton() {
        return editLinearButton;
    }

    public static JButton getDeleteLinearButton() {
        return deleteLinearButton;
    }

    public static JButton getProductButton() {
        return productButton;
    }

    public static JButton getValuesButton() {
        return valuesButton;
    }

    public static JButton getCustomerButton() {
        return customerButton;
    }

    public static CircularButton getCircularProductButton() {
        return circularProductButton;
    }

    public static CircularButton getCircularCustomerButton() {
        return circularCustomerButton;
    }

    public static CircularButton getCircularValueButton() {
        return circularValueButton;
    }

    public static JTextArea getStoreName() {
        return storeName;
    }

    public static JTextArea getStoreDescription() {
        return storeDescription;
    }

    public static JButton getConfirmButton() {
        return confirmButton;
    }

    public static CircularButton getDeleteButton() {
        return deleteButton;
    }

    public static CircularButton getEditProductButton() {
        return editProductButton;
    }

    public static CircularButton getCircularButton() {
        return addButton;
    }

    public static JButton getEditButton() {
        return editButton;
    }

    public SellerStoreTestPanel() {
        // Set the preferred size of the panel
        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));

        Color mediumBlue = new Color(54, 91, 131);
        Color lightBlue = new Color(146, 164, 184);
        Color beige = new Color(231, 217, 194);
        Color darkBlue = new Color(27, 50, 84);

        // all the position of rectangles
        Rectangle nameAndDescription = new Rectangle(0, 0, 668, 154);
        Rectangle logoBackAndAccount = new Rectangle(668, 0, 356, 154);
        Rectangle productsList = new Rectangle(0, 154, 633, 614);
        Rectangle productActions = new Rectangle(633, 154, 216, 614);
        Rectangle sales = new Rectangle(849, 154, 175, 614);

        // logo panel logic
        JPanel logo = new JPanel();
        logo.setPreferredSize(logoBackAndAccount.getSize());
        logo.setBounds(logoBackAndAccount);
        logo.setBackground(darkBlue);

        JPanel circularImageButtonPanel = new JPanel();
        circularImageButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,-2));

        circularImageButtonPanel.setPreferredSize(new Dimension(356, 154));

        circularImageButtonPanel.setForeground(darkBlue);

        // Add circular buttons with images
        logoButton = new CircularButton("Images/circular_image.png", 100);
        marketplace = new JButton("About Us");
        marketplace.setForeground(Color.white);
        marketplace.setBorderPainted(false); // Set to false to remove the border around the text
        marketplace.setContentAreaFilled(false);
        JPanel panelOneA = new JPanel();
        panelOneA.setBackground(darkBlue);
        panelOneA.setLayout(new BorderLayout());
        JPanel panelOneTemp = new JPanel();
        panelOneTemp.setPreferredSize(new Dimension(100,100));
        panelOneTemp.setBackground(darkBlue);
        panelOneTemp.add(logoButton);
        panelOneA.add(panelOneTemp, BorderLayout.CENTER);
        panelOneA.add(marketplace, BorderLayout.SOUTH);
        logo.add(panelOneA);

        circularBackButton = new CircularButton("Images/back_button.png", 100);
        backButton = new JButton("Back");
        backButton.setForeground(Color.white);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        JPanel panelTwoA = new JPanel();
        panelTwoA.setBackground(darkBlue);
        panelTwoA.setLayout(new BorderLayout());
        JPanel panelTwoTemp = new JPanel();
        panelTwoTemp.setPreferredSize(new Dimension(100,100));
        panelTwoTemp.setBackground(darkBlue);
        panelTwoTemp.add(circularBackButton);
        panelTwoA.add(panelTwoTemp, BorderLayout.CENTER);
        panelTwoA.add(backButton, BorderLayout.SOUTH);
        logo.add(panelTwoA);

        circularYourAccountButton = new CircularButton("Images/yourAccount.png", 100);
        yourAccountButton = new JButton("Your Account");
        yourAccountButton.setForeground(Color.white);
        yourAccountButton.setBorderPainted(false);
        yourAccountButton.setContentAreaFilled(false);
        JPanel panelThreeA = new JPanel();
        panelThreeA.setBackground(darkBlue);
        panelThreeA.setLayout(new BorderLayout());
        JPanel panelThreeTemp = new JPanel();
        panelThreeTemp.setPreferredSize(new Dimension(100,100));
        panelThreeTemp.setBackground(darkBlue);
        panelThreeTemp.add(circularYourAccountButton);
        panelThreeA.add(panelThreeTemp, BorderLayout.CENTER);
        panelThreeA.add(yourAccountButton, BorderLayout.SOUTH);
        logo.add(panelThreeA);

        circularImageButtonPanel.setBackground(darkBlue);
        logo.add(circularImageButtonPanel);

        add(logo);

        // name and description panel logic
        JPanel storeNameAndDescription = new JPanel();
        storeNameAndDescription.setBackground(Color.white);
        storeNameAndDescription.setLayout(new BorderLayout());
        storeNameAndDescription.setPreferredSize(nameAndDescription.getSize());
        storeNameAndDescription.setBounds(nameAndDescription);
        storeNameAndDescription.setBackground(darkBlue);

        // bordering
        Border border1 = BorderFactory.createLineBorder(darkBlue, 15);
        storeNameAndDescription.setBorder(border1);

        // formatting
        Font storeNameFont = new Font("Smiley Sans", Font.PLAIN, 24);
        Font storeDescriptionFont = new Font("Smiley Sans", Font.PLAIN, 16);
        // "Store Name" is a variable
        // "Store Description" is a variable
        storeName = new JTextArea("Store Name");
        storeName.setForeground(Color.WHITE);
        storeName.setBackground(darkBlue);
        storeName.setFont(storeNameFont);
        storeName.setEditable(false);

        // *Note: the store description is a maximum of 5 lines
        // *Note: the store description length per line is 75 characters -> aim for 50 considering for capitalis
        storeDescription = new JTextArea("Store Description");
        storeDescription.setForeground(Color.WHITE);
        storeDescription.setBackground(darkBlue);
        storeDescription.setFont(storeDescriptionFont);
        storeDescription.setEditable(false);

        // edit and confirm buttons
        // sets a boolean of edit button clicked to true
        // sets the text area to editable
        // confirms as "confirm" button
        Font buttonFont = new Font("Smiley Sans", Font.PLAIN, 14);

//        marketplace = new JButton("About Us");
//        marketplace.setForeground(Color.white);
//        marketplace.setBorderPainted(false); // Set to false to remove the border around the text
//        marketplace.setContentAreaFilled(false);

        editButton = new JButton("Edit");
        editButton.setFont(buttonFont);
        editButton.setForeground(Color.WHITE);
        editButton.setBorderPainted(false);
        editButton.setContentAreaFilled(false);
        editButton.setFocusPainted(false);

        confirmButton = new JButton("Confirm");
        confirmButton.setFont(buttonFont);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBorderPainted(false);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setFocusPainted(false);

        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setBackground(darkBlue);
        buttonsContainer.setLayout(new BoxLayout(buttonsContainer, BoxLayout.Y_AXIS));

        // Add buttons to the container
        buttonsContainer.add(editButton);
        buttonsContainer.add(confirmButton);

        // adding components
        storeNameAndDescription.add(storeName, BorderLayout.NORTH);
        storeNameAndDescription.add(storeDescription, BorderLayout.CENTER);
        storeNameAndDescription.add(buttonsContainer, BorderLayout.EAST);

        add(storeNameAndDescription);

        // setting the title of products
        Font productsFont = new Font("Smiley Sans", Font.ITALIC, 24);
        JPanel productsLabel = new JPanel();
        productsLabel.setBackground(mediumBlue);
        productsLabel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
        JLabel products = new JLabel("Your Products");
        products.setForeground(Color.WHITE);
        products.setFont(productsFont);
        productsLabel.add(products);

        // working on the products list
        JPanel productsContainer = new JPanel();

        productsContainer.setLayout(new BorderLayout());

        // products list container
        JPanel productsListContainer = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.white, 25);
        productsListContainer.setBorder(border);
        productsListContainer.setBackground(Color.white);
        GridLayout gridLayout = new GridLayout(0,2);
        gridLayout.setVgap(20);
        gridLayout.setHgap(20);
        productsListContainer.setLayout(gridLayout);

        // @Todo add mouse listeners to wait for clicks on these panels when made
        // @Todo also add an array of the panels that need to be named, their names, etc
        MiniProductTestPanel panelOne = new MiniProductTestPanel("Product One", 15, "Images/circular_image.png");

        MiniProductTestPanel panelTwo = new MiniProductTestPanel("Product Two", 10, "Images/inbox.png");

        MiniProductTestPanel panelThree = new MiniProductTestPanel("Product Three", 12, "Images/Social.png");

        productsListContainer.add(panelOne);
        productsListContainer.add(panelTwo);
        productsListContainer.add(panelThree);

        JScrollPane productsListScrollPane = new JScrollPane(productsListContainer);

        productsListScrollPane.setViewportBorder(null);
        productsListScrollPane.setBorder(null);

        productsListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        productsListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        productsContainer.setPreferredSize(new Dimension(633,614));
        productsContainer.setBounds(productsList);

        // adding the panels to the container pane
        productsContainer.add(productsListScrollPane, BorderLayout.CENTER);
        productsContainer.add(productsLabel, BorderLayout.NORTH);

        // adding the products list container
        add(productsContainer);



        // area for all the buttons and action
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(lightBlue);
        buttonPanel.setPreferredSize(productActions.getSize());
        buttonPanel.setBounds(productActions);

        BoxLayout boxLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
        buttonPanel.setLayout(boxLayout);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JLabel productActionsLabel = new JLabel("Product Actions");
        Font headerFontA = new Font("Smiley Sans", Font.PLAIN, 24);
        productActionsLabel.setForeground(darkBlue);
        productActionsLabel.setFont(headerFontA);

        productActionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(productActionsLabel);

        buttonPanel.add(Box.createRigidArea(new Dimension(0,40)));

        JPanel buttonPanelToAdd = new JPanel();
        Dimension size = productActions.getSize();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0,0);
        buttonPanelToAdd.setLayout(flowLayout);

        buttonPanelToAdd.setPreferredSize(size);

        buttonPanelToAdd.setBackground(lightBlue);

        // adding buttons
        JPanel addButtonPanel = new JPanel();
        addButtonPanel.setLayout(new BorderLayout());
        addButtonPanel.setBackground(lightBlue);
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(lightBlue);
        tempPanel.setPreferredSize(new Dimension(100,100));
        addButton = new CircularButton("Images/add.png", 100);
        tempPanel.add(addButton);
        addButtonPanel.add(tempPanel, BorderLayout.CENTER);

        addLinearButton = new JButton("Add Product");
        addLinearButton.setFont(buttonFont);
        addLinearButton.setForeground(Color.WHITE);
        addLinearButton.setBorderPainted(false);
        addLinearButton.setContentAreaFilled(false);
        addLinearButton.setFocusPainted(false);
        addButtonPanel.add(addLinearButton, BorderLayout.SOUTH);
        buttonPanelToAdd.add(addButtonPanel);

        JPanel editButtonPanel = new JPanel();
        editButtonPanel.setLayout(new BorderLayout());
        editButtonPanel.setBackground(lightBlue);
        JPanel tempPanelOne = new JPanel();
        tempPanelOne.setBackground(lightBlue);
        tempPanelOne.setPreferredSize(new Dimension(100,100));
        editProductButton = new CircularButton("Images/editStores.png", 100);
        tempPanelOne.add(editProductButton);
        editButtonPanel.add(tempPanelOne, BorderLayout.CENTER);

        editLinearButton = new JButton("Edit Product");
        editLinearButton.setFont(buttonFont);
        editLinearButton.setForeground(Color.WHITE);
        editLinearButton.setBorderPainted(false);
        editLinearButton.setContentAreaFilled(false);
        editLinearButton.setFocusPainted(false);
        editButtonPanel.add(editLinearButton, BorderLayout.SOUTH);
        buttonPanelToAdd.add(editButtonPanel);

        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.setLayout(new BorderLayout());
        deleteButtonPanel.setBackground(lightBlue);
        JPanel tempPanelTwo = new JPanel();
        tempPanelTwo.setBackground(lightBlue);
        tempPanelTwo.setPreferredSize(new Dimension(100,100));
        deleteButton = new CircularButton("Images/delete.png", 100);
        tempPanelTwo.add(deleteButton);
        deleteButtonPanel.add(tempPanelTwo, BorderLayout.CENTER);

        deleteLinearButton = new JButton("Delete Product");
        deleteLinearButton.setFont(buttonFont);
        deleteLinearButton.setForeground(Color.WHITE);
        deleteLinearButton.setBorderPainted(false);
        deleteLinearButton.setContentAreaFilled(false);
        deleteLinearButton.setFocusPainted(false);
        deleteButtonPanel.add(deleteLinearButton, BorderLayout.SOUTH);
        buttonPanelToAdd.add(deleteButtonPanel);

        buttonPanel.add(buttonPanelToAdd);
        add(buttonPanel);

        // adding the sales panel
        JPanel salesPanel = new JPanel();
        salesPanel.setLayout(new BorderLayout());
        salesPanel.setPreferredSize(sales.getSize());
        salesPanel.setBounds(sales);
        salesPanel.setBackground(beige);

        JPanel salesPanelLabel = new JPanel();
        salesPanelLabel.setBackground(beige);
        salesPanelLabel.setPreferredSize(new Dimension(175, 70));
        Font headerFont = new Font("Smiley Sans", Font.PLAIN, 24);
        JLabel headerLabel = new JLabel("Sales");
        headerLabel.setForeground(darkBlue);
        headerLabel.setFont(headerFont);

        Font textFont = new Font("Smiley Sans", Font.PLAIN, 12 );
        JLabel textLabel = new JLabel("Get your sales by...");
        textLabel.setForeground(darkBlue);
        textLabel.setFont(textFont);

        salesPanelLabel.add(headerLabel);
        salesPanelLabel.add(textLabel);

        JPanel salesPanelMain = new JPanel();
        salesPanelMain.setBackground(beige);

        JPanel salesPanelOne = new JPanel();
        salesPanelOne.setBackground(beige);
        salesPanelOne.setLayout(new BorderLayout());
        JPanel circularProductContainer = new JPanel();
        circularProductContainer.setBackground(beige);
        circularProductButton = new CircularButton("Images/byProduct.png", 100);
        circularProductButton.setBackground(beige);
        // button font
        Font salesButtonFont = new Font("Smiley Sans", Font.PLAIN, 12);
        productButton = new JButton("Product");
        productButton.setFont(buttonFont);
        productButton.setForeground(darkBlue);
        productButton.setBorderPainted(false);
        productButton.setOpaque(false);
        productButton.setFocusPainted(false);
        circularProductContainer.setPreferredSize(new Dimension(100,100));
        circularProductContainer.add(circularProductButton);
        salesPanelOne.add(circularProductContainer, BorderLayout.CENTER);
        salesPanelOne.add(productButton, BorderLayout.SOUTH);
        salesPanelMain.add(salesPanelOne);


        JPanel salesPanelTwo = new JPanel();
        salesPanelTwo.setBackground(beige);
        salesPanelTwo.setLayout(new BorderLayout());
        JPanel circularValueContainer = new JPanel();
        circularValueContainer.setBackground(beige);
        circularValueButton = new CircularButton("Images/value.png", 100);
        circularValueButton.setBackground(beige);
        valuesButton = new JButton("Value");
        valuesButton.setFont(buttonFont);
        valuesButton.setForeground(darkBlue);
        valuesButton.setBorderPainted(false);
        valuesButton.setContentAreaFilled(false);
        valuesButton.setFocusPainted(false);
        circularValueContainer.setPreferredSize(new Dimension(100,100));
        circularValueContainer.add(circularValueButton);
        salesPanelTwo.add(circularValueContainer, BorderLayout.CENTER);
        salesPanelTwo.add(valuesButton, BorderLayout.SOUTH);
        salesPanelMain.add(salesPanelTwo);

        JPanel salesPanelThree = new JPanel();
        salesPanelThree.setBackground(beige);
        salesPanelThree.setLayout(new BorderLayout());
        JPanel circularCustomerContainer = new JPanel();
        circularCustomerContainer.setBackground(beige);
        circularCustomerButton = new CircularButton("Images/Social.png", 100);
        circularCustomerButton.setBackground(beige);

        editLinearButton = new JButton("Edit Product");
        editLinearButton.setFont(buttonFont);
        editLinearButton.setForeground(Color.WHITE);
        editLinearButton.setBorderPainted(false);
        editLinearButton.setContentAreaFilled(false);
        editLinearButton.setFocusPainted(false);

        customerButton = new JButton("Customer");
        customerButton.setFont(buttonFont);
        customerButton.setForeground(darkBlue);
        customerButton.setBorderPainted(false);
        customerButton.setContentAreaFilled(false);
        customerButton.setFocusPainted(false);
        circularCustomerContainer.setPreferredSize(new Dimension(100,100));
        circularCustomerContainer.add(customerButton);
        salesPanelThree.add(circularCustomerButton, BorderLayout.CENTER);
        salesPanelThree.add(customerButton, BorderLayout.SOUTH);
        salesPanelMain.add(salesPanelThree);

        salesPanel.add(salesPanelLabel, BorderLayout.NORTH);
        salesPanel.add(salesPanelMain, BorderLayout.CENTER);
        // adding the final sales panel to main panel
        add(salesPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainframe = new JFrame();
            mainframe.setSize(1024, 768);
            mainframe.setLocationRelativeTo(null);
            mainframe.setResizable(false);
            mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            SellerStoreTestPanel sellerStoreTestPanel = new SellerStoreTestPanel();
            mainframe.setContentPane(sellerStoreTestPanel);
            mainframe.setVisible(true);
        });
    }
}
