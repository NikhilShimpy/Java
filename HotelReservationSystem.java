import java.util.Scanner;

abstract class Room {
    protected String roomNumber;
    protected String roomType;

    public Room(String roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    public abstract void bookRoom();

    public abstract void checkout();
}

interface RoomService {
    void orderRoomService();
}

interface LaundryService {
    void requestLaundryService();
}

class StandardRoom extends Room implements RoomService {
    public StandardRoom(String roomNumber) {
        super(roomNumber, "Standard Room");
    }

    @Override
    public void bookRoom() {
        System.out.println("Room " + roomNumber + " of type " + roomType + " has been booked.");
    }

    @Override
    public void checkout() {
        System.out.println("Checked out of room " + roomNumber + ".");
    }

    @Override
    public void orderRoomService() {
        System.out.println("Room service ordered for room " + roomNumber + ".");
    }
}

class DeluxeRoom extends Room implements RoomService {
    public DeluxeRoom(String roomNumber) {
        super(roomNumber, "Deluxe Room");
    }

    @Override
    public void bookRoom() {
        System.out.println("Room " + roomNumber + " of type " + roomType + " has been booked.");
    }

    @Override
    public void checkout() {
        System.out.println("Checked out of room " + roomNumber + ".");
    }

    @Override
    public void orderRoomService() {
        System.out.println("Room service ordered for room " + roomNumber + ".");
    }
}

class Suite extends Room implements RoomService, LaundryService {
    public Suite(String roomNumber) {
        super(roomNumber, "Suite");
    }

    @Override
    public void bookRoom() {
        System.out.println("Room " + roomNumber + " of type " + roomType + " has been booked with complimentary services.");
    }

    @Override
    public void checkout() {
        System.out.println("Checked out of room " + roomNumber + ".");
    }

    @Override
    public void orderRoomService() {
        System.out.println("Room service ordered for suite " + roomNumber + ".");
    }

    @Override
    public void requestLaundryService() {
        System.out.println("Laundry service requested for suite " + roomNumber + ".");
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Room room = null;

        while (true) {
            System.out.println("Select room type:");
            System.out.println("1. Standard Room");
            System.out.println("2. Deluxe Room");
            System.out.println("3. Suite");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();

            if (choice == 4) {
                break;
            }

            System.out.print("Enter room number: ");
            String roomNumber = scanner.next();

            switch (choice) {
                case 1:
                    room = new StandardRoom(roomNumber);
                    break;
                case 2:
                    room = new DeluxeRoom(roomNumber);
                    break;
                case 3:
                    room = new Suite(roomNumber);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            while (true) {
                System.out.println("\nChoose operation:");
                System.out.println("1. Book Room");
                System.out.println("2. Checkout");
                System.out.println("3. Order Room Service");
                if (room instanceof Suite) {
                    System.out.println("4. Request Laundry Service");
                }
                System.out.println("5. Back to Room Type Selection");

                int operation = scanner.nextInt();

                try {
                    switch (operation) {
                        case 1:
                            room.bookRoom();
                            break;
                        case 2:
                            room.checkout();
                            break;
                        case 3:
                            if (room instanceof RoomService) {
                                ((RoomService) room).orderRoomService();
                            }
                            break;
                        case 4:
                            if (room instanceof LaundryService) {
                                ((LaundryService) room).requestLaundryService();
                            }
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Invalid operation. Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    scanner.next();
                }

                if (operation == 5) {
                    break;
                }
            }
        }

        scanner.close();
    }
}
