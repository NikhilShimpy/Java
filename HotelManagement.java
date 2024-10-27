
import java.util.ArrayList;
import java.util.Scanner;

abstract class Person {
    protected String name;
    protected String ID;
    protected String contactInfo;

    public Person(String name, String ID, String contactInfo) {
        this.name = name;
        this.ID = ID;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public abstract void displayDetails();
}

class Customer extends Person {
    public Customer(String name, String ID, String contactInfo) {
        super(name, ID, contactInfo);
    }

    @Override
    public void displayDetails() {
        System.out.println("Customer Name: " + name);
        System.out.println("Customer ID: " + ID);
        System.out.println("Contact Info: " + contactInfo);
    }
}

class Room {
    private int roomNumber;
    private String roomType;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookRoom() {
        isAvailable = false;
    }

    public void releaseRoom() {
        isAvailable = true;
    }

    public void displayRoomDetails() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: $" + price);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
    }
}

class Booking {
    protected Customer customer;
    protected Room room;
    protected int numberOfDays;
    protected double additionalCharges;

    public Booking(Customer customer, Room room, int numberOfDays) {
        this.customer = customer;
        this.room = room;
        this.numberOfDays = numberOfDays;
        this.additionalCharges = 0;
    }

    public void addAdditionalCharges(double charges) {
        additionalCharges += charges;
    }

    public void generateBill() {
        double roomCharges = room.getPrice() * numberOfDays;
        double totalAmount = roomCharges + additionalCharges;
        System.out.println("Bill for " + customer.getName());
        System.out.println("Room Charges: Rs. " + roomCharges);
        System.out.println("Additional Charges: Rs. " + additionalCharges);
        System.out.println("Total Amount: Rs. " + totalAmount);
    }

    public void checkOut() {
        room.releaseRoom();
        generateBill();
    }
}

public class HotelManagement {
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        rooms.add(new Room(101, "Deluxe", 200.0));
        rooms.add(new Room(102, "Suite", 300.0));
        rooms.add(new Room(103, "Single", 100.0));

        boolean exit = false;

        while (!exit) {
            System.out.println("\nHotel Management System Menu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Book Room");
            System.out.println("3. Check Out");
            System.out.println("4. View Room Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addCustomer(scanner);
                    break;
                case 2:
                    bookRoom(scanner);
                    break;
                case 3:
                    checkOut(scanner);
                    break;
                case 4:
                    viewRoomDetails(scanner);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter Customer Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter Customer ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter Contact Info: ");
        String contactInfo = scanner.nextLine();

        Customer newCustomer = new Customer(name, ID, contactInfo);
        customers.add(newCustomer);
        System.out.println("Customer added successfully.");
    }

    private static void bookRoom(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        scanner.nextLine();
        String customerID = scanner.nextLine();
        Customer customer = findCustomerByID(customerID);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.print("Enter Room Number to book: ");
        int roomNumber = scanner.nextInt();
        Room room = findRoomByNumber(roomNumber);

        if (room == null || !room.isAvailable()) {
            System.out.println("Room not available.");
            return;
        }

        System.out.print("Enter number of days: ");
        int numberOfDays = scanner.nextInt();

        room.bookRoom();
        Booking booking = new Booking(customer, room, numberOfDays);
        bookings.add(booking);
        System.out.println("Room booked successfully.");
    }

    private static void checkOut(Scanner scanner) {
        System.out.print("Enter Room Number for check-out: ");
        int roomNumber = scanner.nextInt();
        Room room = findRoomByNumber(roomNumber);

        if (room == null || room.isAvailable()) {
            System.out.println("Room is already available.");
            return;
        }

        Booking booking = findBookingByRoomNumber(roomNumber);
        if (booking != null) {
            booking.checkOut();
            bookings.remove(booking);
            System.out.println("Check-out completed.");
        }
    }

    private static void viewRoomDetails(Scanner scanner) {
        System.out.print("Enter Room Number to view details: ");
        int roomNumber = scanner.nextInt();
        Room room = findRoomByNumber(roomNumber);

        if (room != null) {
            room.displayRoomDetails();
        }
    }

    private static Customer findCustomerByID(String ID) {
        for (Customer customer : customers) {
            if (customer.getID().equals(ID)) {
                return customer;
            }
        }
        return null;
    }

    private static Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private static Booking findBookingByRoomNumber(int roomNumber) {
        for (Booking booking : bookings) {
            if (booking.room.getRoomNumber() == roomNumber) {
                return booking;
            }
        }
        return null;
    }
}
