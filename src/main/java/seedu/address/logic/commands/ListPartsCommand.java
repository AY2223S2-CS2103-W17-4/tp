package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all customers in the AutoM8 system to the user.
 */
public class ListPartsCommand extends Command {

    public static final String COMMAND_WORD = "listparts";

    public static final String MESSAGE_SUCCESS = "Currently listing all parts.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, Tab.PARTS);
    }
}










