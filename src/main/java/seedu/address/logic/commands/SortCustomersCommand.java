package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Customer;

/**
 * Manages Sorting of customers
 */
public class SortCustomersCommand extends Command {
    public static final String COMMAND_WORD = "sortcustomers";
    public static final String MESSAGE_SUCCESS = "Sorted customers";
    public static final String COMMAND_USAGE = COMMAND_WORD + ": Sorts customers by attribute. "
        + "Parameters: "
        + PREFIX_SORT_BY + "[id | name | email | phone | address] "
        + "Optional: "
        + PREFIX_REVERSE_SORT;

    private final Comparator<Customer> cmp;

    public SortCustomersCommand(Comparator<Customer> cmp) {
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
        model.updateCustomerComparator(cmp);
        return new CommandResult(MESSAGE_SUCCESS, Tab.CUSTOMERS);
    }
}
