import java.util.Scanner;

abstract class Vehicle {
    private String fuelType;
    private int maxSpeed;

    public Vehicle(String fuelType, int maxSpeed) {
        this.fuelType = fuelType;
        this.maxSpeed = maxSpeed;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void drive() {
        System.out.println("Driving at max speed: " + maxSpeed + " km/h");
    }

    public abstract void maintenance();
}

class Car extends Vehicle {
    public Car(String fuelType, int maxSpeed) {
        super(fuelType, maxSpeed);
    }

    @Override
    public void drive() {
        System.out.println("Car is driving smoothly at max speed: " + getMaxSpeed() + " km/h");
    }

    @Override
    public void maintenance() {
        System.out.println("Performing maintenance on the car.");
    }
}

class Truck extends Vehicle {
    public Truck(String fuelType, int maxSpeed) {
        super(fuelType, maxSpeed);
    }

    @Override
    public void drive() {
        System.out.println("Truck is driving steadily at max speed: " + getMaxSpeed() + " km/h");
    }

    @Override
    public void maintenance() {
        System.out.println("Performing maintenance on the truck.");
    }
}

class Bus extends Vehicle {
    public Bus(String fuelType, int maxSpeed) {
        super(fuelType, maxSpeed);
    }

    @Override
    public void drive() {
        System.out.println("Bus is driving with passengers at max speed: " + getMaxSpeed() + " km/h");
    }

    @Override
    public void maintenance() {
        System.out.println("Performing maintenance on the bus.");
    }
}

public class AutomatedVehicleManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\n1. Add Car\n2. Add Truck\n3. Add Bus\n4. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    System.out.print("Enter fuel type for the car: ");
                    String fuelType = scanner.nextLine();
                    System.out.print("Enter max speed for the car: ");
                    int maxSpeed = Integer.parseInt(scanner.nextLine());
                    Vehicle car = new Car(fuelType, maxSpeed);
                    car.drive();
                    car.maintenance();

                } else if (choice == 2) {
                    System.out.print("Enter fuel type for the truck: ");
                    String fuelType = scanner.nextLine();
                    System.out.print("Enter max speed for the truck: ");
                    int maxSpeed = Integer.parseInt(scanner.nextLine());
                    Vehicle truck = new Truck(fuelType, maxSpeed);
                    truck.drive();
                    truck.maintenance();

                } else if (choice == 3) {
                    System.out.print("Enter fuel type for the bus: ");
                    String fuelType = scanner.nextLine();
                    System.out.print("Enter max speed for the bus: ");
                    int maxSpeed = Integer.parseInt(scanner.nextLine());
                    Vehicle bus = new Bus(fuelType, maxSpeed);
                    bus.drive();
                    bus.maintenance();

                } else if (choice == 4) {
                    System.out.println("Exiting system.");
                    break;

                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers only where expected.");
        } finally {
            scanner.close();
        }
    }
}
