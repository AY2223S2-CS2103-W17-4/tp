package seedu.address.model.entity.shop;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyShop;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.entity.person.UniqueCustomerList;
import seedu.address.model.entity.person.UniqueTechnicianList;
import seedu.address.model.entity.person.exceptions.PersonNotFoundException;
import seedu.address.model.service.PartMap;
import seedu.address.model.service.Service;
import seedu.address.model.service.ServiceList;
import seedu.address.model.service.UniqueVehicleList;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.appointment.Appointment;
import seedu.address.model.service.appointment.UniqueAppointmentList;
import seedu.address.model.service.exception.PartLessThanZeroException;
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

    private final UniqueAppointmentList appointments = new UniqueAppointmentList();
    private final PartMap partMap = new PartMap();

    /**
     * Constructor for class Shop.
     */
    public Shop() {
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
     * @param service Service to be added to the system
     * @throws VehicleNotFoundException If vehicle not in the system
     */
    public void addService(Service service) throws VehicleNotFoundException {
        this.addService(service.getVehicleId(), service);
    }

    /**
     * Adds service to a vehicle
     *
     * @param vehicleId Id of vehicle
     * @param service   Service to be added to vehicle
     * @throws VehicleNotFoundException If vehicle not in the system
     */
    public void addService(int vehicleId, Service service) throws VehicleNotFoundException {
        for (var vehicle : this.getVehicleList()) {
            if (vehicle.getId() == vehicleId) {
                vehicle.addService(service);
                this.services.add(service);
                return;
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

    public void setService(Service services, Service edit) {

        this.services.setService(services, edit);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeService(Service key) {
        services.remove(key);
    }

    // --------------------------------------------------
    //// Appointment-level operations

    /**
     * Adds appointment to the appointment list
     *
     * @param appointment Appointment to be added
     */
    public void addAppointment(Appointment appointment) throws PersonNotFoundException {
        for (var customer : customers) {
            if (customer.getId() == appointment.getCustomerId()) {
                customer.addAppointment(appointment);
                this.appointments.add(appointment);
                return;
            }
        }
        throw new PersonNotFoundException();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return this.appointments.asUnmodifiableObservableList();
    }

    public void removeAppointment(Appointment key) {
        appointments.remove(key);
    }

    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireNonNull(editedAppointment);
        appointments.setAppointment(target, editedAppointment);
    }

    /**
     * Wrapper function to also check if appointment already added
     * but using appointment param
     *
     * @param appointment Appointment to check
     */
    public boolean hasAppointment(Appointment appointment) {
        return appointments.contains(appointment);
    }

    /**
     * Checks if appointment in the system
     *
     * @param appointmentId ID of appointment
     */
    public boolean hasAppointment(int appointmentId) {
        return this.getAppointmentList().stream().anyMatch(a -> a.getId() == appointmentId);
    }

    /**
     * Replaces the contents of the appointment list with {@code appointments}.
     * {@code appointments} must not contain appointment customers.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }

    // --------------------------------------------------
    //// part-level operations
    @Override
    public PartMap getPartMap() {
        return this.partMap;
    }

    /**
     * Adds part
     *
     * @param partName Name of the part to add
     * @param quantity Quantity of the part to add
     */
    public void addPart(String partName, int quantity) {
        this.getPartMap().addPart(partName, quantity);
    }

    /**
     * Adds part to service
     *
     * @param serviceId ID of service
     * @param partName  Name of part
     * @param quantity  Quantity of part
     * @throws NoSuchElementException    If service not in system
     * @throws PartNotFoundException     If part not registered
     * @throws PartLessThanZeroException If part insufficient
     */
    public void addPartToService(int serviceId, String partName, int quantity)
            throws NoSuchElementException, PartNotFoundException, PartLessThanZeroException {
        Optional<Service> serviceOption = this.getServiceList()
            .stream()
            .filter(s -> s.getId() == serviceId)
            .findFirst();
        Service service = serviceOption.orElseThrow();
        this.getPartMap().decreasePartQuantity(partName, quantity);
        service.addPart(partName, quantity);
    }

    /**
     * Assigns existing technician to existing service
     *
     * @param serviceId    ID of service
     * @param technicianId ID of technician
     * @throws NoSuchElementException If service or technician doesn't exist
     */
    public void addTechnicianToService(int serviceId, int technicianId) throws NoSuchElementException {
        if (!this.hasTechnician(technicianId)) {
            throw new NoSuchElementException();
        }
        Service service = this.getServiceList().stream()
            .filter(s -> s.getId() == serviceId)
            .findFirst()
            .orElseThrow();
        service.assignTechnician(technicianId);
    }

    /**
     * Assigns existing technician to existing appointment
     *
     * @param technicianId ID of technician
     * @param appointmentId ID of appointment
     * @throws NoSuchElementException if technician ID or appointment ID does not exist
     */
    public void addTechnicianToAppointment(int technicianId, int appointmentId) throws NoSuchElementException {
        if (!this.hasTechnician(technicianId)) {
            throw new NoSuchElementException();
        }
        Appointment appointment = this.getAppointmentList().stream()
            .filter(a -> a.getId() == appointmentId)
            .findFirst()
            .orElseThrow();
        appointment.addTechnician(technicianId);
    }

    /**
     * Increases part stock by a specified quantity
     *
     * @param partName Name of part
     * @param quantity Quanity to increase by
     */
    public void addPartStock(String partName, int quantity) throws PartNotFoundException {
        this.partMap.increasePartQuantity(partName, quantity);
    }

    /**
     * Checks if part already in the system
     *
     * @param partName Name of part to check
     */
    public boolean hasPart(String partName) {
        return this.partMap.contains(partName);
    }

    /**
     * Replaces the contents of the part map with {@code parts}.
     */
    public void setParts(PartMap parts) {
        this.partMap.replace(parts);
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
        key.getAppointmentIds().stream()
            .flatMap(i -> this.getAppointment(i).stream())
            .forEach(this::removeAppointment);
        key.getVehicleIds().stream()
            .flatMap(i -> this.getVehicle(i).stream())
            .forEach(this::removeVehicle);
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
     * Checks if technician already in the system
     *
     * @param technicianId ID of technician to check against
     */
    public boolean hasTechnician(int technicianId) {
        return this.getTechnicianList().stream()
            .anyMatch(p -> p.getId() == technicianId);
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
     * Replaces the contents of the technicians list with {@code technicians}.
     * {@code technicians} must not contain duplicate technicians.
     */
    public void setTechnicians(List<Technician> technicians) {
        this.technicians.setTechnicians(technicians);
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
                this.vehicles.add(vehicle);
                return;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Adds vehicle to the shop
     *
     * @param vehicle Vehicle to be added
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
        key.getServiceIds().stream()
            .flatMap(i -> getService(i).stream())
            .forEach(this::removeService);
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
        setAppointments(newData.getAppointmentList());
        setTechnicians(newData.getTechnicianList());
    }

    // Private getters to help in cascading removal

    private Optional<Customer> getCustomer(int customerId) {
        return this.getCustomerList().stream()
            .filter(c -> c.getId() == customerId)
            .findFirst();
    }

    private Optional<Vehicle> getVehicle(int vehicleId) {
        return this.getVehicleList().stream()
            .filter(v -> v.getId() == vehicleId)
            .findFirst();
    }

    private Optional<Service> getService(int serviceId) {
        return this.getServiceList().stream()
            .filter(v -> v.getId() == serviceId)
            .findFirst();
    }

    private Optional<Appointment> getAppointment(int appointmentId) {
        return this.getAppointmentList().stream()
            .filter(a -> a.getId() == appointmentId)
            .findFirst();
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
