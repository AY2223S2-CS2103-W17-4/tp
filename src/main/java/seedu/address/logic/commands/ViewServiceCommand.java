package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.service.Service;

/**
 * Finds and returns the service details of the provided id.
 */
public class ViewServiceCommand extends Command {

    public static final String COMMAND_WORD = "viewservice";

    public static final String MESSAGE_SERVICE_NOT_FOUND = "Service %d not in system";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display service details given the service id. "
            + "Parameters: ID\n"
            + "Example: " + COMMAND_WORD + " 8";

    private final int serviceId;

    public ViewServiceCommand(int serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasService(this.serviceId)) {
            throw new CommandException(String.format(MESSAGE_SERVICE_NOT_FOUND, this.serviceId));
        }
        model.updateFilteredServiceList(s -> s.getId() == this.serviceId);
        Service current = model.getFilteredServiceList().get(0);
        model.selectService(current);
        return new CommandResult(
                String.format(Messages.MESSAGE_SERVICE_VIEW_OVERVIEW, current.getId()),
                Tab.SERVICES);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewServiceCommand // instanceof handles nulls
                && this.serviceId == ((ViewServiceCommand) other).serviceId); // state check
    }
}
