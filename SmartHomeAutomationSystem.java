import java.util.Scanner;

abstract class SmartDevice {
    private String deviceName;
    private boolean status;

    public SmartDevice(String deviceName) {
        this.deviceName = deviceName;
        this.status = false; // Initially turned off
    }

    public String getDeviceName() {
        return deviceName;
    }

    public boolean isStatus() {
        return status;
    }

    public void turnOn() {
        status = true;
        System.out.println(deviceName + " is turned on.");
    }

    public void turnOff() {
        status = false;
        System.out.println(deviceName + " is turned off.");
    }

    public abstract void scheduleAutomation();
}

class Light extends SmartDevice {
    private int brightness;

    public Light(String deviceName) {
        super(deviceName);
        this.brightness = 100; // Default brightness
    }

    @Override
    public void turnOn() {
        super.turnOn();
        System.out.println("Adjusting brightness to " + brightness + "%.");
    }

    @Override
    public void scheduleAutomation() {
        System.out.println("Light automation scheduled.");
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
        System.out.println("Brightness set to " + brightness + "%.");
    }
}

class Thermostat extends SmartDevice {
    private int temperature;

    public Thermostat(String deviceName) {
        super(deviceName);
        this.temperature = 22; // Default temperature in Celsius
    }

    @Override
    public void turnOn() {
        super.turnOn();
        System.out.println("Setting temperature to " + temperature + "°C.");
    }

    @Override
    public void scheduleAutomation() {
        System.out.println("Thermostat automation scheduled.");
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        System.out.println("Temperature set to " + temperature + "°C.");
    }
}

class SecurityCamera extends SmartDevice {
    public SecurityCamera(String deviceName) {
        super(deviceName);
    }

    @Override
    public void turnOn() {
        super.turnOn();
        System.out.println("Security camera is now recording.");
    }

    @Override
    public void scheduleAutomation() {
        System.out.println("Security camera automation scheduled.");
    }
}

interface BatteryPowered {
    void checkBatteryStatus();
}

class BatteryOperatedLight extends Light implements BatteryPowered {
    public BatteryOperatedLight(String deviceName) {
        super(deviceName);
    }

    @Override
    public void checkBatteryStatus() {
        System.out.println("Battery status is good.");
    }
}

public class SmartHomeAutomationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SmartDevice[] devices = new SmartDevice[3];

        devices[0] = new Light("Living Room Light");
        devices[1] = new Thermostat("Home Thermostat");
        devices[2] = new SecurityCamera("Front Door Camera");

        while (true) {
            System.out.println("\nSelect a device to control (1: Light, 2: Thermostat, 3: Security Camera, 4: Exit): ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 4) {
                System.out.println("Exiting the Smart Home Automation System.");
                break;
            }

            SmartDevice selectedDevice = devices[choice - 1];

            System.out.println("Selected Device: " + selectedDevice.getDeviceName());
            System.out.println("1. Turn On");
            System.out.println("2. Turn Off");
            System.out.println("3. Schedule Automation");
            System.out.print("Select an action: ");
            int action = Integer.parseInt(scanner.nextLine());

            try {
                switch (action) {
                    case 1:
                        selectedDevice.turnOn();
                        if (selectedDevice instanceof Light) {
                            System.out.print("Enter brightness (0-100): ");
                            int brightness = Integer.parseInt(scanner.nextLine());
                            ((Light) selectedDevice).setBrightness(brightness);
                        }
                        if (selectedDevice instanceof Thermostat) {
                            System.out.print("Enter temperature: ");
                            int temperature = Integer.parseInt(scanner.nextLine());
                            ((Thermostat) selectedDevice).setTemperature(temperature);
                        }
                        break;
                    case 2:
                        selectedDevice.turnOff();
                        break;
                    case 3:
                        selectedDevice.scheduleAutomation();
                        break;
                    default:
                        System.out.println("Invalid action selected.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
