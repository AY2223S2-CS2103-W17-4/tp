package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Technician;

/**
 * Manages Sorting of technicians
 */
public class SortTechniciansCommand extends Command {
    public static final String COMMAND_WORD = "sorttechnicians";
    public static final String MESSAGE_SUCCESS = "Sorted technicians";
    public static final String COMMAND_USAGE = COMMAND_WORD + ": Sorts technicians by attribute. "
        + "Parameters: "
        + PREFIX_SORT_BY + "[id | name | phone | email | address] "
        + "Optional: "
        + PREFIX_REVERSE_SORT;

    private final Comparator<Technician> cmp;

    public SortTechniciansCommand(Comparator<Technician> cmp) {
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
        model.updateTechnicianComparator(cmp);
        return new CommandResult(MESSAGE_SUCCESS, Tab.TECHNICIANS);
    }
}
