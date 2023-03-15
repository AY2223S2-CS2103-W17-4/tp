package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Person;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.service.Part;
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
    Predicate<Part> PREDICATE_SHOW_ALL_PARTS = unused -> true;
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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

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


    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void updateFilteredCustomerList(Predicate<Customer> predicate);

    // ==== For Customers ==

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

    /**
     * Adds appointment
     *
     * @param appointment Appointment to add
     */
    void addAppointment(Appointment appointment);

    /**
     * Adds part
     *
     * @param part Part to add
     */
    void addPart(Part part);

    /**
     * Checks if part already exists
     *
     * @param part Part to check against
     */
    boolean hasPart(Part part);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Customer> getFilteredCustomerList();

}
