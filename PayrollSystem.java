import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Base Employee class
abstract class Employee {
    protected String name;
    protected String employeeID;
    protected double baseSalary;

    protected static final double TAX_RATE = 0.1; // 10% tax
    protected static final double PERFORMANCE_BONUS = 500; // Performance bonus for eligible employees

    public Employee(String name, String employeeID, double baseSalary) {
        this.name = name;
        this.employeeID = employeeID;
        this.baseSalary = baseSalary;
    }

    // Abstract method to calculate salary
    public abstract double calculateSalary();

    // Method to generate payslip
    public void generatePayslip() {
        System.out.println("Employee ID: " + employeeID);
        System.out.println("Name: " + name);
        System.out.println("Base Salary: $" + baseSalary);
        System.out.println("Total Salary: $" + calculateSalary());
        System.out.println("---------------------------");
    }
}

// Full-Time Employee subclass
class FullTimeEmployee extends Employee {
    private double overtimeHours;
    private double overtimeRate;

    public FullTimeEmployee(String name, String employeeID, double baseSalary, double overtimeHours, double overtimeRate) {
        super(name, employeeID, baseSalary);
        this.overtimeHours = overtimeHours;
        this.overtimeRate = overtimeRate;
    }

    @Override
    public double calculateSalary() {
        double overtimePay = overtimeHours * overtimeRate;
        double grossSalary = baseSalary + overtimePay + PERFORMANCE_BONUS;
        return grossSalary - (grossSalary * TAX_RATE);
    }
}

// Part-Time Employee subclass
class PartTimeEmployee extends Employee {
    private double hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String name, String employeeID, double hourlyRate, double hoursWorked) {
        super(name, employeeID, 0);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        double grossSalary = hoursWorked * hourlyRate;
        return grossSalary - (grossSalary * TAX_RATE);
    }
}

// Contract Employee subclass
class ContractEmployee extends Employee {
    private double contractBonus;

    public ContractEmployee(String name, String employeeID, double baseSalary, double contractBonus) {
        super(name, employeeID, baseSalary);
        this.contractBonus = contractBonus;
    }

    @Override
    public double calculateSalary() {
        double grossSalary = baseSalary + contractBonus;
        return grossSalary - (grossSalary * TAX_RATE);
    }
}

// Payroll System
public class PayrollSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Employee> employees = new ArrayList<>();
        
        try {
            System.out.print("Enter the number of employees: ");
            int numEmployees = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < numEmployees; i++) {
                System.out.println("Enter details for Employee " + (i + 1));

                System.out.print("Name: ");
                String name = scanner.nextLine();

                System.out.print("Employee ID: ");
                String employeeID = scanner.nextLine();

                System.out.print("Enter employee type (FullTime/PartTime/Contract): ");
                String type = scanner.nextLine().toLowerCase();

                switch (type) {
                    case "fulltime" -> {
                        System.out.print("Base Salary: ");
                        double baseSalary = Double.parseDouble(scanner.nextLine());

                        System.out.print("Overtime Hours: ");
                        double overtimeHours = Double.parseDouble(scanner.nextLine());

                        System.out.print("Overtime Rate: ");
                        double overtimeRate = Double.parseDouble(scanner.nextLine());

                        employees.add(new FullTimeEmployee(name, employeeID, baseSalary, overtimeHours, overtimeRate));
                    }
                    case "parttime" -> {
                        System.out.print("Hourly Rate: ");
                        double hourlyRate = Double.parseDouble(scanner.nextLine());

                        System.out.print("Hours Worked: ");
                        double hoursWorked = Double.parseDouble(scanner.nextLine());

                        employees.add(new PartTimeEmployee(name, employeeID, hourlyRate, hoursWorked));
                    }
                    case "contract" -> {
                        System.out.print("Base Salary: ");
                        double baseSalary = Double.parseDouble(scanner.nextLine());

                        System.out.print("Contract Bonus: ");
                        double contractBonus = Double.parseDouble(scanner.nextLine());

                        employees.add(new ContractEmployee(name, employeeID, baseSalary, contractBonus));
                    }
                    default -> System.out.println("Invalid employee type.");
                }
            }

            System.out.println("\nGenerating Payslips...");
            for (Employee emp : employees) {
                emp.generatePayslip();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numbers correctly.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
