package seedu.address.model.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.entity.person.UniqueTechnicianList;
import seedu.address.model.service.appointment.Appointment;

/**
 * Represents a mapping between appointments and their assigned staff
 */
public class AppointmentDataMap {
    private final Map<Appointment, UniqueTechnicianList> appointmentToTechniciansMap = new HashMap<>();
    private final Map<Appointment, Customer> appointmentToCustomerMap = new HashMap<>();

    /**
     * Constructs a ServiceDataMap object.
     *
     * @param appointments  The list of appointments.
     * @param technicians  The list of technicians.
     * @param customers  The list of customers.
     */
    public AppointmentDataMap(List<Appointment> appointments, List<Technician> technicians, List<Customer> customers) {
        reset(appointments, technicians, customers);
    }

    /**
     * Resets the mapping of appointments to their associated staff and customer
     *
     * @param appointments  The list of appointments.
     * @param technicians  The list of technicians.
     * @param customers  The list of customers.
     */
    public void reset(List<Appointment> appointments, List<Technician> technicians, List<Customer> customers) {
        Map<Integer, Technician> idToStaffMap = new HashMap<>();
        for (Technician technician : technicians) {
            idToStaffMap.put(technician.getId(), technician);
        }

        Map<Integer, Customer> idToCustomerMap = new HashMap<>();
        for (Customer customer : customers) {
            idToCustomerMap.put(customer.getId(), customer);
        }

        for (Appointment appointment : appointments) {
            UniqueTechnicianList appointmentTechnicianList = new UniqueTechnicianList();
            Customer appointmentCustomer = idToCustomerMap.get(appointment.getCustomerId());

            if (appointmentCustomer != null) {
                this.appointmentToCustomerMap.put(appointment, appointmentCustomer);
            }

            for (int technicianId : appointment.getStaffIds()) {
                Technician technician = idToStaffMap.get(technicianId);
                if (technician != null) {
                    appointmentTechnicianList.add(technician);
                }
            }
            this.appointmentToTechniciansMap.put(appointment, appointmentTechnicianList);
        }
    }

    /**
     * Returns an unmodifiable observable list of technicians associated with the
     * specified appointment.
     *
     * @param appointment The appointment whose technicians to retrieve.
     * @return An unmodifiable observable list of technicians associated with the
     *         specified appointment.
     */
    public ObservableList<Technician> getAppointmentTechnicians(Appointment appointment) {
        return this.appointmentToTechniciansMap.get(appointment).asUnmodifiableObservableList();
    }

    /**
     * Returns the Customer associated with the specified appointment.
     *
     * @param appointment The appointment whose customer to retrieve.
     * @return The customer with the appointment
     */
    public Customer getAppointmentCustomer(Appointment appointment) {
        return this.appointmentToCustomerMap.get(appointment);
    }
}
