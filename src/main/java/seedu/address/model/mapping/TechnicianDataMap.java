package seedu.address.model.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.service.Service;
import seedu.address.model.service.ServiceList;
import seedu.address.model.service.appointment.Appointment;
import seedu.address.model.service.appointment.UniqueAppointmentList;

/**
 * Represents a mapping between technicians with their associated services and appointments.
 */
public class TechnicianDataMap {
    private final Map<Technician, ServiceList> technicianToServiceMap = new HashMap<>();
    private final Map<Technician, UniqueAppointmentList> technicianToAppointmentsMap = new HashMap<>();

    /**
     * Constructs a TechnicianDataMap object.
     *
     * @param technicians The list of technicians.
     * @param services  The list of services.
     * @param appointments  The list of appointments.
     */
    public TechnicianDataMap(List<Technician> technicians, List<Service> services, List<Appointment> appointments) {
        reset(technicians, services, appointments);
    }

    /**
     * Resets the mapping of technicians to their associated appointments and services
     * based on the global lists provided
     *
     * @param technicians The list of technicians.
     * @param services  The list of services.
     * @param appointments  The list of appointments.
     */
    public void reset(List<Technician> technicians, List<Service> services, List<Appointment> appointments) {
        Map<Integer, Service> idToServiceMap = new HashMap<>();
        for (Service service : services) {
            idToServiceMap.put(service.getId(), service);
        }
        Map<Integer, Appointment> idToAppointmentMap = new HashMap<>();
        for (Appointment appointment : appointments) {
            idToAppointmentMap.put(appointment.getId(), appointment);
        }

        for (Technician technician : technicians) {
            ServiceList technicianServices = new ServiceList();
            UniqueAppointmentList technicianAppointments = new UniqueAppointmentList();
            for (int serviceId : technician.getServiceIds()) {
                Service service = idToServiceMap.get(serviceId);
                if (service != null) {
                    technicianServices.add(service);
                }
            }
            for (int appointmentId : technician.getAppointmentIds()) {
                Appointment appointment = idToAppointmentMap.get(appointmentId);
                if (appointment != null) {
                    technicianAppointments.add(appointment);
                }
            }
            this.technicianToServiceMap.put(technician, technicianServices);
            this.technicianToAppointmentsMap.put(technician, technicianAppointments);
        }
    }

    /**
     * Returns an unmodifiable observable list of services associated with the
     * specified technician.
     *
     * @param technician The technician whose services to retrieve.
     * @return An unmodifiable observable list of services associated with the
     *         specified technician.
     */
    public ObservableList<Service> getTechnicianServices(Technician technician) {
        return this.technicianToServiceMap.get(technician).asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable observable list of appointments associated with the
     * specified technician.
     *
     * @param technician The technician whose appointments to retrieve.
     * @return An unmodifiable observable list of appointments associated with the
     *         specified technician.
     */
    public ObservableList<Appointment> getTechnicianAppointments(Technician technician) {
        return this.technicianToAppointmentsMap.get(technician).asUnmodifiableObservableList();
    }
}
