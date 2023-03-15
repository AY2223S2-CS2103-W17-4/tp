package seedu.address.model.service;

/**
 * Represents the possible types of service status that AutoM8 can recognise.
 */
public enum ServiceStatus {
    TO_REPAIR("To Repair"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete"),
    CANCELLED("Cancelled"),
    ON_HOLD("On Hold");

    private final String value;

    /**
     * Constructs a new ServiceStatus with the specified value.
     *
     * @param value the value of this ServiceStatus
     */
    ServiceStatus(String value) {
        this.value = value;
    }

    /**
     * Returns whether this ServiceStatus's value is equal to the specified input, ignoring case.
     *
     * @param input the input to compare with this ServiceStatus's value
     * @return whether this ServiceStatus's value is equal to the specified input, ignoring case
     */
    public boolean isEqual(String input) {
        return this.value.equalsIgnoreCase(input);
    }

    public String getValue() {
        return value;
    }
}
