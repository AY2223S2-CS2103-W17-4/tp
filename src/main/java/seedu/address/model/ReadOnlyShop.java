package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.service.PartMap;
import seedu.address.model.service.Service;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.appointment.Appointment;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyShop {

    ObservableList<Appointment> getAppointmentList();

    PartMap getPartMap();

    /**
     * Returns an unmodifiable view of the customers list.
     * This list will not contain any duplicate customers.
     */
    ObservableList<Customer> getCustomerList();

    /**
     * Returns an unmodifiable view of the vehicle list.
     * This list will not contain any duplicate vehicles.
     */
    ObservableList<Vehicle> getVehicleList();

    /**
     * Returns an unmodifiable view of the technicians list.
     * This list will not contain any duplicate technicians.
     */
    ObservableList<Technician> getTechnicianList();

    /**
     * Returns an unmodifiable view of the services list.
     */
    ObservableList<Service> getServiceList();
}
