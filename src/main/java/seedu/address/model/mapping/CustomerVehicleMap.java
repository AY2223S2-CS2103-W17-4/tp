package seedu.address.model.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.service.UniqueVehicleList;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.VehicleType;
import seedu.address.model.service.appointment.Appointment;
import seedu.address.model.service.appointment.UniqueAppointmentList;

/**
 * Represents a mapping between customers with their associated vehicles and appointments.
 */
public class CustomerVehicleMap {
    private final Map<Customer, UniqueVehicleList> customerToVehiclesMap = new HashMap<>();
    private final Map<Customer, UniqueAppointmentList> customerToAppointmentsMap = new HashMap<>();

    /**
     * Constructs a CustomerVehicleMap object.
     *
     * @param customers The list of customers.
     * @param vehicles  The list of vehicles.
     */
    public CustomerVehicleMap(List<Customer> customers, List<Vehicle> vehicles, List<Appointment> appointments) {
        reset(customers, vehicles, appointments);
    }

    /**
     * Resets the mapping of customers to their associated vehicles based on the given customer and vehicle lists.
     *
     * @param customers The list of customers.
     * @param vehicles  The list of vehicles.
     */
    public void reset(List<Customer> customers, List<Vehicle> vehicles, List<Appointment> appointments) {
        this.customerToAppointmentsMap.clear();
        this.customerToVehiclesMap.clear();

        Map<Integer, Vehicle> idToVehicleMap = new HashMap<>();
        for (Vehicle vehicle : vehicles) {
            idToVehicleMap.put(vehicle.getId(), vehicle);
        }
        Map<Integer, Appointment> idToAppointmentMap = new HashMap<>();
        for (Appointment appointment : appointments) {
            idToAppointmentMap.put(appointment.getId(), appointment);
        }

        for (Customer customer : customers) {
            UniqueVehicleList customerVehicles = new UniqueVehicleList();
            UniqueAppointmentList customerAppointments = new UniqueAppointmentList();
            for (int vehicleId : customer.getVehicleIds()) {
                Vehicle vehicle = idToVehicleMap.get(vehicleId);
                if (vehicle != null) {
                    customerVehicles.add(vehicle);
                }
            }
            for (int appointmentId : customer.getAppointmentIds()) {
                Appointment appointment = idToAppointmentMap.get(appointmentId);
                if (appointment != null) {
                    customerAppointments.add(appointment);
                }
            }
            this.customerToVehiclesMap.put(customer, customerVehicles);
            this.customerToAppointmentsMap.put(customer, customerAppointments);
        }
    }

    /**
     * Returns an unmodifiable observable list of vehicles associated with the
     * specified customer.
     *
     * @param customer The customer whose vehicles to retrieve.
     * @return An unmodifiable observable list of vehicles associated with the
     *         specified customer.
     */
    public ObservableList<Vehicle> getCustomerVehicles(Customer customer) {
        return this.customerToVehiclesMap.get(customer).asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable observable list of appointments associated with the
     * specified customer.
     *
     * @param customer The customer whose appointments to retrieve.
     * @return An unmodifiable observable list of appointments associated with the
     *         specified customer.
     */
    public ObservableList<Appointment> getCustomerAppointments(Customer customer) {
        return this.customerToAppointmentsMap.get(customer).asUnmodifiableObservableList();
    }

    /**
     * Returns the number of vehicles with the type car a customer has
     *
     * @param customer The customer whose vehicles to retrieve.
     * @return the number of vehicles with the type car a customer has.
     */
    public int getCustomerCarsCount(Customer customer) {
        int count = 0;
        List<Vehicle> customerVehicles = this.getCustomerVehicles(customer);
        for (Vehicle vehicle : customerVehicles) {
            if (vehicle.getType() == VehicleType.CAR) {
                count++;
            }
        }

        return count;
    }

    /**
     * Returns the number of vehicles with the type motorbike a customer has
     *
     * @param customer The customer whose vehicles to retrieve.
     * @return the number of vehicles with the type motorbike a customer has.
     */
    public int getCustomerMotorbikesCount(Customer customer) {
        int count = 0;
        List<Vehicle> customerVehicles = this.getCustomerVehicles(customer);
        for (Vehicle vehicle : customerVehicles) {
            if (vehicle.getType() == VehicleType.MOTORBIKE) {
                count++;
            }
        }

        return count;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Map.Entry<Customer, UniqueVehicleList> e : this.customerToVehiclesMap.entrySet()) {
            builder.append(e.getKey().getId() + ", ");
        }
        return builder.toString();
    }
}
