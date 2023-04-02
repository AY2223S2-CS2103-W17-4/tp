package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.SharedComparatorsUtil;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Person;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.entity.shop.Shop;
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
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Customer> filteredCustomers;
    private final SortedList<Customer> sortedFilteredCustomers;
    private final FilteredList<Technician> filteredTechnicians;
    private final SortedList<Technician> sortedFilteredTechnicians;
    private final FilteredList<Service> filteredServices;
    private final SortedList<Service> sortedFilteredServices;
    private final FilteredList<Vehicle> filteredVehicles;
    private final SortedList<Vehicle> sortedFilteredVehicles;
    private final FilteredList<Appointment> filteredAppointments;
    private final SortedList<Appointment> sortedFilteredAppointments;

    private final PartMap partMap;
    private final Shop shop;

    private Customer selectedCustomer;
    private Vehicle selectedVehicle;
    private Service selectedService;
    private Appointment selectedAppointment;
    private Technician selectedTechnician;

    // Mapped
    private final CustomerVehicleMap customerVehicleMap;
    private final VehicleDataMap vehicleDataMap;
    private final ServiceDataMap serviceDataMap;
    private final AppointmentDataMap appointmentDataMap;
    private final TechnicianDataMap technicianDataMap;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyShop shop) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.shop = new Shop(shop);


        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        filteredCustomers = new FilteredList<>(this.shop.getCustomerList());
        this.sortedFilteredCustomers = new SortedList<>(this.filteredCustomers);

        filteredTechnicians = new FilteredList<>(this.shop.getTechnicianList());
        this.sortedFilteredTechnicians = new SortedList<>(this.filteredTechnicians);

        filteredServices = new FilteredList<>(this.shop.getServiceList());
        this.sortedFilteredServices = new SortedList<>(this.filteredServices);

        filteredVehicles = new FilteredList<>(this.shop.getVehicleList());
        this.sortedFilteredVehicles = new SortedList<>(this.filteredVehicles);

        filteredAppointments = new FilteredList<>(this.shop.getAppointmentList());
        this.sortedFilteredAppointments = new SortedList<>(this.filteredAppointments);
        // default sort for appointments
        this.sortedFilteredAppointments.setComparator(SharedComparatorsUtil.getDefaultAppointmentSort());

        partMap = this.shop.getPartMap();
        //        filteredParts = new FilteredList<>(this.shop.getPartList()); // filteredParts

        customerVehicleMap = new CustomerVehicleMap(this.shop.getCustomerList(), this.shop.getVehicleList(),
                this.shop.getAppointmentList());
        vehicleDataMap = new VehicleDataMap(this.shop.getVehicleList(), this.shop.getCustomerList(),
                this.shop.getServiceList());
        serviceDataMap = new ServiceDataMap(this.shop.getServiceList(), this.shop.getTechnicianList(),
                this.shop.getVehicleList());
        appointmentDataMap = new AppointmentDataMap(this.shop.getAppointmentList(), this.shop.getTechnicianList(),
                this.shop.getCustomerList());
        technicianDataMap = new TechnicianDataMap(this.shop.getTechnicianList(), this.shop.getServiceList(),
                this.shop.getAppointmentList());

        if (filteredCustomers.size() > 0) {
            selectedCustomer = filteredCustomers.get(0);
        }
        if (filteredVehicles.size() > 0) {
            selectedVehicle = filteredVehicles.get(0);
        }
        if (filteredServices.size() > 0) {
            selectedService = filteredServices.get(0);
        }
        if (filteredAppointments.size() > 0) {
            selectedAppointment = sortedFilteredAppointments.get(0);
        }
        if (filteredTechnicians.size() > 0) {
            selectedTechnician = filteredTechnicians.get(0);
        }
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new Shop());
    }

    @Override
    public void resetMaps() {
        this.customerVehicleMap.reset(this.shop.getCustomerList(), this.shop.getVehicleList(),
                this.shop.getAppointmentList());
        this.vehicleDataMap.reset(this.shop.getVehicleList(), this.shop.getCustomerList(),
                this.shop.getServiceList());
        this.serviceDataMap.reset(this.shop.getServiceList(), this.shop.getTechnicianList(),
                this.shop.getVehicleList());
        this.appointmentDataMap.reset(this.shop.getAppointmentList(), this.shop.getTechnicianList(),
                this.shop.getCustomerList());;
        this.technicianDataMap.reset(this.shop.getTechnicianList(), this.shop.getServiceList(),
                this.shop.getAppointmentList());
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
    public Path getShopFilePath() {
        return userPrefs.getShopFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setShopFilePath(Path shopFilePath) {
        requireNonNull(shopFilePath);
        userPrefs.setShopFilePath(shopFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public void setShop(ReadOnlyShop shop) {
        this.shop.resetData(shop);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    //=========== AddressBook ================================================================================
    @Override
    public ReadOnlyShop getShop() {
        return shop;
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
        resetMaps();
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        //#todo fix 44 allow shops
    }

    /**
     * Checks whether Shop already has customer
     *
     * @param customerId Customer ID to be checked
     */
    @Override
    public boolean hasCustomer(int customerId) {
        return this.shop.hasCustomer(customerId);
    }

    @Override
    public void deleteCustomer(Customer target) {
        this.shop.removeCustomer(target);
        resetMaps();
        updateFilteredServiceList(PREDICATE_SHOW_ALL_SERVICES);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        updateFilteredVehicleList(PREDICATE_SHOW_ALL_VEHICLES);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setCustomer(Customer target, Customer editedPerson) {
        requireAllNonNull(target, editedPerson);
        this.shop.setCustomer(target, editedPerson);
        resetMaps();
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return this.sortedFilteredCustomers;
    }

    @Override
    public void updateFilteredCustomerList(Predicate<? super Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
        if (filteredCustomers.size() == 0) {
            selectCustomer(null);
        } else if (selectedCustomer == null && filteredCustomers.size() > 0) {
            selectCustomer(filteredCustomers.get(0));
        } else if (selectedCustomer != null && filteredCustomers.stream()
                .noneMatch(selectedCustomer::isSameCustomer)) {
            selectCustomer(filteredCustomers.get(0));
        }
    }

    // ==== For Vehicles ==

    @Override
    public ObservableList<Vehicle> getFilteredVehicleList() {
        return this.sortedFilteredVehicles;
    }

    /**
     * Adds vehicle to the shop
     *
     * @param vehicle Vehicle to be added
     */
    @Override
    public void addVehicle(int customerId, Vehicle vehicle) {
        this.shop.addVehicle(customerId, vehicle);
        resetMaps();
        updateFilteredVehicleList(PREDICATE_SHOW_ALL_VEHICLES);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
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

    @Override
    public void deleteVehicle(Vehicle target) {
        this.shop.removeVehicle(target);
        Customer targetOwner = vehicleDataMap.getVehicleCustomer(target);
        targetOwner.removeVehicle(target);
        this.shop.setCustomer(vehicleDataMap.getVehicleCustomer(target), targetOwner);
        resetMaps();
        updateFilteredVehicleList(PREDICATE_SHOW_ALL_VEHICLES);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        updateFilteredServiceList(PREDICATE_SHOW_ALL_SERVICES);
    }

    @Override
    public void setVehicle(Vehicle target, Vehicle editedVehicle) {
        requireAllNonNull(target, editedVehicle);
        this.shop.setVehicle(target, editedVehicle);
        resetMaps();
    }

    // ==== For Services ==

    @Override
    public ObservableList<Service> getFilteredServiceList() {
        return this.sortedFilteredServices;
    }

    /**
     * Adds service
     *
     * @param service Service to add
     */
    @Override
    public void addService(int vehicleId, Service service) {
        this.shop.addService(vehicleId, service);
        resetMaps();
        updateFilteredServiceList(PREDICATE_SHOW_ALL_SERVICES);
    }

    /**
     * @param serviceId ID of service
     * @return Whether service already in the system
     */
    @Override
    public boolean hasService(int serviceId) {
        return this.shop.hasService(serviceId);
    }

    @Override
    public void deleteService(Service service) {
        this.shop.removeService(service);
        resetMaps();
        updateFilteredServiceList(PREDICATE_SHOW_ALL_SERVICES);
        updateFilteredVehicleList(PREDICATE_SHOW_ALL_VEHICLES);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    // ==== For Appointment ==

    /**
     * Adds appointment
     *
     * @param appointment Appointment to add
     */
    @Override
    public void addAppointment(Appointment appointment) {
        this.shop.addAppointment(appointment);
        resetMaps();
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public boolean hasAppointment(int appointmentId) {
        return this.shop.hasAppointment(appointmentId);
    }

    @Override
    public void deleteAppointment(Appointment target) {
        this.shop.removeAppointment(target);
        Customer targetOwner = appointmentDataMap.getAppointmentCustomer(target);
        targetOwner.removeAppointment(target);
        this.shop.setCustomer(appointmentDataMap.getAppointmentCustomer(target), targetOwner);
        resetMaps();
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return this.sortedFilteredAppointments;
    }

    // ==== For Part ==

    /**
     * Adds part
     *
     * @param partName Name of the part to add
     * @param quantity Quantity of the part to add
     */
    @Override
    public void addPart(String partName, int quantity) {
        this.shop.addPart(partName, quantity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPartToService(int serviceId, String partName, int quantity) throws NoSuchElementException {
        this.shop.addPartToService(serviceId, partName, quantity);
        resetMaps();
        updateFilteredServiceList(PREDICATE_SHOW_ALL_SERVICES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTechnicianToService(int serviceId, int techId) throws NoSuchElementException {
        this.shop.addTechnicianToService(serviceId, techId);
        resetMaps();
        updateFilteredServiceList(PREDICATE_SHOW_ALL_SERVICES);
    }

    @Override
    public void addTechnicianToAppointment(int techId, int appointmentId) throws NoSuchElementException {
        this.shop.addTechnicianToAppointment(techId, appointmentId);
        resetMaps();
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    /**
     * Checks if part already exists
     *
     * @param partName Name of part to check
     */
    @Override
    public boolean hasPart(String partName) {
        return this.shop.hasPart(partName);
    }

    @Override
    public ObservableList<Technician> getFilteredTechnicianList() {
        return this.sortedFilteredTechnicians;
    }

    // ==== For Technician ==
    @Override
    public void addTechnician(Technician technician) {
        this.shop.addTechnician(technician);
        updateFilteredServiceList(PREDICATE_SHOW_ALL_SERVICES);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public boolean hasTechnician(int technicianId) {
        return this.shop.hasTechnician(technicianId);
    }

    @Override
    public void deleteTechnician(Technician target) {
        this.shop.removeTechnician(target);
        resetMaps();
        updateFilteredServiceList(PREDICATE_SHOW_ALL_SERVICES);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void setTechnician(Technician target, Technician editedPerson) {
        requireAllNonNull(target, editedPerson);
        shop.setTechnician(target, editedPerson);
        this.resetMaps();
    }

    // ==== Mapped ==
    @Override
    public CustomerVehicleMap getCustomerVehicleMap() {
        return this.customerVehicleMap;
    }

    @Override
    public VehicleDataMap getVehicleDataMap() {
        return this.vehicleDataMap;
    }

    @Override
    public ServiceDataMap getServiceDataMap() {
        return this.serviceDataMap;
    }

    @Override
    public AppointmentDataMap getAppointmentDataMap() {
        return this.appointmentDataMap;
    }

    @Override
    public TechnicianDataMap getTechnicianDataMap() {
        return this.technicianDataMap;
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
    public PartMap getPartMap() {
        return partMap;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTechnicianList(Predicate<? super Technician> predicate) {
        requireNonNull(predicate);
        filteredTechnicians.setPredicate(predicate);
        if (filteredTechnicians.size() == 0) {
            selectTechnician(null);
        } else if (selectedTechnician == null && filteredTechnicians.size() > 0) {
            selectTechnician(filteredTechnicians.get(0));
        } else if (selectedTechnician != null && filteredTechnicians.stream()
                .noneMatch(selectedTechnician::isSameStaff)) {
            selectTechnician(filteredTechnicians.get(0));
        }
    }

    @Override
    public void updateFilteredServiceList(Predicate<? super Service> predicate) {
        requireNonNull(predicate);
        filteredServices.setPredicate(predicate);
        if (filteredServices.size() == 0) {
            selectService(null);
        } else if (selectedService == null && filteredServices.size() > 0) {
            selectService(filteredServices.get(0));
        } else if (selectedService != null && filteredServices.stream()
                .noneMatch(selectedService::isSameService)) {
            selectService(filteredServices.get(0));
        }
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<? super Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
        if (filteredAppointments.size() == 0) {
            selectAppointment(null);
        } else if (selectedAppointment == null && filteredAppointments.size() > 0) {
            selectAppointment(filteredAppointments.get(0));
        } else if (selectedAppointment != null && filteredAppointments.stream()
                .noneMatch(selectedAppointment::isSameAppointment)) {
            selectAppointment(filteredAppointments.get(0));
        }
    }

    @Override
    public void updateFilteredVehicleList(Predicate<? super Vehicle> predicate) {
        requireNonNull(predicate);
        filteredVehicles.setPredicate(predicate);
        if (filteredVehicles.size() == 0) {
            selectVehicle(null);
        } else if (selectedVehicle == null && filteredVehicles.size() > 0) {
            selectVehicle(filteredVehicles.get(0));
        } else if (selectedVehicle != null && filteredVehicles.stream()
                .noneMatch(selectedVehicle::isSameVehicle)) {
            selectVehicle(filteredVehicles.get(0));
        }
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);
        this.shop.setAppointment(target, editedAppointment);
        this.resetMaps();
    }

    //    @Override
    //    public void updateFilteredPartList(Predicate<? super Part> predicate) {
    //        requireNonNull(predicate);
    //        filteredParts.setPredicate(predicate);
    //    }

    //    @Override
    //    public void updateFilteredAppointmentList(Predicate<? super Appointment> predicate) {
    //        requireNonNull(predicate);
    //        filteredAppointments.setPredicate(predicate);
    //    }

    @Override
    public void updatePartsMap() {
        //todo: GUI stuff for printing!
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

    @Override
    public void selectCustomer(Customer customer) {
        selectedCustomer = customer;
    }

    @Override
    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    @Override
    public void selectVehicle(Vehicle vehicle) {
        selectedVehicle = vehicle;
    }

    @Override
    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    @Override
    public void selectService(Service service) {
        selectedService = service;
    }

    @Override
    public Service getSelectedService() {
        return selectedService;
    }

    @Override
    public void selectAppointment(Appointment appointment) {
        selectedAppointment = appointment;
    }

    @Override
    public Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    @Override
    public void selectTechnician(Technician technician) {
        selectedTechnician = technician;
    }

    @Override
    public Technician getSelectedTechnician() {
        return selectedTechnician;
    }

    @Override
    public void setService(Service target, Service editedService) {
        requireAllNonNull(target, editedService);
        this.shop.setService(target, editedService);
        this.resetMaps();
    }

    @Override
    public void updateCustomerComparator(Comparator<? super Customer> cmp) {
        this.sortedFilteredCustomers.setComparator(cmp);
        if (sortedFilteredCustomers.size() == 0) {
            selectCustomer(null);
        } else if (selectedCustomer == null && sortedFilteredCustomers.size() > 0) {
            selectCustomer(sortedFilteredCustomers.get(0));
        } else if (selectedCustomer != null && sortedFilteredCustomers.stream()
                .noneMatch(selectedCustomer::isSameCustomer)) {
            selectCustomer(sortedFilteredCustomers.get(0));
        }
    }

    @Override
    public void updateVehicleComparator(Comparator<? super Vehicle> cmp) {
        this.sortedFilteredVehicles.setComparator(cmp);
        if (sortedFilteredVehicles.size() == 0) {
            selectVehicle(null);
        } else if (selectedVehicle == null && sortedFilteredVehicles.size() > 0) {
            selectVehicle(sortedFilteredVehicles.get(0));
        } else if (selectedVehicle != null && sortedFilteredVehicles.stream()
                .noneMatch(selectedVehicle::isSameVehicle)) {
            selectVehicle(sortedFilteredVehicles.get(0));
        }
    }

    @Override
    public void updateServiceComparator(Comparator<? super Service> cmp) {
        this.sortedFilteredServices.setComparator(cmp);
        if (sortedFilteredServices.size() == 0) {
            selectService(null);
        } else if (selectedService == null && sortedFilteredServices.size() > 0) {
            selectService(sortedFilteredServices.get(0));
        } else if (selectedService != null && sortedFilteredServices.stream()
                .noneMatch(selectedService::isSameService)) {
            selectService(sortedFilteredServices.get(0));
        }
    }

    @Override
    public void updateAppointmentComparator(Comparator<? super Appointment> cmp) {
        this.sortedFilteredAppointments.setComparator(cmp);
        if (sortedFilteredAppointments.size() == 0) {
            selectAppointment(null);
        } else if (selectedAppointment == null && sortedFilteredAppointments.size() > 0) {
            selectAppointment(sortedFilteredAppointments.get(0));
        } else if (selectedAppointment != null && sortedFilteredAppointments.stream()
                .noneMatch(selectedAppointment::isSameAppointment)) {
            selectAppointment(sortedFilteredAppointments.get(0));
        }
    }

    @Override
    public void updateTechnicianComparator(Comparator<? super Technician> cmp) {
        this.sortedFilteredTechnicians.setComparator(cmp);
        if (sortedFilteredTechnicians.size() == 0) {
            selectTechnician(null);
        } else if (selectedTechnician == null && sortedFilteredTechnicians.size() > 0) {
            selectTechnician(sortedFilteredTechnicians.get(0));
        } else if (selectedTechnician != null && sortedFilteredTechnicians.stream()
                .noneMatch(selectedTechnician::isSameStaff)) {
            selectTechnician(sortedFilteredTechnicians.get(0));
        }
    }
}
