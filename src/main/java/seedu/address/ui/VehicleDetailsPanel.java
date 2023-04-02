package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.mapping.VehicleDataMap;
import seedu.address.model.service.Service;
import seedu.address.model.service.Vehicle;

/**
 * A UI component that displays information of a {@code Vehicle}.
 */
public class VehicleDetailsPanel extends UiPart<Region> {

    private static final String FXML = "VehicleDetailsPanel.fxml";

    public final Vehicle vehicle;
    @FXML
    private VBox parentContainer;
    @FXML
    private Label id;
    @FXML
    private Label plateNumber;
    @FXML
    private Label brand;
    @FXML
    private Label color;
    @FXML
    private Label type;
    @FXML
    private Label vehicleServicesLabel;
    @FXML
    private VBox vehicleServices;
    @FXML
    private VBox vehicleOwner;

    /**
     * Creates a {@code VehicleCode} with the given {@code Vehicle} and index to display.
     */
    public VehicleDetailsPanel(Vehicle vehicle, VehicleDataMap dataMap) {
        super(FXML);
        this.vehicle = vehicle;
        update(vehicle, dataMap);
    }

    private void update(Vehicle vehicle, VehicleDataMap dataMap) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

        if (vehicle == null) {
            parentContainer.getChildren().clear();
            parentContainer.getChildren().add(new EmptyDetailsPanelPlaceholder("Vehicle").getRoot());
            return;
        }

        id.setText("Vehicle ID: " + vehicle.getId());
        plateNumber.setText(vehicle.getPlateNumber() + "");
        color.setText(vehicle.getColor());
        brand.setText(vehicle.getBrand());
        type.setText(vehicle.getType().getValue());
        List<Service> services = dataMap.getVehicleServices(vehicle);
        Customer owner = dataMap.getVehicleCustomer(vehicle);

        Label ownerLabel = new Label(owner.getId() + ". " + owner.getName().fullName);
        ownerLabel.getStyleClass().add("details-info");
        vehicleOwner.getChildren().add(ownerLabel);
        vehicleServicesLabel.setText("Services (" + services.size() + ")");

        for (int i = 0; i < services.size(); i++) {
            Service s = services.get(i);
            Label sLabel = new Label(s.getId() + ". " + s.getDescription() + ", Status: " + s.getStatus().getValue());
            sLabel.getStyleClass().add("details-info");
            vehicleServices.getChildren().add(sLabel);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VehicleDetailsPanel)) {
            return false;
        }

        // state check
        VehicleDetailsPanel panel = (VehicleDetailsPanel) other;
        return id.getText().equals(panel.id.getText())
                && vehicle.equals(panel.vehicle);
    }
}
