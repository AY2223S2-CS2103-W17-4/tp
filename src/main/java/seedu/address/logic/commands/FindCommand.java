package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.Findable;
import seedu.address.model.Model;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case in-sensitive.
 */
public class FindCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all entities whose attributes match "
        + "the specified keywords (case-insensitive) or the given date and displays them in the tab lists.\n"
        + "Parameters: KEYWORD [MORE SPACE-SEPERATED KEYWORDS] "
        + "Example: " + COMMAND_WORD + " James james@gmail.com";

    public static final String MESSAGE_SUCCESS = "%d customers, %d vehicles, %d services, %d appointments, "
        + "%d technicians found";

    private final Predicate<Findable> predicate;

    public FindCommand(Predicate<Findable> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) {
        requireNonNull(model);

        model.updateFilteredCustomerList(predicate);
        model.updateFilteredVehicleList(predicate);
        model.updateFilteredServiceList(predicate);
        model.updateFilteredAppointmentList(predicate);
        model.updateFilteredTechnicianList(predicate);

        return new CommandResult(
            String.format(MESSAGE_SUCCESS, model.getFilteredCustomerList().size(),
                model.getFilteredVehicleList().size(), model.getFilteredServiceList().size(),
                model.getFilteredAppointmentList().size(), model.getFilteredTechnicianList().size()), Tab.ALL);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
