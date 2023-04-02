package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.service.Service;

/**
 * Manages Sorting of services
 */
public class SortServicesCommand extends Command {
    public static final String COMMAND_WORD = "sortservices";
    public static final String MESSAGE_SUCCESS = "Sorted services";
    public static final String COMMAND_USAGE = COMMAND_WORD + ": Sorts services by attribute. "
        + "Parameters: "
        + PREFIX_SORT_BY + "[id | vehicle id | start date | end date | desc | status] "
        + "Optional: "
        + PREFIX_REVERSE_SORT;

    private final Comparator<Service> cmp;

    public SortServicesCommand(Comparator<Service> cmp) {
        this.cmp = cmp;
    }
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateServiceComparator(cmp);
        return new CommandResult(MESSAGE_SUCCESS, Tab.SERVICES);
    }
}
