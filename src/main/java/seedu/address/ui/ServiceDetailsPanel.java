package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.mapping.ServiceDataMap;
import seedu.address.model.service.PartMap;
import seedu.address.model.service.Service;
import seedu.address.model.service.ServiceStatus;
import seedu.address.model.service.Vehicle;

/**
 * An UI component that displays information of a {@code Vehicle}.
 */
public class ServiceDetailsPanel extends UiPart<Region> {

    private static final String FXML = "ServiceDetailsPanel.fxml";
    private static final Color toRepair = Color.rgb(61, 167, 201);
    private static final Color inProgress = Color.rgb(252, 159, 39);
    private static final Color complete = Color.rgb(89, 201, 61);
    private static final Color cancelled = Color.rgb(236, 46, 46);
    private static final Color onHold = Color.rgb(255, 230, 0);

    public final Service service;
    @FXML
    private VBox parentContainer;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label entryDate;
    @FXML
    private Label estimatedFinishDate;
    @FXML
    private Label status;
    @FXML
    private Circle statusCircle;
    @FXML
    private VBox vehicleAssignedTo;
    @FXML
    private VBox serviceTechnicians;
    @FXML
    private VBox serviceParts;

    /**
     * Creates a {@code VehicleCode} with the given {@code Vehicle} and index to display.
     */
    public ServiceDetailsPanel(Service service, ServiceDataMap dataMap) {
        super(FXML);
        this.service = service;
        update(service, dataMap);
    }

    private void update(Service service, ServiceDataMap dataMap) {
        if (service == null) {
            parentContainer.getChildren().clear();
            parentContainer.getChildren().add(new EmptyDetailsPanelPlaceholder("Service").getRoot());
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        id.setText("Service ID: " + service.getId());
        description.setText(service.getDescription());
        entryDate.setText(service.getEntryDate().format(dtf));
        estimatedFinishDate.setText(service.getEstimatedFinishDate().format(dtf));
        status.setText(service.getStatus().getValue());
        setStatusCircle(service.getStatus());

        Vehicle v = dataMap.getServiceVehicle(service);
        Label vehicleLabel = new Label(v.getId() + ". " + v.getBrand() + " (" + v.getPlateNumber() + ") "
                + v.getType().getValue());
        vehicleLabel.getStyleClass().add("details-info");
        vehicleAssignedTo.getChildren().add(vehicleLabel);

        List<Technician> technicians = dataMap.getServiceTechnicians(service);
        PartMap partsRequired = service.getRequiredParts();

        for (int i = 0; i < technicians.size(); i++) {
            Technician t = technicians.get(i);
            Label tLabel = new Label(t.getId() + ". " + t.getName().fullName);
            tLabel.getStyleClass().add("details-info");
            serviceTechnicians.getChildren().add(tLabel);
        }

        for (Map.Entry<String, Integer> entry : partsRequired.getEntrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Label pLabel = new Label(value + " " + key);
            pLabel.getStyleClass().add("details-info");
            serviceParts.getChildren().add(pLabel);
        }
    }

    private void setStatusCircle(ServiceStatus status) {
        switch (status) {
        case TO_REPAIR:
            statusCircle.setFill(toRepair);
            break;
        case IN_PROGRESS:
            statusCircle.setFill(inProgress);
            break;
        case COMPLETE:
            statusCircle.setFill(complete);
            break;
        case CANCELLED:
            statusCircle.setFill(cancelled);
            break;
        default:
            statusCircle.setFill(onHold);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ServiceDetailsPanel)) {
            return false;
        }

        // state check
        ServiceDetailsPanel panel = (ServiceDetailsPanel) other;
        return id.getText().equals(panel.id.getText())
                && service.equals(panel.service);
    }
}
