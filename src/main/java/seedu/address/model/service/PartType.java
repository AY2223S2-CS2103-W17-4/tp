package seedu.address.model.service;

/**
 * Represents the possible types of vehicle parts that AutoM8 can add to the system.
 */
public enum PartType {
    WHEELS("wheels"),
    SUSPENSION("suspension"),
    FRAME("frame"),
    GEARBOX("gearbox"),
    BOLT("bolt"),
    HEADLAMP("headlamp"),
    LIGHT("light"),
    HORN("horn"),
    STEERING("steering"),
    OTHERS("others");

    private final String value;

    /**
     * Constructs a new PartType with the specified value.
     *
     * @param value the value of this PartType
     */
    PartType(String value) {
        this.value = value;
    }

    /**
     * Returns whether this PartType's value is equal to the specified input, ignoring case.
     *
     * @param input the input to compare with this PartType's value
     * @return whether this PartType's value is equal to the specified input, ignoring case
     */
    public boolean isEqual(String input) {
        return this.value.equalsIgnoreCase(input);
    }

    public String getValue() {
        return value;
    }
}
