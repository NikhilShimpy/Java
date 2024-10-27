import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Syllabus {
    private String description;

    public Syllabus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

class Course {
    private static int idCounter = 100;
    private int courseId;
    private String courseName;
    private List<Student> enrolledStudents;
    private Syllabus syllabus;

    public Course(String courseName, Syllabus syllabus) {
        this.courseId = ++idCounter;
        this.courseName = courseName;
        this.syllabus = syllabus;
        this.enrolledStudents = new ArrayList<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
    }

    public void enrollStudent(int studentId, List<Student> allStudents) {
        for (Student student : allStudents) {
            if (student.getStudentId() == studentId) {
                enrolledStudents.add(student);
                return;
            }
        }
        System.out.println("Student ID not found.");
    }

    public void displayCourseDetails() {
        System.out.println("Course ID: " + courseId + ", Course Name: " + courseName + ", Syllabus: " + syllabus.getDescription());
        System.out.println("Enrolled Students:");
        for (Student student : enrolledStudents) {
            System.out.println(" - " + student.getName());
        }
    }
}

class UndergraduateCourse extends Course {
    public UndergraduateCourse(String courseName, Syllabus syllabus) {
        super(courseName, syllabus);
    }
}

class GraduateCourse extends Course {
    public GraduateCourse(String courseName, Syllabus syllabus) {
        super(courseName, syllabus);
    }
}

class Student {
    private static int idCounter = 2000;
    private int studentId;
    private String name;
    private List<Course> courses;

    public Student(String name) {
        this.studentId = ++idCounter;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void enrollInCourse(Course course) {
        courses.add(course);
        course.enrollStudent(this);
    }

    public void displayStudentDetails() {
        System.out.println("Student ID: " + studentId + ", Name: " + name);
        System.out.println("Enrolled Courses:");
        for (Course course : courses) {
            System.out.println(" - " + course.getCourseName());
        }
    }
}

class Faculty {
    private static int idCounter = 3000;
    private int facultyId;
    private String name;

    public Faculty(String name) {
        this.facultyId = ++idCounter;
        this.name = name;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getName() {
        return name;
    }

    public void assignCourseMaterial(Course course, String material) {
        System.out.println("Faculty " + name + " assigned material to course " + course.getCourseName() + ": " + material);
    }
}

public class UniversityEnrollmentSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
        List<Course> courses = new ArrayList<>();

        try {
            while (true) {
                System.out.println("\n1. Add Student\n2. Add Course\n3. Enroll Student in Course\n4. Display Student Info\n5. Display Course Info\n6. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    Student student = new Student(name);
                    students.add(student);
                    System.out.println("Student added with ID: " + student.getStudentId());

                } else if (choice == 2) {
                    System.out.print("Enter Course Name: ");
                    String courseName = scanner.nextLine();
                    System.out.print("Enter Syllabus Description: ");
                    String syllabusDescription = scanner.nextLine();
                    Syllabus syllabus = new Syllabus(syllabusDescription);

                    System.out.print("Is this course for (1) Undergraduate or (2) Graduate? ");
                    int courseType = Integer.parseInt(scanner.nextLine());

                    Course course;
                    if (courseType == 1) {
                        course = new UndergraduateCourse(courseName, syllabus);
                    } else {
                        course = new GraduateCourse(courseName, syllabus);
                    }
                    courses.add(course);
                    System.out.println("Course added with ID: " + course.getCourseId());

                } else if (choice == 3) {
                    System.out.print("Enter Student ID: ");
                    int studentId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Course ID: ");
                    int courseId = Integer.parseInt(scanner.nextLine());

                    Student student = findStudentById(students, studentId);
                    Course course = findCourseById(courses, courseId);

                    if (student != null && course != null) {
                        student.enrollInCourse(course);
                        System.out.println("Student enrolled in course.");
                    } else {
                        System.out.println("Invalid Student ID or Course ID.");
                    }

                } else if (choice == 4) {
                    System.out.print("Enter Student ID: ");
                    int studentId = Integer.parseInt(scanner.nextLine());
                    Student student = findStudentById(students, studentId);
                    if (student != null) {
                        student.displayStudentDetails();
                    } else {
                        System.out.println("Student not found.");
                    }

                } else if (choice == 5) {
                    System.out.print("Enter Course ID: ");
                    int courseId = Integer.parseInt(scanner.nextLine());
                    Course course = findCourseById(courses, courseId);
                    if (course != null) {
                        course.displayCourseDetails();
                    } else {
                        System.out.println("Course not found.");
                    }

                } else if (choice == 6) {
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

    private static Student findStudentById(List<Student> students, int id) {
        for (Student student : students) {
            if (student.getStudentId() == id) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourseById(List<Course> courses, int id) {
        for (Course course : courses) {
            if (course.getCourseId() == id) {
                return course;
            }
        }
        return null;
    }
}
