package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Person;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.mapping.AppointmentDataMap;
import seedu.address.model.mapping.CustomerVehicleMap;
import seedu.address.model.mapping.ServiceDataMap;
import seedu.address.model.mapping.TechnicianDataMap;
import seedu.address.model.mapping.VehicleDataMap;
import seedu.address.model.service.PartMap;
import seedu.address.model.service.Service;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.appointment.Appointment;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<Customer> PREDICATE_SHOW_ALL_CUSTOMERS = unused -> true;
    Predicate<Technician> PREDICATE_SHOW_ALL_TECHNICIANS = unused -> true;
    Predicate<PartMap> PREDICATE_SHOW_ALL_PARTS = unused -> true;
    Predicate<Service> PREDICATE_SHOW_ALL_SERVICES = unused -> true;
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;
    Predicate<Vehicle> PREDICATE_SHOW_ALL_VEHICLES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' shop file path.
     */
    Path getShopFilePath();

    /**
     * Sets the user prefs' shop file path.
     */
    void setShopFilePath(Path shopFilePath);

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setShop(ReadOnlyShop shop);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the Shop
     */
    ReadOnlyShop getShop();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    ObservableList<Appointment> getFilteredAppointmentList();

    PartMap getPartMap();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void updateFilteredAppointmentList(Predicate<? super Appointment> predicate);

    // ==== For Customers ==

    /**
     * Returns an unmodifiable view of the filtered customer list
     */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Updates the filter of the filtered customer list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCustomerList(Predicate<? super Customer> predicate);

    /**
     * Adds customer to the shop
     *
     * @param customer Customer to be added
     */
    void addCustomer(Customer customer);

    /**
     * Checks whether Shop already has customer
     *
     * @param customerId Customer ID to be checked
     */
    boolean hasCustomer(int customerId);

    void deleteCustomer(Customer target);

    void setCustomer(Customer target, Customer editedPerson);

    // ==== For Vehicles ==

    /**
     * Returns an unmodifiable view of the filtered vehicle list
     */
    ObservableList<Vehicle> getFilteredVehicleList();

    /**
     * Adds vehicle to the shop
     *
     * @param vehicle Vehicle to be added
     */
    void addVehicle(int customerId, Vehicle vehicle);

    /**
     * Checks if shop already has vehicle
     *
     * @param vehicleId Vehicle ID to check against
     */
    boolean hasVehicle(int vehicleId);

    void deleteVehicle(Vehicle target);

    void setVehicle(Vehicle target, Vehicle editedVehicle);

    // ==== For Services ==

    /**
     * Returns an unmodifiable view of the filtered service list
     */
    ObservableList<Service> getFilteredServiceList();

    /**
     * Adds service
     *
     * @param service Service to add
     */
    void addService(int vehicleId, Service service);

    /**
     * @param serviceId ID of service
     * @return Whether service already in the system
     */
    boolean hasService(int serviceId);

    void deleteAppointment(Appointment target);

    void deleteService(Service service);

    /**
     * Adds appointment
     *
     * @param appointment Appointment to add
     */
    void addAppointment(Appointment appointment);

    /**
     * Checks if appointment in the system
     *
     * @param appointmentId ID of appointment
     */
    boolean hasAppointment(int appointmentId);

    /**
     * Adds part
     *
     * @param partName Name of the part to add
     * @param quantity Quantity of the part to add
     */
    void addPart(String partName, int quantity);

    /**
     * Adds part to service
     *
     * @param serviceId ID of service
     * @param partName  Name of part
     * @param quantity  Quantity of part
     * @throws NoSuchElementException If service not in system
     */
    void addPartToService(int serviceId, String partName, int quantity) throws NoSuchElementException;

    /**
     * Assigns existing technician to existing service
     *
     * @param serviceId ID of service
     * @param techId    ID of technician
     * @throws NoSuchElementException If technician or service not in system
     */
    void addTechnicianToService(int serviceId, int techId) throws NoSuchElementException;

    /**
     * Assigns existing technician to existing appointment
     *
     * @param techId ID of technician
     * @param appointmentId ID of appointment
     * @throws NoSuchElementException if technician ID or appointment ID does not exist
     */
    void addTechnicianToAppointment(int techId, int appointmentId) throws NoSuchElementException;

    /**
     * Checks if part already exists
     *
     * @param partName Name of the part to check against
     */
    boolean hasPart(String partName);

    ObservableList<Technician> getFilteredTechnicianList();

    /**
     * Adds Technician
     *
     * @param technician Technician to be added
     */
    void addTechnician(Technician technician);

    /**
     * Checks if technician already in the model
     *
     * @param technicianId ID of technician to check against
     */
    boolean hasTechnician(int technicianId);

    void updateFilteredTechnicianList(Predicate<? super Technician> predicate);

    void updateFilteredVehicleList(Predicate<? super Vehicle> predicate);

    void updateFilteredServiceList(Predicate<? super Service> predicate);

    void setAppointment(Appointment target, Appointment editedAppointment);

    void updatePartsMap();

    void deleteTechnician(Technician target);

    void setTechnician(Technician target, Technician editedPerson);

    CustomerVehicleMap getCustomerVehicleMap();

    VehicleDataMap getVehicleDataMap();

    ServiceDataMap getServiceDataMap();

    AppointmentDataMap getAppointmentDataMap();

    TechnicianDataMap getTechnicianDataMap();

    /**
     * Sets currently selected customer
     */
    void selectCustomer(Customer customer);

    /**
     * Returns currently selected customer
     */
    Customer getSelectedCustomer();

    /**
     * Sets currently selected vehicle
     */
    void selectVehicle(Vehicle vehicle);

    /**
     * Returns currently selected vehicle
     */
    Vehicle getSelectedVehicle();

    /**
     * Sets currently selected service
     */
    void selectService(Service service);

    /**
     * Returns currently selected service
     */
    Appointment getSelectedAppointment();

    /**
     * Sets currently selected appointment
     */
    void selectAppointment(Appointment appointment);

    /**
     * Returns currently selected service
     */
    Service getSelectedService();

    /**
     * Returns currently selected technician
     */
    Technician getSelectedTechnician();

    /**
     * Sets currently selected technician
     */
    void selectTechnician(Technician technician);

    void setService(Service target, Service editedService);

    // Sort helper functions

    /**
     * Updates the comparator used to sort customers
     *
     * @param cmp Customer comparator
     */
    void updateCustomerComparator(Comparator<? super Customer> cmp);

    /**
     * Updates the comparator used to sort vehicles
     *
     * @param cmp Vehicle comparator
     */
    void updateVehicleComparator(Comparator<? super Vehicle> cmp);

    /**
     * Updates the comparator used to sort services
     *
     * @param cmp Service comparator
     */
    void updateServiceComparator(Comparator<? super Service> cmp);

    /**
     * Updates the comparator used to sort appointments
     *
     * @param cmp Appointment comparator
     */
    void updateAppointmentComparator(Comparator<? super Appointment> cmp);

    /**
     * Updates the comparator used to sort technicians
     *
     * @param cmp Technician comparator
     */
    void updateTechnicianComparator(Comparator<? super Technician> cmp);

    /**
     * Update mappings
     */
    void resetMaps();
}
