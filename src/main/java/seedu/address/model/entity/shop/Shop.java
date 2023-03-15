package seedu.address.model.entity.shop;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyShop;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.entity.person.UniqueCustomerList;
import seedu.address.model.entity.person.UniqueTechnicianList;
import seedu.address.model.entity.person.exceptions.PersonNotFoundException;
import seedu.address.model.service.Part;
import seedu.address.model.service.PartMap;
import seedu.address.model.service.Service;
import seedu.address.model.service.ServiceList;
import seedu.address.model.service.UniqueVehicleList;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.appointment.Appointment;
import seedu.address.model.service.exception.PartNotFoundException;
import seedu.address.model.service.exception.VehicleNotFoundException;

/**
 * A Shop is an entity that usually buy sells things.
 */
public class Shop implements ReadOnlyShop {
    private final UniqueCustomerList customers = new UniqueCustomerList();
    private final UniqueVehicleList vehicles = new UniqueVehicleList();
    private final UniqueTechnicianList technicians = new UniqueTechnicianList();
    private final ServiceList services = new ServiceList();

    //TODO: Implement immutable list for appointments
    private final List<Appointment> appointments;

    //TODO: convert back to final, after figuring out how to properly implement setPartMap immutably
    private PartMap partMap;


    /**
     * Constructor for class Shop.
     */
    public Shop() {
        this.appointments = new ArrayList<>();
        this.partMap = new PartMap();
    }

    /**
     * Creates a Shop using the data in the {@code toBeCopied}
     */
    public Shop(ReadOnlyShop toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // --------------------------------------------------
    //// Service-level operations
    /**
     * Adds service to the system
     *
     * @param service   Service to be added to the system
     */
    public void addService(Service service) {
        this.services.add(service);
    }

    /**
     * Adds service to a vehicle
     *
     * @param vehicleId Id of vehicle
     * @param service   Service to be added to vehicle
     * @throws VehicleNotFoundException if customer does not have specific vehicle
     */
    public void addService(int vehicleId, Service service) throws VehicleNotFoundException {
        for (var vehicle : this.getVehicleList()) {
            if (vehicle.getId() == vehicleId) {
                vehicle.addService(service);
                this.services.add(service);
            }
        }
        throw new VehicleNotFoundException();
    }

    /**
     * Checks if service already added
     *
     * @param serviceId Service ID to check
     */
    public boolean hasService(int serviceId) {
        return this.getServiceList()
                .stream()
                .anyMatch(s -> s.getId() == serviceId);
    }

    /**
     * Wrapper function to also check if service already added
     * but using Service param
     *
     * @param service Service to check
     */
    public boolean hasService(Service service) {
        return this.hasService(service.getId());
    }

    @Override
    public ObservableList<Service> getServiceList() {
        return this.services.asUnmodifiableObservableList();
    }

    /**
     * Replaces the contents of the service list with {@code services}.
     */
    public void setServices(List<Service> services) {
        this.services.setServices(services);
    }

    // --------------------------------------------------
    //// Appointment-level operations
    /**
     * Get appointment list
     *
     * @return List of appointments
     */
    public List<Appointment> getAppointments() {
        return this.appointments;
    }

    /**
     * Adds appointment to the appointment list
     *
     * @param appointment Appointment to be added
     */
    public void addAppointment(Appointment appointment) {
        this.getAppointments().add(appointment);
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {

        //        return this.appointments.asUnmodifiableObservableList();
        return null;
    }

    // --------------------------------------------------
    //// part-level operations
    @Override
    public PartMap getPartMap() {
        return this.partMap;
    }

    /**
     * Adds part to the part map
     *
     * @param part Part to be added
     */
    public void addPart(Part part) {
        this.getPartMap().addPart(part.getName(), part);
    }

    /**
     * Increases part stock
     *
     * @param partName Name of part
     * @param amt      Amount to increase by
     */
    public void addPartStock(String partName, int amt) throws PartNotFoundException {
        this.partMap.getPart(partName).increaseStock(amt);
    }

    /**
     * Checks if part already in the system
     *
     * @param partName Name of part
     */
    public boolean hasPart(String partName) {
        return this.partMap.contains(partName);
    }

    /**
     * Replaces the contents of the part map with {@code parts}.
     */
    public void setParts(PartMap parts) {
        this.partMap = parts;
    }

    // --------------------------------------------------
    //// customer-level operations

    /**
     * Adds customer to the shop
     *
     * @param customer Customer to be added
     */
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Adds vehicle to the shop and to customer's vehicle ids
     *
     * @param customerId Customer ID to check
     */
    public boolean hasCustomer(int customerId) {
        return this.getCustomerList().stream()
                .anyMatch(c -> c.getId() == customerId);
    }

    /**
     * Returns true if a customer with the same id or identity as {@code customer}
     * exists in the autom8 system.
     */
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return customers.contains(customer);
    }


    /**
     * Replaces the contents of the customer list with {@code customers}.
     * {@code customers} must not contain duplicate customers.
     */
    public void setCustomers(List<Customer> customers) {
        this.customers.setCustomers(customers);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setCustomer(Customer target, Customer editedPerson) {
        requireNonNull(editedPerson);
        customers.setCustomer(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCustomer(Customer key) {
        customers.remove(key);
    }

    @Override
    public ObservableList<Customer> getCustomerList() {
        return this.customers.asUnmodifiableObservableList();
    }

    // --------------------------------------------------
    //// technician-level operations
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasTechnician(Technician person) {
        requireNonNull(person);
        return technicians.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addTechnician(Technician p) {
        technicians.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setTechnician(Technician target, Technician editedPerson) {
        requireNonNull(editedPerson);
        technicians.setTechnician(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTechnician(Technician key) {
        technicians.remove(key);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setTechnicians(List<Technician> persons) {
        technicians.setTechnicians(persons);
    }

    @Override
    public ObservableList<Technician> getTechnicianList() {
        return this.technicians.asUnmodifiableObservableList();
    }

    // --------------------------------------------------
    //// Vehicle-level operations

    /**
     * Adds vehicle to the shop
     *
     * @param customerId Id of vehicle's owner
     * @param vehicle    Vehicle to be added
     * @throws PersonNotFoundException Customer not registered with the shop
     */
    public void addVehicle(int customerId, Vehicle vehicle) throws PersonNotFoundException {
        for (var customer : customers) {
            if (customer.getId() == customerId) {
                customer.addVehicle(vehicle);
                this.getVehicleList().add(vehicle);
                return;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Adds vehicle to the shop
     *
     * @param vehicle    Vehicle to be added
     */
    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    /**
     * Checks if vehicle is in the shop
     *
     * @param vehicleId Vehicle ID to check
     */
    public boolean hasVehicle(int vehicleId) {
        return this.getVehicleList().stream()
                .anyMatch(v -> v.getId() == vehicleId);
    }

    /**
     * Returns true if a vehicle with the same plate number as {@code vehicle}
     * exists in the autom8 system.
     */
    public boolean hasVehicle(Vehicle vehicle) {
        requireNonNull(vehicle);
        return vehicles.contains(vehicle);
    }

    /**
     * Replaces the contents of the vehicle list with {@code vehicles}.
     * {@code vehicles} must not contain duplicate vehicles.
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles.setVehicles(vehicles);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setVehicle(Vehicle target, Vehicle editedVehicle) {
        requireNonNull(editedVehicle);
        vehicles.setVehicle(target, editedVehicle);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeVehicle(Vehicle key) {
        vehicles.remove(key);
    }


    @Override
    public ObservableList<Vehicle> getVehicleList() {
        return this.vehicles.asUnmodifiableObservableList();
    }

    // --------------------------------------------------

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyShop newData) {
        requireNonNull(newData);

        setCustomers(newData.getCustomerList());
        setVehicles(newData.getVehicleList());
        setParts(newData.getPartMap());
        setServices(newData.getServiceList());
        setTechnicians(newData.getTechnicianList());
    }

    //// Delete operations

    // --------------------------------------------------
    //// util methods

    //    @Override
    //    public String toString() {
    //        return persons.asUnmodifiableObservableList().size() + " persons";
    //        // TODO: refine later
    //        // TODO: modify this
    //    }
    //
    //    @Override
    //    public boolean equals(Object other) {
    //        return other == this // short circuit if same object
    //                || (other instanceof AddressBook // instanceof handles nulls
    //                && persons.equals(((AddressBook) other).persons));
    //    }
    //
    //    @Override
    //    public int hashCode() {
    //        return persons.hashCode();
    //    }

    //// Others
}
