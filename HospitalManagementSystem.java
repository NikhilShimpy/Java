import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MedicalHistory {
    private String history;

    public MedicalHistory(String history) {
        this.history = history;
    }

    public String getHistory() {
        return history;
    }
}

class Patient {
    private static int idCounter = 100;
    private int patientId;
    private String name;
    private int age;
    private MedicalHistory medicalHistory;

    public Patient(String name, int age, String history) {
        this.patientId = ++idCounter;
        this.name = name;
        this.age = age;
        this.medicalHistory = new MedicalHistory(history);
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void displayPatientInfo() {
        System.out.println("Patient ID: " + patientId + ", Name: " + name + ", Age: " + age + ", Medical History: " + medicalHistory.getHistory());
    }
}

class Doctor {
    private static int idCounter = 500;
    private int doctorId;
    private String name;
    private String specialty;
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor(String name, String specialty) {
        this.doctorId = ++idCounter;
        this.name = name;
        this.specialty = specialty;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void displayDoctorInfo() {
        System.out.println("Doctor ID: " + doctorId + ", Name: " + name + ", Specialty: " + specialty);
        System.out.println("Appointments:");
        for (Appointment appointment : appointments) {
            appointment.displayAppointment();
        }
    }
}

class Appointment {
    private String date;
    private String time;
    private Patient patient;
    private Doctor doctor;

    public Appointment(String date, Patient patient, Doctor doctor) {
        this.date = date;
        this.patient = patient;
        this.doctor = doctor;
        doctor.addAppointment(this);
    }

    public Appointment(String date, String time, Patient patient, Doctor doctor) {
        this(date, patient, doctor);
        this.time = time;
    }

    public void displayAppointment() {
        System.out.println("Appointment Date: " + date + (time != null ? " at " + time : "") + ", Patient: " + patient.getName() + ", Doctor: " + doctor.getName());
    }
}

public class HospitalManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Patient> patients = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();

        try {
            while (true) {
                System.out.println("\n1. Register Patient\n2. Register Doctor\n3. Schedule Appointment\n4. Display Doctor Info\n5. Display Patient Info\n6. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    System.out.print("Enter Patient Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Medical History: ");
                    String history = scanner.nextLine();
                    Patient patient = new Patient(name, age, history);
                    patients.add(patient);
                    System.out.println("Patient Registered with ID: " + patient.getPatientId());

                } else if (choice == 2) {
                    System.out.print("Enter Doctor Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Specialty: ");
                    String specialty = scanner.nextLine();
                    Doctor doctor = new Doctor(name, specialty);
                    doctors.add(doctor);
                    System.out.println("Doctor Registered with ID: " + doctor.getDoctorId());

                } else if (choice == 3) {
                    System.out.print("Enter Patient ID: ");
                    int patientId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Doctor ID: ");
                    int doctorId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Appointment Date (dd-mm-yyyy): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Appointment Time (optional, hh:mm or press enter): ");
                    String time = scanner.nextLine();

                    Patient patient = findPatientById(patients, patientId);
                    Doctor doctor = findDoctorById(doctors, doctorId);

                    if (patient != null && doctor != null) {
                        Appointment appointment;
                        if (!time.isEmpty()) {
                            appointment = new Appointment(date, time, patient, doctor);
                        } else {
                            appointment = new Appointment(date, patient, doctor);
                        }
                        System.out.println("Appointment scheduled successfully.");
                    } else {
                        System.out.println("Invalid Patient or Doctor ID.");
                    }

                } else if (choice == 4) {
                    System.out.print("Enter Doctor ID: ");
                    int doctorId = Integer.parseInt(scanner.nextLine());
                    Doctor doctor = findDoctorById(doctors, doctorId);
                    if (doctor != null) {
                        doctor.displayDoctorInfo();
                    } else {
                        System.out.println("Doctor not found.");
                    }

                } else if (choice == 5) {
                    System.out.print("Enter Patient ID: ");
                    int patientId = Integer.parseInt(scanner.nextLine());
                    Patient patient = findPatientById(patients, patientId);
                    if (patient != null) {
                        patient.displayPatientInfo();
                    } else {
                        System.out.println("Patient not found.");
                    }

                } else if (choice == 6) {
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

    private static Patient findPatientById(List<Patient> patients, int id) {
        for (Patient patient : patients) {
            if (patient.getPatientId() == id) {
                return patient;
            }
        }
        return null;
    }

    private static Doctor findDoctorById(List<Doctor> doctors, int id) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId() == id) {
                return doctor;
            }
        }
        return null;
    }
}
