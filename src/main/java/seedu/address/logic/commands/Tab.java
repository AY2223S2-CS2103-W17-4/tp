package seedu.address.logic.commands;

/**
 * Represents the Tabs that AutoM8 can display to the user.
 * This helps to identify which UI component to update based on CommandResult's executions
 */
public enum Tab {
    CUSTOMERS,
    VEHICLES,
    SERVICES,
    APPOINTMENTS,
    TECHNICIANS,
    PARTS,
    ALL,
    UNCHANGED;

    @Override
    public String toString() {
        return name();
    }
}
