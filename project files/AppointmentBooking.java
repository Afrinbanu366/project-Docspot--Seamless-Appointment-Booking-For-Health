import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// ====================== APPOINTMENT ======================
class Appointment {
    private Doctor doctor;
    private LocalDateTime dateTime;
    private String patientName;

    public Appointment(Doctor doctor, LocalDateTime dateTime, String patientName) {
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.patientName = patientName;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "Doctor: " + doctor.getName() +
                ", Date/Time: " + dateTime.format(formatter) +
                ", Patient: " + patientName;
    }
}

// ====================== DOCTOR ======================
class Doctor {
    private String name;
    private List<LocalDateTime> availableSlots;

    public Doctor(String name) {
        this.name = name;
        this.availableSlots = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addAvailableSlot(LocalDateTime slot) {
        availableSlots.add(slot);
    }

    public List<LocalDateTime> getAvailableSlots() {
        return availableSlots;
    }

    public boolean removeSlot(LocalDateTime slot) {
        return availableSlots.remove(slot);
    }
}

// ====================== BOOKING SERVICE ======================
class BookingService {

    private List<Doctor> doctors = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void showAvailableSlots(String doctorName) {
        doctors.stream()
                .filter(d -> d.getName().equalsIgnoreCase(doctorName))
                .findFirst()
                .ifPresentOrElse(doctor -> {
                    System.out.println("\nAvailable slots for " + doctor.getName());
                    doctor.getAvailableSlots().forEach(System.out::println);
                }, () -> System.out.println("Doctor not found."));
    }

    public void bookAppointment(String doctorName, String patientName, LocalDateTime time) {

        Optional<Doctor> doctorOpt = doctors.stream()
                .filter(d -> d.getName().equalsIgnoreCase(doctorName))
                .findFirst();

        if (doctorOpt.isEmpty()) {
            System.out.println("Doctor not found.");
            return;
        }

        Doctor doctor = doctorOpt.get();

        if (doctor.getAvailableSlots().contains(time)) {
            doctor.removeSlot(time);
            Appointment appointment = new Appointment(doctor, time, patientName);
            appointments.add(appointment);
            System.out.println("\n✅ Appointment Booked Successfully!");
            System.out.println(appointment);
        } else {
            System.out.println("\n❌ Time slot not available.");
        }
    }

    public void showAllAppointments() {
        System.out.println("\n==== All Bookings ====");
        appointments.forEach(System.out::println);
    }
}

// ====================== MAIN ======================
public class AppointmentBooking {

    public static void main(String[] args) {

        BookingService bookingService = new BookingService();

        // Create doctors
        Doctor doctor1 = new Doctor("Dr. Smith");
        Doctor doctor2 = new Doctor("Dr. Johnson");

        // Add slots
        doctor1.addAvailableSlot(LocalDateTime.of(2026, 2, 19, 10, 0));
        doctor1.addAvailableSlot(LocalDateTime.of(2026, 2, 19, 11, 0));

        doctor2.addAvailableSlot(LocalDateTime.of(2026, 2, 19, 10, 0));
        doctor2.addAvailableSlot(LocalDateTime.of(2026, 2, 19, 11, 0));

        bookingService.addDoctor(doctor1);
        bookingService.addDoctor(doctor2);

        // Show slots
        bookingService.showAvailableSlots("Dr. Smith");

        // Book appointment
        bookingService.bookAppointment(
                "Dr. Smith",
                "Alice",
                LocalDateTime.of(2026, 2, 19, 10, 0)
        );

        // Show all appointments
        bookingService.showAllAppointments();
    }
}