import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}

class ShoppingCart {
    private List<Product> products;
    private static final double TAX_RATE = 0.08;
    private static final double DISCOUNT_RATE = 0.1;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
        System.out.println(product.getName() + " added to the cart.");
    }

    public void removeProduct(String productName) {
        products.removeIf(product -> product.getName().equalsIgnoreCase(productName));
        System.out.println(productName + " removed from the cart.");
    }

    public double calculateTotalCost() {
        double total = 0;
        for (Product product : products) {
            total += product.getTotalPrice();
        }
        if (total > 100) {
            total -= total * DISCOUNT_RATE;
        }
        total += total * TAX_RATE;
        return total;
    }

    public void displayCartContents() {
        if (products.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\nCart Contents:");
        for (Product product : products) {
            System.out.println("Product: " + product.getName() + ", Price: $" + product.getPrice() + ", Quantity: " + product.getQuantity());
        }
        System.out.println("Total Cost (with tax and discounts): $" + calculateTotalCost());
    }
}

class Customer {
    private String name;
    private String email;
    private ShoppingCart cart;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.cart = new ShoppingCart();
    }

    public ShoppingCart getCart() {
        return cart;
    }
}

public class OnlineShoppingCartSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Customer customer = null;

        try {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            customer = new Customer(name, email);

            while (true) {
                System.out.println("\n1. Add Product\n2. Remove Product\n3. View Cart\n4. Checkout\n5. Exit");
                System.out.print("Choose an option: ");
                int option = Integer.parseInt(scanner.nextLine());

                if (option == 1) {
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double productPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter product quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    Product product = new Product(productName, productPrice, quantity);
                    customer.getCart().addProduct(product);

                } else if (option == 2) {
                    System.out.print("Enter product name to remove: ");
                    String productName = scanner.nextLine();
                    customer.getCart().removeProduct(productName);

                } else if (option == 3) {
                    customer.getCart().displayCartContents();

                } else if (option == 4) {
                    System.out.println("\nProceeding to checkout...");
                    customer.getCart().displayCartContents();
                    System.out.println("Thank you for shopping with us!");
                    break;

                } else if (option == 5) {
                    System.out.println("Exiting the system. Thank you!");
                    break;

                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter the correct data type.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
