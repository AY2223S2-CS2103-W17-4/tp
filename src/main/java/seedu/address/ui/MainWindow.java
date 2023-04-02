package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListAppointmentsCommand;
import seedu.address.logic.commands.ListCustomersCommand;
import seedu.address.logic.commands.ListPartsCommand;
import seedu.address.logic.commands.ListServicesCommand;
import seedu.address.logic.commands.ListTechniciansCommand;
import seedu.address.logic.commands.ListVehiclesCommand;
import seedu.address.logic.commands.Tab;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    private static final String FXML = "MainWindow.fxml";

    public final String[] tabResultDisplayMessages = new String[6];

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private CustomerListPanel customerListPanel;
    private VehicleListPanel vehicleListPanel;
    private ServiceListPanel serviceListPanel;
    private AppointmentListPanel appointmentListPanel;
    private TechnicianListPanel technicianListPanel;
    private PartListPanel partListPanel;
    private CustomerDetailsPanel customerDetailsPanel;
    private VehicleDetailsPanel vehicleDetailsPanel;
    private ServiceDetailsPanel serviceDetailsPanel;
    private AppointmentDetailsPanel appointmentDetailsPanel;
    private TechnicianDetailsPanel technicianDetailsPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private HBox commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane customerListPanelPlaceholder;

    @FXML
    private StackPane vehicleListPanelPlaceholder;

    @FXML
    private StackPane serviceListPanelPlaceholder;

    @FXML
    private StackPane appointmentListPanelPlaceholder;

    @FXML
    private StackPane technicianListPanelPlaceholder;
    @FXML
    private StackPane partListPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane tabs;
    @FXML
    private StackPane customerDetailsPlaceholder;
    @FXML
    private StackPane vehicleDetailsPlaceholder;
    @FXML
    private StackPane serviceDetailsPlaceholder;
    @FXML
    private StackPane appointmentDetailsPlaceholder;
    @FXML
    private StackPane technicianDetailsPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    private void initCustomerListPanel() {
        customerListPanel = new CustomerListPanel(logic.getFilteredCustomerList(),
                logic.getCustomerVehicleMap());
        customerListPanelPlaceholder.getChildren().add(customerListPanel.getRoot());
    }

    private void initVehicleListPanel() {
        vehicleListPanel = new VehicleListPanel(logic.getFilteredVehicleList(),
                logic.getVehicleDataMap());
        vehicleListPanelPlaceholder.getChildren().add(vehicleListPanel.getRoot());
    }

    private void initServiceListPanel() {
        serviceListPanel = new ServiceListPanel(logic.getFilteredServiceList(),
                logic.getServiceDataMap());
        serviceListPanelPlaceholder.getChildren().add(serviceListPanel.getRoot());
    }

    private void initAppointmentListPanel() {
        appointmentListPanel = new AppointmentListPanel(logic.getFilteredAppointmentList(),
                logic.getAppointmentDataMap());
        appointmentListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());
    }

    private void initTechnicianListPanel() {
        technicianListPanel = new TechnicianListPanel(logic.getFilteredTechnicianList(),
                logic.getTechnicianDataMap());
        technicianListPanelPlaceholder.getChildren().add(technicianListPanel.getRoot());
    }

    private void initPartListPanel() {
        partListPanel = new PartListPanel(logic.getPartMap());
        partListPlaceholder.getChildren().add(partListPanel.getRoot());
    }

    private void initTabResultDisplayMessages() {
        tabResultDisplayMessages[Tab.CUSTOMERS.ordinal()] = ListCustomersCommand.MESSAGE_SUCCESS;
        tabResultDisplayMessages[Tab.VEHICLES.ordinal()] = ListVehiclesCommand.MESSAGE_SUCCESS;
        tabResultDisplayMessages[Tab.SERVICES.ordinal()] = ListServicesCommand.MESSAGE_SUCCESS;
        tabResultDisplayMessages[Tab.APPOINTMENTS.ordinal()] = ListAppointmentsCommand.MESSAGE_SUCCESS;
        tabResultDisplayMessages[Tab.TECHNICIANS.ordinal()] = ListTechniciansCommand.MESSAGE_SUCCESS;
        tabResultDisplayMessages[Tab.PARTS.ordinal()] = ListPartsCommand.MESSAGE_SUCCESS;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        initTabResultDisplayMessages();

        initCustomerListPanel();
        initVehicleListPanel();
        initServiceListPanel();
        initAppointmentListPanel();
        initTechnicianListPanel();
        initPartListPanel();

        initSelected();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getShopFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        HBox.setHgrow(commandBox.getRoot(), Priority.ALWAYS);

        tabs.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            resultDisplay.setFeedbackToUser(tabResultDisplayMessages[newValue.intValue()]);
            if (newValue.intValue() == Tab.PARTS.ordinal()) {
                initPartListPanel();
            }
        });
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    private void updateSelectedTab(CommandResult commandResult) {
        int tabIndex = commandResult.getType().ordinal();
        if (commandResult.getType() != Tab.UNCHANGED && commandResult.getType() != Tab.ALL) {
            tabResultDisplayMessages[tabIndex] = commandResult.getFeedbackToUser();
        }
        if (commandResult.getType() == Tab.ALL) {
            for (int i = 0; i < tabResultDisplayMessages.length; i++) {
                tabResultDisplayMessages[i] = commandResult.getFeedbackToUser();
            }
        }
        if (!tabs.getSelectionModel().isSelected(tabIndex) && commandResult.getType() != Tab.UNCHANGED) {
            tabs.getSelectionModel().select(tabIndex);
        }
    }

    private void initSelected() {
        customerDetailsPlaceholder.getChildren().clear();
        customerDetailsPanel = new CustomerDetailsPanel(logic.getSelectedCustomer(), logic.getCustomerVehicleMap());
        customerDetailsPlaceholder.getChildren().add(customerDetailsPanel.getRoot());

        vehicleDetailsPlaceholder.getChildren().clear();
        vehicleDetailsPanel = new VehicleDetailsPanel(logic.getSelectedVehicle(), logic.getVehicleDataMap());
        vehicleDetailsPlaceholder.getChildren().add(vehicleDetailsPanel.getRoot());

        serviceDetailsPlaceholder.getChildren().clear();
        serviceDetailsPanel = new ServiceDetailsPanel(logic.getSelectedService(), logic.getServiceDataMap());
        serviceDetailsPlaceholder.getChildren().add(serviceDetailsPanel.getRoot());

        appointmentDetailsPlaceholder.getChildren().clear();
        appointmentDetailsPanel = new AppointmentDetailsPanel(logic.getSelectedAppointment(),
                logic.getAppointmentDataMap());
        appointmentDetailsPlaceholder.getChildren().add(appointmentDetailsPanel.getRoot());

        technicianDetailsPlaceholder.getChildren().clear();
        technicianDetailsPanel = new TechnicianDetailsPanel(logic.getSelectedTechnician(),
                logic.getTechnicianDataMap());
        technicianDetailsPlaceholder.getChildren().add(technicianDetailsPanel.getRoot());
    }


    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            updateSelectedTab(commandResult);
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            initSelected();

            // TODO: Hack to display updated parts on ui, need to make parts into observable map/list
            if (commandResult.getType() == Tab.PARTS) {
                initPartListPanel();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
