import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Grade {
    private String subject;
    private double score;

    public Grade(String subject, double score) {
        this.subject = subject;
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public double getScore() {
        return score;
    }
}

class Student {
    private String name;
    private int id;
    private List<Grade> grades;

    private static final double GRADE_A_THRESHOLD = 90.0;
    private static final double GRADE_B_THRESHOLD = 80.0;
    private static final double GRADE_C_THRESHOLD = 70.0;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
        this.grades = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addGrade(String subject, double score) {
        grades.add(new Grade(subject, score));
        System.out.println("Grade added: " + subject + " - " + score);
    }

    public double calculateAverage() {
        double total = 0;
        for (Grade grade : grades) {
            total += grade.getScore();
        }
        return grades.isEmpty() ? 0 : total / grades.size();
    }

    public String determineOverallGrade() {
        double average = calculateAverage();
        if (average >= GRADE_A_THRESHOLD) {
            return "A";
        } else if (average >= GRADE_B_THRESHOLD) {
            return "B";
        } else if (average >= GRADE_C_THRESHOLD) {
            return "C";
        } else {
            return "D";
        }
    }

    public void displayGradesAndPerformance() {
        System.out.println("\nStudent: " + name + " (ID: " + id + ")");
        System.out.println("Grades:");
        for (Grade grade : grades) {
            System.out.println("Subject: " + grade.getSubject() + ", Score: " + grade.getScore());
        }
        System.out.println("Average Grade: " + calculateAverage());
        System.out.println("Overall Grade: " + determineOverallGrade());
    }
}

public class StudentGradingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();

        try {
            while (true) {
                System.out.println("\n1. Add Student\n2. Add Grade\n3. Display Student Performance\n4. Exit");
                System.out.print("Choose an option: ");
                int option = Integer.parseInt(scanner.nextLine());

                if (option == 1) {
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    students.add(new Student(name, id));
                    System.out.println("Student added: " + name);

                } else if (option == 2) {
                    System.out.print("Enter student ID to add grade: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Student student = findStudentById(students, id);

                    if (student != null) {
                        System.out.print("Enter subject name: ");
                        String subject = scanner.nextLine();
                        System.out.print("Enter grade score: ");
                        double score = Double.parseDouble(scanner.nextLine());
                        student.addGrade(subject, score);
                    } else {
                        System.out.println("Student ID not found.");
                    }

                } else if (option == 3) {
                    System.out.print("Enter student ID to display performance: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Student student = findStudentById(students, id);

                    if (student != null) {
                        student.displayGradesAndPerformance();
                    } else {
                        System.out.println("Student ID not found.");
                    }

                } else if (option == 4) {
                    System.out.println("Exiting the system. Thank you!");
                    break;

                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter the correct data type.");
        } finally {
            scanner.close();
        }
    }

    private static Student findStudentById(List<Student> students, int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
}
