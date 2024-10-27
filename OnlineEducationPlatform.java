
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Course {
    private String courseName;
    private String courseCode;

    public Course(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void startCourse() {
        System.out.println("Starting course: " + courseName + " (" + courseCode + ")");
    }

    public void display() {
        System.out.println("Course Name: " + courseName);
        System.out.println("Course Code: " + courseCode);
    }
}

class ScienceCourse extends Course {
    public ScienceCourse(String courseName, String courseCode) {
        super(courseName, courseCode);
    }

    @Override
    public void startCourse() {
        System.out.println("Starting Science course: " + getCourseName());
    }
}

class ArtsCourse extends Course {
    public ArtsCourse(String courseName, String courseCode) {
        super(courseName, courseCode);
    }

    @Override
    public void startCourse() {
        System.out.println("Starting Arts course: " + getCourseName());
    }
}

class TechnologyCourse extends Course {
    public TechnologyCourse(String courseName, String courseCode) {
        super(courseName, courseCode);
    }

    @Override
    public void startCourse() {
        System.out.println("Starting Technology course: " + getCourseName());
    }
}

class ProgrammingCourse extends TechnologyCourse {
    public ProgrammingCourse(String courseName, String courseCode) {
        super(courseName, courseCode);
    }

    @Override
    public void startCourse() {
        System.out.println("Starting Programming course: " + getCourseName());
    }
}
interface Interactive {
    void conductLiveSession();
    void assignQuiz();
}

class InteractiveScienceCourse extends ScienceCourse implements Interactive {
    public InteractiveScienceCourse(String courseName, String courseCode) {
        super(courseName, courseCode);
    }

    @Override
    public void conductLiveSession() {
        System.out.println("Conducting live session for Science course: " + getCourseName());
    }

    @Override
    public void assignQuiz() {
        System.out.println("Assigning quiz for Science course: " + getCourseName());
    }
}

class InteractiveArtsCourse extends ArtsCourse implements Interactive {
    public InteractiveArtsCourse(String courseName, String courseCode) {
        super(courseName, courseCode);
    }

    @Override
    public void conductLiveSession() {
        System.out.println("Conducting live session for Arts course: " + getCourseName());
    }

    @Override
    public void assignQuiz() {
        System.out.println("Assigning quiz for Arts course: " + getCourseName());
    }
}

class InteractiveProgrammingCourse extends ProgrammingCourse implements Interactive {
    public InteractiveProgrammingCourse(String courseName, String courseCode) {
        super(courseName, courseCode);
    }

    @Override
    public void conductLiveSession() {
        System.out.println("Conducting live session for Programming course: " + getCourseName());
    }

    @Override
    public void assignQuiz() {
        System.out.println("Assigning quiz for Programming course: " + getCourseName());
    }
}

class Lesson {
    private String title;
    private String content;

    public Lesson(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void displayLesson() {
        System.out.println("Lesson Title: " + title);
        System.out.println("Content: " + content);
    }
}

class CourseManager {
    private Map<String, List<Lesson>> courseLessonsMap;

    public CourseManager() {
        courseLessonsMap = new HashMap<>();
    }

    public void addLesson(String courseType, Lesson lesson) {
        courseLessonsMap.computeIfAbsent(courseType, k -> new ArrayList<>()).add(lesson);
    }

    public void displayLessons(String courseType) {
        List<Lesson> lessons = courseLessonsMap.get(courseType);
        if (lessons == null || lessons.isEmpty()) {
            System.out.println("No lessons found for " + courseType + " course.");
            return;
        }
        
        System.out.println("Lessons for " + courseType + " course:");
        for (Lesson lesson : lessons) {
            lesson.displayLesson();
            System.out.println("-------------------");
        }
    }
}

public class OnlineEducationPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseManager courseManager = new CourseManager();
        String selectedCourseType = "";

        while (true) {
            System.out.println("\nOnline Education Platform Menu:");
            System.out.println("1. Create Course");
            System.out.println("2. Add Lesson");
            System.out.println("3. Display Lessons");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 4) {
                System.out.println("Exiting the application. Goodbye!");
                break;  // Exit the loop and terminate the application
            }

            switch (choice) {
                case 1: // Create Course
                    try {
                        System.out.print("Enter course type (Science/Arts/Technology/Programming): ");
                        String courseType = scanner.nextLine();

                        System.out.print("Enter course name: ");
                        String courseName = scanner.nextLine();

                        System.out.print("Enter course code: ");
                        String courseCode = scanner.nextLine();

                        Course course;

                        switch (courseType.toLowerCase()) {
                            case "science":
                                course = new InteractiveScienceCourse(courseName, courseCode);
                                break;
                            case "arts":
                                course = new InteractiveArtsCourse(courseName, courseCode);
                                break;
                            case "technology":
                                course = new TechnologyCourse(courseName, courseCode);
                                break;
                            case "programming":
                                course = new InteractiveProgrammingCourse(courseName, courseCode);
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid course type entered.");
                        }

                        course.startCourse();
                        course.display();  // Display course details

                        // Store the selected course type for lesson management
                        selectedCourseType = courseType;

                        if (course instanceof Interactive) {
                            ((Interactive) course).conductLiveSession();
                            ((Interactive) course).assignQuiz();
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred while creating the course: " + e.getMessage());
                    }
                    break;

                case 2: // Add Lesson
                    if (selectedCourseType.isEmpty()) {
                        System.out.println("Please create a course first before adding lessons.");
                        break;
                    }

                    try {
                        System.out.print("Enter lesson title: ");
                        String lessonTitle = scanner.nextLine();
                        System.out.print("Enter lesson content: ");
                        String lessonContent = scanner.nextLine();
                        Lesson lesson = new Lesson(lessonTitle, lessonContent);
                        courseManager.addLesson(selectedCourseType, lesson);
                    } catch (Exception e) {
                        System.out.println("An error occurred while adding the lesson: " + e.getMessage());
                    }
                    break;

                case 3: // Display Lessons
                    if (selectedCourseType.isEmpty()) {
                        System.out.println("Please create a course first to display lessons.");
                        break;
                    }
                    courseManager.displayLessons(selectedCourseType);
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
        scanner.close();
    }
}
