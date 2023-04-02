package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
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
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' shop file path.
     */
    Path getShopFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);


    //// AutoM8

    /** Returns an unmodifiable view of the filtered list of customers */
    ObservableList<Customer> getFilteredCustomerList();

    /** Returns an unmodifiable view of the filtered list of vehicles */
    ObservableList<Vehicle> getFilteredVehicleList();

    /** Returns an unmodifiable view of the filtered list of services */
    ObservableList<Service> getFilteredServiceList();

    /**
     * @return Unmodifiable view of the filtered list of appointments
     */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * @return Unmodifiable view of the filtered list of technicians
     */
    ObservableList<Technician> getFilteredTechnicianList();

    /**
     * @return parts
     */
    PartMap getPartMap();

    /** Returns a map of customers and their respective vehicle(s) */
    CustomerVehicleMap getCustomerVehicleMap();

    /** Returns maps of vehicles and their respective owner or services */
    VehicleDataMap getVehicleDataMap();

    /** Returns maps of services and their respective vehicle or technicians */
    ServiceDataMap getServiceDataMap();

    /** Returns maps of appointments and their respective staff and customers */
    AppointmentDataMap getAppointmentDataMap();

    /** Returns maps of technicians and their respective services and appointments */
    TechnicianDataMap getTechnicianDataMap();

    /** Returns currently selected customer*/
    Customer getSelectedCustomer();

    /** Returns currently selected vehicle*/
    Vehicle getSelectedVehicle();

    /** Returns currently selected service*/
    Service getSelectedService();

    /** Returns currently selected appointment*/
    Appointment getSelectedAppointment();

    /** Returns currently selected technician*/
    Technician getSelectedTechnician();
}
