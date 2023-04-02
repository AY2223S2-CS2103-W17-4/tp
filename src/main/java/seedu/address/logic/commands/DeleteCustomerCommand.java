package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.idgen.IdGenerator;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Customer;

/**
 * Deletes a vehicle identified using it's displayed index from viewcustomer.
 */
public class DeleteCustomerCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "deletecustomer";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the customer identified by the id number displayed by the list command.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CUSTOMER_SUCCESS = "Deleted Customer: %1$s";

    public static final String MESSAGE_CUSTOMER_NOT_FOUND = "Customer is not registered";

    private static final Customer CUSTOMER_DOES_NOT_EXIST = null;

    private final int id;

    public DeleteCustomerCommand(int id) {
        this.id = id;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        Customer customerToDelete = lastShownList.stream().filter(customer->
                id == customer.getId()).findFirst().orElse(CUSTOMER_DOES_NOT_EXIST);

        if (customerToDelete == CUSTOMER_DOES_NOT_EXIST) {
            throw new CommandException(MESSAGE_CUSTOMER_NOT_FOUND);
        }

        model.deleteCustomer(customerToDelete);
        IdGenerator.setCustomerIdUnused(id);
        return new CommandResult(String.format(MESSAGE_DELETE_CUSTOMER_SUCCESS, customerToDelete), Tab.CUSTOMERS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCustomerCommand // instanceof handles nulls
                && id == ((DeleteCustomerCommand) other).id); // state check
    }
}
