package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.entity.person.Technician;

/**
 * A UI component that displays information of a {@code Customer}.
 */
public class TechnicianCard extends UiPart<Region> {

    private static final String FXML = "TechnicianListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Technician technician;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;

    @FXML
    private Label email;
    @FXML
    private Label numberOfServices;
    @FXML
    private Label numberOfAppointments;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code CustomerCode} with the given {@code Customer} and index to display.
     */
    public TechnicianCard(Technician technician, int services, int appointments) {
        super(FXML);
        this.technician = technician;
        id.setText(technician.getId() + ". ");
        name.setText(technician.getName().fullName + ", ");
        numberOfServices.setText(services + "");
        numberOfAppointments.setText(appointments + "");
        phone.setText(technician.getPhone().value);
        email.setText(technician.getEmail().value);
        technician.getTags().stream()
                .forEach(tag -> tags.getChildren()
                        .add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TechnicianCard)) {
            return false;
        }

        // state check
        TechnicianCard card = (TechnicianCard) other;
        return id.getText().equals(card.id.getText())
                && technician.equals(card.technician);
    }
}
