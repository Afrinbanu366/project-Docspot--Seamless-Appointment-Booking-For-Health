import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Appointment {
    private String doctorName;
    private Date dateTime;
    private String patientName;

    public Appointment(String doctorName, Date dateTime, String patientName) {
        this.doctorName = doctorName;
        this.dateTime = dateTime;
        this.patientName = patientName;
    }

    @Override
    public String toString() {
        return "Doctor: " + doctorName + ", Date/Time: " + dateTime + ", Patient: " + patientName;
    }
}

class Doctor {
    private String name;
    private List<Date> availableSlots;

    public Doctor(String name, List<Date> availableSlots) {
        this.name = name;
        this.availableSlots = availableSlots;
    }

    public String getName() {
        return name;
    }

    public List<Date> getAvailableSlots() {
        return availableSlots;
    }

    public boolean bookSlot(Date appointmentTime) {
        if (availableSlots.contains(appointmentTime)) {
            availableSlots.remove(appointmentTime);
            return true;
        }
        return false;
    }
}

public class AppointmentBooking {
    public static void main(String[] args) {
        List<Date> slots1 = new ArrayList<>();
        slots1.add(new Date(2026, 2, 19, 10, 0));
        slots1.add(new Date(2026, 2, 19, 11, 0));
        Doctor doctor1 = new Doctor("Dr. Smith", slots1);

        List<Date> slots2 = new ArrayList<>();
        slots2.add(new Date(2026, 2, 19, 10, 0));
        slots2.add(new Date(2026, 2, 19, 11, 0));
        Doctor doctor2 = new Doctor("Dr. Johnson", slots2);

        // Booking an appointment
        String patientName = "Alice";
        Date appointmentTime = new Date(2026, 2, 19, 10, 0);
        
        if (doctor1.bookSlot(appointmentTime)) {
            Appointment appointment = new Appointment(doctor1.getName(), appointmentTime, patientName);
            System.out.println("Appointment booked: " + appointment);
        } else {
            System.out.println("Sorry, this time slot is not available.");
        }
    }
}



