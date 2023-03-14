package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Customer;

/**
 * Manages adding of customers
 */
public class AddCustomerCommand extends Command {
    public static final String COMMAND_WORD = "addcustomer";
    public static final String MESSAGE_USAGE = AddCommand.MESSAGE_USAGE;
    public static final String MESSAGE_SUCCESS = "New customer added: %1$s";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "This customer already registered";

    private final Customer toAdd;

    /**
     * Constructs command that adds customer to the model
     *
     * @param customer Customer to be added
     */
    public AddCustomerCommand(Customer customer) {
        requireNonNull(customer);
        this.toAdd = customer;
    }

    /**
     * Execution of command
     *
     * @param model {@code Model} which the command should operate on.
     * @return Result of command execution
     * @throws CommandException If error occurs during command execution
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCustomer(toAdd.getId())) {
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        }

        model.addCustomer(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCustomerCommand // instanceof handles nulls
                && toAdd.equals(((AddCustomerCommand) other).toAdd));
    }
}
