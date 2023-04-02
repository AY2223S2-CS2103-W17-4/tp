package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A UI component that displays PlaceHolder UI for empty/null detail panels.
 */
public class EmptyDetailsPanelPlaceholder extends UiPart<Region> {

    private static final String FXML = "EmptyDetailsPanelPlaceholder.fxml";

    @FXML
    private Label title;

    /**
     * Creates a PlaceHolder UI for empty/null detail panels
     */
    public EmptyDetailsPanelPlaceholder(String object) {
        super(FXML);
        title.setText("No selected " + object);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmptyDetailsPanelPlaceholder)) {
            return false;
        }

        // state check
        EmptyDetailsPanelPlaceholder panel = (EmptyDetailsPanelPlaceholder) other;
        return title.getText().equals(panel.title.getText());
    }
}
