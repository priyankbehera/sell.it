package Objects;

public class Product {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private double sales;

    public Product(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSales() {
        return getQuantity() * getPrice();
    }

    public void increaseQuantity(int amount) {
        setQuantity(getQuantity() + amount);
    }

    public void decreaseQuantity(int amount) {
        setQuantity(getQuantity() - amount);
    }

}