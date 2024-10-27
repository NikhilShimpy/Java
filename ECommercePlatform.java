import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Base Product class
class Product {
    private static int idCounter = 1000;
    private int productId;
    private String name;
    private double price;
    private int stockQuantity;

    public Product(String name, double price, int stockQuantity) {
        this.productId = ++idCounter;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void reduceStock(int quantity) {
        if (quantity <= stockQuantity) {
            stockQuantity -= quantity;
        }
    }

    public void displayProduct() {
        System.out.println("Product ID: " + productId + ", Name: " + name + ", Price: " + price + ", Stock: " + stockQuantity);
    }
}

// Electronics subclass
class Electronics extends Product {
    private String brand;
    private int warrantyPeriod; // in months

    public Electronics(String name, double price, int stockQuantity, String brand, int warrantyPeriod) {
        super(name, price, stockQuantity);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }
}

// Clothing subclass
class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(String name, double price, int stockQuantity, String size, String color) {
        super(name, price, stockQuantity);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}

class Customer {
    private static int idCounter = 2000;
    private int customerId;
    private String name;
    private String email;
    private List<Order> orderHistory;

    public Customer(String name, String email) {
        this.customerId = ++idCounter;
        this.name = name;
        this.email = email;
        this.orderHistory = new ArrayList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
    }

    public void displayCustomerInfo() {
        System.out.println("Customer ID: " + customerId + ", Name: " + name + ", Email: " + email);
    }
}

class Order {
    private static int orderCounter = 3000;
    private int orderId;
    private Customer customer;
    private List<Product> products;
    private double totalAmount;

    public Order(Customer customer, Product product, int quantity) {
        this.orderId = ++orderCounter;
        this.customer = customer;
        this.products = new ArrayList<>();
        addProduct(product, quantity);
        customer.addOrder(this);
    }

    public Order(Customer customer, List<Product> products) {
        this.orderId = ++orderCounter;
        this.customer = customer;
        this.products = products;
        calculateTotal();
        customer.addOrder(this);
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    private void addProduct(Product product, int quantity) {
        if (product.getStockQuantity() >= quantity) {
            product.reduceStock(quantity);
            products.add(product);
            totalAmount += product.getPrice() * quantity;
        } else {
            System.out.println("Insufficient stock for product: " + product.getName());
        }
    }

    private void calculateTotal() {
        totalAmount = 0;
        for (Product product : products) {
            totalAmount += product.getPrice();
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderId + ", Customer: " + customer.getName() + ", Total Amount: " + totalAmount);
        System.out.println("Products:");
        for (Product product : products) {
            product.displayProduct();
        }
    }
}

public class ECommercePlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();

        // Sample data for testing
        products.add(new Electronics("Laptop", 800, 10, "Dell", 24));
        products.add(new Clothing("T-Shirt", 20, 50, "L", "Red"));

        try {
            while (true) {
                System.out.println("\n1. Add Customer\n2. Place Order\n3. View Customer Orders\n4. View Products\n5. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    System.out.print("Enter Customer Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Customer Email: ");
                    String email = scanner.nextLine();
                    Customer customer = new Customer(name, email);
                    customers.add(customer);
                    System.out.println("Customer Registered with ID: " + customer.getCustomerId());

                } else if (choice == 2) {
                    System.out.print("Enter Customer ID: ");
                    int customerId = Integer.parseInt(scanner.nextLine());
                    Customer customer = findCustomerById(customers, customerId);
                    if (customer != null) {
                        System.out.print("Enter Product ID: ");
                        int productId = Integer.parseInt(scanner.nextLine());
                        Product product = findProductById(products, productId);
                        if (product != null) {
                            System.out.print("Enter Quantity: ");
                            int quantity = Integer.parseInt(scanner.nextLine());
                            new Order(customer, product, quantity);
                            System.out.println("Order placed successfully!");
                        } else {
                            System.out.println("Product not found.");
                        }
                    } else {
                        System.out.println("Customer not found.");
                    }

                } else if (choice == 3) {
                    System.out.print("Enter Customer ID: ");
                    int customerId = Integer.parseInt(scanner.nextLine());
                    Customer customer = findCustomerById(customers, customerId);
                    if (customer != null) {
                        customer.displayCustomerInfo();
                        for (Order order : customer.getOrderHistory()) {
                            order.displayOrderDetails();
                        }
                    } else {
                        System.out.println("Customer not found.");
                    }

                } else if (choice == 4) {
                    System.out.println("Product List:");
                    for (Product product : products) {
                        product.displayProduct();
                    }

                } else if (choice == 5) {
                    System.out.println("Exiting the system.");
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter correct data.");
        } finally {
            scanner.close();
        }
    }

    private static Customer findCustomerById(List<Customer> customers, int id) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == id) {
                return customer;
            }
        }
        return null;
    }

    private static Product findProductById(List<Product> products, int id) {
        for (Product product : products) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }
}
