package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Customer;

/**
 * Finds and returns the customer details of the provided id.
 */
public class ViewCustomerCommand extends Command {

    public static final String COMMAND_WORD = "viewcustomer";

    public static final String MESSAGE_CUSTOMER_NOT_FOUND = "Customer %d not in system";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display customer details given the customer id."
            + "Parameters: ID\n"
            + "Example: " + COMMAND_WORD + " 8";

    private final int customerId;

    public ViewCustomerCommand(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasCustomer(this.customerId)) {
            throw new CommandException(String.format(MESSAGE_CUSTOMER_NOT_FOUND, this.customerId));
        }
        model.updateFilteredCustomerList(c -> c.getId() == this.customerId);
        Customer current = model.getFilteredCustomerList().get(0);
        model.selectCustomer(current);
        return new CommandResult(
                String.format(Messages.MESSAGE_CUSTOMER_VIEW_OVERVIEW, current.getId()), Tab.CUSTOMERS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCustomerCommand // instanceof handles nulls
                && this.customerId == ((ViewCustomerCommand) other).customerId); // state check
    }
}
