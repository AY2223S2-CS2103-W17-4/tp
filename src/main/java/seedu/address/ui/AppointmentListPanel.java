package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.mapping.AppointmentDataMap;
import seedu.address.model.service.appointment.Appointment;

/**
 * Panel containing the list of services.
 */
public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "AppointmentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);

    @FXML
    private ListView<Appointment> appointmentListView;
    private AppointmentDataMap dataMap;

    /**
     * Creates a {@code VehicleListPanel} with the given {@code ObservableList}.
     */
    public AppointmentListPanel(ObservableList<Appointment> appointmentList, AppointmentDataMap dataMap) {
        super(FXML);
        appointmentListView.setItems(appointmentList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
        this.dataMap = dataMap;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Service}
     * using a {@code VehicleCard}.
     */
    class AppointmentListViewCell extends ListCell<Appointment> {
        @Override
        protected void updateItem(Appointment appointment, boolean empty) {
            super.updateItem(appointment, empty);

            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AppointmentCard(appointment,
                        dataMap.getAppointmentCustomer(appointment),
                        dataMap.getAppointmentTechnicians(appointment).size()).getRoot());
            }
        }
    }

}
