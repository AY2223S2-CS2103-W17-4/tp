package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Technician;

/**
 * Finds and returns the customer details of the provided id.
 */
public class ViewTechnicianCommand extends Command {

    public static final String COMMAND_WORD = "viewtechnician";

    public static final String MESSAGE_TECHNICIAN_NOT_FOUND = "Technician %d not in system";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display technician details given the customer id."
            + "Parameters: ID\n"
            + "Example: " + COMMAND_WORD + " 8";

    private final int technicianId;

    public ViewTechnicianCommand(int technicianId) {
        this.technicianId = technicianId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasTechnician(this.technicianId)) {
            throw new CommandException(String.format(MESSAGE_TECHNICIAN_NOT_FOUND, this.technicianId));
        }
        model.updateFilteredTechnicianList(t -> t.getId() == this.technicianId);
        Technician current = model.getFilteredTechnicianList().get(0);
        model.selectTechnician(current);
        return new CommandResult(
                String.format(Messages.MESSAGE_CUSTOMER_VIEW_OVERVIEW, this.technicianId, Tab.TECHNICIANS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTechnicianCommand // instanceof handles nulls
                && this.technicianId == ((ViewTechnicianCommand) other).technicianId); // state check
    }
}
