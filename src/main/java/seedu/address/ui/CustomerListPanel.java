package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.person.Customer;

import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
public class CustomerListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CustomerListPanel.class);

    @FXML
    private ListView<Customer> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public CustomerListPanel(ObservableList<Customer> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new CustomerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class CustomerListViewCell extends ListCell<Customer> {
        @Override
        protected void updateItem(Customer person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CustomerCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
