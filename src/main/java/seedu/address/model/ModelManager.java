package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Person;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.entity.shop.Shop;
import seedu.address.model.service.Part;
import seedu.address.model.service.Service;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.appointment.Appointment;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Technician> filteredTechnicians;
    private final FilteredList<Service> filteredServices;
    private final FilteredList<Vehicle> filteredVehicles;
    //    private final FilteredList<Part> filteredParts;
    //    private final FilteredList<Appointment> filteredAppointment;
    private final Shop shop;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.shop = new Shop();

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredCustomers = new FilteredList<>(this.shop.getCustomerList());
        filteredTechnicians = new FilteredList<>(this.shop.getTechnicianList());
        filteredServices = new FilteredList<>(this.shop.getServiceList());
        filteredVehicles = new FilteredList<>(this.shop.getVehicleList());
        //        filteredParts = new FilteredList<>(this.shop.getPartList());
        //        filteredAppointment = new FilteredList<>(this.shop.getAppointmentList());

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    // ==== For persons ===
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }
    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    // ==== For Customers ==
    /**
     * Adds customer to the shop
     *
     * @param customer Customer to be added
     */
    @Override
    public void addCustomer(Customer customer) {
        this.shop.addCustomer(customer);
        // addressBook.addCustomer(person);
        // updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS); #todo fix 44 allow shops
    }

    /**
     * Checks whether Shop already has customer
     *
     * @param customerId Customer ID to be checked
     */
    @Override
    public boolean hasCustomer(int customerId) {
        return this.shop.hasCustomer(customerId);
        //        @Override
        //        public boolean hasCustomer(Customer person) {
        //            requireNonNull(person);
        //            return addressBook.hasCustomer(person); #todo Deploy shop into modelmanager
        //        }
    }

    //    @Override
    //    public void deleteCustomer(Customer target) {
    //        addressBook.removeCustomer(target);
    //    }
    //
    //    @Override
    //    public void setCustomer(Customer target, Customer editedPerson) {
    //        requireAllNonNull(target, editedPerson);
    //        addressBook.setCustomer(target, editedPerson);
    //    }

    // ==== For Vehicles ==
    /**
     * Adds vehicle to the shop
     *
     * @param vehicle Vehicle to be added
     */
    @Override
    public void addVehicle(int customerId, Vehicle vehicle) {
        this.shop.addVehicle(customerId, vehicle);
    }

    /**
     * Checks if shop already has vehicle
     *
     * @param vehicleId Vehicle ID to check against
     */
    @Override
    public boolean hasVehicle(int vehicleId) {
        return this.shop.hasVehicle(vehicleId);
    }

    // -------------
    /**
     * Adds service
     *
     * @param service Service to add
     */
    @Override
    public void addService(int vehicleId, Service service) {
        this.shop.addService(vehicleId, service);
    }

    /**
     * @param serviceId ID of service
     * @return Whether service already in the system
     */
    @Override
    public boolean hasService(int serviceId) {
        return this.shop.hasService(serviceId);
    }

    // -------------

    /**
     * Adds appointment
     *
     * @param appointment Appointment to add
     */
    @Override
    public void addAppointment(Appointment appointment) {
        this.shop.addAppointment(appointment);
    }

    // -------------
    /**
     * Adds part
     *
     * @param part Part to add
     */
    @Override
    public void addPart(Part part) {
        this.shop.addPart(part);
    }

    /**
     * Checks if part already exists
     *
     * @param part Part to check against
     */
    @Override
    public boolean hasPart(Part part) {
        return this.shop.hasPart(part.getName());
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredCustomerList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
