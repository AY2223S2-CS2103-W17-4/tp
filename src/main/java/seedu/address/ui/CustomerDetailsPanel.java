package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.mapping.CustomerVehicleMap;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.appointment.Appointment;

/**
 * A UI component that displays information of a {@code Customer}.
 */
public class CustomerDetailsPanel extends UiPart<Region> {

    private static final String FXML = "CustomerDetailsPanel.fxml";

    public final Customer customer;
    @FXML
    private VBox parentContainer;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label vehiclesLabel;
    @FXML
    private Label appointmentsLabel;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox customerVehicles;
    @FXML
    private VBox customerAppointments;

    /**
     * Creates a {@code CustomerCode} with the given {@code Customer} and index to display.
     */
    public CustomerDetailsPanel(Customer customer, CustomerVehicleMap dataMap) {
        super(FXML);
        this.customer = customer;
        update(customer, dataMap);
    }

    private void update(Customer customer, CustomerVehicleMap dataMap) {
        if (customer == null) {
            parentContainer.getChildren().clear();
            parentContainer.getChildren().add(new EmptyDetailsPanelPlaceholder("Customer").getRoot());
            return;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

        id.setText("Customer ID: " + customer.getId());
        name.setText(customer.getName().fullName);
        phone.setText(customer.getPhone().value);
        email.setText(customer.getEmail().value);
        address.setText(customer.getAddress().value);
        customer.getTags().stream()
                .forEach(tag -> tags.getChildren()
                        .add(new Label(tag.tagName)));
        List<Vehicle> vehicles = dataMap.getCustomerVehicles(customer);
        List<Appointment> appointments = dataMap.getCustomerAppointments(customer);

        vehiclesLabel.setText("Vehicles (" + vehicles.size() + ")");
        appointmentsLabel.setText("Appointments (" + appointments.size() + ")");

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            Label vLabel = new Label(v.getId() + ". " + v.getBrand() + " (" + v.getPlateNumber() + ") "
                    + v.getType().getValue());
            vLabel.getStyleClass().add("details-info");
            customerVehicles.getChildren().add(vLabel);
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            Label aLabel = new Label(a.getId() + ". " + a.getTimeDate().format(dtf));
            aLabel.getStyleClass().add("details-info");
            customerAppointments.getChildren().add(aLabel);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerDetailsPanel)) {
            return false;
        }

        // state check
        CustomerDetailsPanel panel = (CustomerDetailsPanel) other;
        return id.getText().equals(panel.id.getText())
                && customer.equals(panel.customer)
                && customer.getVehicleIds().size() == panel.customer.getVehicleIds().size();
    }
}
