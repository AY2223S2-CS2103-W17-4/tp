package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.mapping.AppointmentDataMap;
import seedu.address.model.service.appointment.Appointment;

/**
 * An UI component that displays information of a {@code Vehicle}.
 */
public class AppointmentDetailsPanel extends UiPart<Region> {

    private static final String FXML = "AppointmentDetailsPanel.fxml";
    public final Appointment appointment;
    @FXML
    private VBox parentContainer;
    @FXML
    private Label id;
    @FXML
    private Label customerName;
    @FXML
    private Label datetime;
    @FXML
    private Label customerId;
    @FXML
    private Label asOf;
    @FXML
    private Label dateStatus;
    @FXML
    private Label appointmentStaffLabel;
    @FXML
    private VBox appointmentStaff;

    /**
     * Creates a {@code VehicleCode} with the given {@code Vehicle} and index to display.
     */
    public AppointmentDetailsPanel(Appointment appointment, AppointmentDataMap dataMap) {
        super(FXML);
        this.appointment = appointment;
        update(appointment, dataMap);
    }

    private void update(Appointment appointment, AppointmentDataMap dataMap) {
        if (appointment == null) {
            parentContainer.getChildren().clear();
            parentContainer.getChildren().add(new EmptyDetailsPanelPlaceholder("Appointment").getRoot());
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

        id.setText("Appointment ID: " + appointment.getId());
        customerName.setText(dataMap.getAppointmentCustomer(appointment).getName().fullName);
        datetime.setText(appointment.getTimeDate().format(dtf));
        customerId.setText(appointment.getCustomerId() + "");
        asOf.setText("(as of " + LocalDateTime.now().format(dtf) + ")");
        setDateStatus(appointment.getDateStatus());

        List<Technician> technicians = dataMap.getAppointmentTechnicians(appointment);
        appointmentStaffLabel.setText("Staff (" + technicians.size() + ")");

        for (int i = 0; i < technicians.size(); i++) {
            Technician t = technicians.get(i);
            Label tLabel = new Label(t.getId() + ". " + t.getName().fullName);
            tLabel.getStyleClass().add("details-info");
            appointmentStaff.getChildren().add(tLabel);
        }
    }

    private void setDateStatus(Appointment.DateStatus status) {
        switch (status) {
        case PASSED:
            dateStatus.setText(status.name());
            dateStatus.getStyleClass().add("passed-tag");
            break;
        case UPCOMING:
            dateStatus.setText(status.name());
            dateStatus.getStyleClass().add("upcoming-tag");
            break;
        default:
            dateStatus.setText(status.name());
            dateStatus.getStyleClass().add("today-tag");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentDetailsPanel)) {
            return false;
        }

        // state check
        AppointmentDetailsPanel panel = (AppointmentDetailsPanel) other;
        return id.getText().equals(panel.id.getText())
                && appointment.equals(panel.appointment);
    }
}
