package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.mapping.TechnicianDataMap;
import seedu.address.model.service.Service;
import seedu.address.model.service.appointment.Appointment;

/**
 * A UI component that displays information of a {@code Technician}.
 */
public class TechnicianDetailsPanel extends UiPart<Region> {

    private static final String FXML = "TechnicianDetailsPanel.fxml";

    public final Technician technician;
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
    private Label servicesLabel;
    @FXML
    private Label appointmentsLabel;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox technicianServices;
    @FXML
    private VBox technicianAppointments;

    /**
     * Creates a {@code TechnicianCode} with the given {@code Technician} and index to display.
     */
    public TechnicianDetailsPanel(Technician technician, TechnicianDataMap dataMap) {
        super(FXML);
        this.technician = technician;
        update(technician, dataMap);
    }

    private void update(Technician technician, TechnicianDataMap dataMap) {
        if (technician == null) {
            parentContainer.getChildren().clear();
            parentContainer.getChildren().add(new EmptyDetailsPanelPlaceholder("Technician").getRoot());
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

        id.setText("Technician ID: " + technician.getId());
        name.setText(technician.getName().fullName);
        phone.setText(technician.getPhone().value);
        email.setText(technician.getEmail().value);
        address.setText(technician.getAddress().value);
        technician.getTags().stream()
                .forEach(tag -> tags.getChildren()
                        .add(new Label(tag.tagName)));
        List<Service> services = dataMap.getTechnicianServices(technician);
        List<Appointment> appointments = dataMap.getTechnicianAppointments(technician);

        servicesLabel.setText("Services (" + services.size() + ")");
        appointmentsLabel.setText("Appointments (" + appointments.size() + ")");

        for (int i = 0; i < services.size(); i++) {
            Service s = services.get(i);
            Label sLabel = new Label(s.getId() + ". " + s.getDescription() + ", Status: " + s.getStatus().getValue());
            sLabel.getStyleClass().add("details-info");
            technicianServices.getChildren().add(sLabel);
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            Label aLabel = new Label(a.getId() + ". " + a.getTimeDate().format(dtf));
            aLabel.getStyleClass().add("details-info");
            technicianAppointments.getChildren().add(aLabel);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TechnicianDetailsPanel)) {
            return false;
        }

        // state check
        TechnicianDetailsPanel panel = (TechnicianDetailsPanel) other;
        return id.getText().equals(panel.id.getText())
                && technician.equals(panel.technician);
    }
}
