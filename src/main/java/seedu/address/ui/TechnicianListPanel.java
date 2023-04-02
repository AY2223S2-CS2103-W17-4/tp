package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.mapping.TechnicianDataMap;

/**
 * Panel containing the list of customers.
 */
public class TechnicianListPanel extends UiPart<Region> {
    private static final String FXML = "TechnicianListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TechnicianListPanel.class);

    @FXML
    private ListView<Technician> technicianListView;
    private TechnicianDataMap dataMap;

    /**
     * Creates a {@code CustomerListPanel} with the given {@code ObservableList}.
     */
    public TechnicianListPanel(ObservableList<Technician> technicianList, TechnicianDataMap dataMap) {
        super(FXML);
        technicianListView.setItems(technicianList);
        technicianListView.setCellFactory(listView -> new TechnicianListViewCell());
        this.dataMap = dataMap;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Technician}
     * using a {@code TechnicianCard}.
     */
    class TechnicianListViewCell extends ListCell<Technician> {
        @Override
        protected void updateItem(Technician technician, boolean empty) {
            super.updateItem(technician, empty);

            if (empty || technician == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TechnicianCard(technician,
                        dataMap.getTechnicianServices(technician).size(),
                        dataMap.getTechnicianAppointments(technician).size()).getRoot());
            }
        }
    }

}
