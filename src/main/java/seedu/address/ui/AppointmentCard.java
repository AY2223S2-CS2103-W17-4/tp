package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.service.appointment.Appointment;


/**
 * A UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     * issue on AddressBook level 4</a>
     */

    public final Appointment appointment;
    public final Customer customer;
    public final int numberOfStaff;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label customerName;
    @FXML
    private Label datetime;
    @FXML
    private Label dateStatus;
    @FXML
    private Label numberOfStaffAssigned;

    /**
     * Creates a {@code AppointmentCode} with the given {@code Appointment} and number of staff assigned
     */
    public AppointmentCard(Appointment appointment, Customer customer, int numberOfStaff) {
        super(FXML);
        this.appointment = appointment;
        this.customer = customer;
        this.numberOfStaff = numberOfStaff;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        id.setText(appointment.getId() + ". ");
        customerName.setText(customer.getName().fullName);
        datetime.setText("Appointment DateTime: " + appointment.getTimeDate().format(dtf));
        setDateStatus(appointment.getDateStatus());
        numberOfStaffAssigned.setText(numberOfStaff + " Staff Assigned");
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
        if (!(other instanceof AppointmentCard)) {
            return false;
        }

        // state check
        AppointmentCard card = (AppointmentCard) other;
        return id.getText().equals(card.id.getText())
                && appointment.equals(card.appointment);
    }
}
