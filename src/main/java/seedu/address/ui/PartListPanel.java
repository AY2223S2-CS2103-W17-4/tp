package seedu.address.ui;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.service.PartMap;

/**
 * A UI component that displays information of a part.
 */
public class PartListPanel extends UiPart<Region> {

    private static final String FXML = "PartListPanel.fxml";

    public final PartMap parts;
    @FXML
    private VBox partListPanel;

    /**
     * Creates a part listing.
     */
    public PartListPanel(PartMap parts) {
        super(FXML);
        this.parts = parts;
        for (Map.Entry<String, Integer> entry : parts.getEntrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Label nameLabel = new Label(key);
            Label quantityLabel = new Label("Quantity: " + value);
            VBox box = new VBox();
            box.getChildren().addAll(nameLabel, quantityLabel);
            box.getStyleClass().add("part-box");

            partListPanel.getChildren().add(box);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PartListPanel)) {
            return false;
        }

        // state check
        PartListPanel panel = (PartListPanel) other;
        return parts.equals(panel.parts);
    }
}
