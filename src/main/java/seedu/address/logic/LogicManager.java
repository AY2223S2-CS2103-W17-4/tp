package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.StackUndoRedo;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
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
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final StackUndoRedo undoRedoStack;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.undoRedoStack = new StackUndoRedo();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        command.setData(undoRedoStack);
        commandResult = command.execute(model);

        try {
            storage.saveShop(model.getShop());
            undoRedoStack.push(command);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getShopFilePath() {
        return model.getShopFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return model.getFilteredCustomerList();
    }

    @Override
    public ObservableList<Vehicle> getFilteredVehicleList() {
        return model.getFilteredVehicleList();
    }

    @Override
    public ObservableList<Service> getFilteredServiceList() {
        return model.getFilteredServiceList();
    }

    /**
     * @return Unmodifiable view of the filtered list of appointments
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList();
    }

    /**
     * @return Unmodifiable view of the filtered list of technicians
     */
    @Override
    public ObservableList<Technician> getFilteredTechnicianList() {
        return model.getFilteredTechnicianList();
    }

    @Override
    public PartMap getPartMap() {
        return model.getPartMap();
    }

    @Override
    public CustomerVehicleMap getCustomerVehicleMap() {
        return model.getCustomerVehicleMap();
    }

    @Override
    public VehicleDataMap getVehicleDataMap() {
        return model.getVehicleDataMap();
    }

    @Override
    public ServiceDataMap getServiceDataMap() {
        return model.getServiceDataMap();
    }

    @Override
    public AppointmentDataMap getAppointmentDataMap() {
        return model.getAppointmentDataMap();
    }

    @Override
    public TechnicianDataMap getTechnicianDataMap() {
        return model.getTechnicianDataMap();
    }

    @Override
    public Customer getSelectedCustomer() {
        return model.getSelectedCustomer();
    }

    @Override
    public Vehicle getSelectedVehicle() {
        return model.getSelectedVehicle();
    }

    @Override
    public Service getSelectedService() {
        return model.getSelectedService();
    }

    @Override
    public Appointment getSelectedAppointment() {
        return model.getSelectedAppointment();
    }

    @Override
    public Technician getSelectedTechnician() {
        return model.getSelectedTechnician();
    }
}
