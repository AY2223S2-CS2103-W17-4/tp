package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVAID_PART_REQUESTED;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.service.PartMap;

/**
 * Deletes a part identified using it's displayed name from viewpart or listparts.
 * This only deletes from the global list, and does not affect each appointment individually.
 */
public class DeletePartCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "deletepart";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the part identified by the part name.\n"
            + "Parameters: STRING\n"
            + "Example: " + COMMAND_WORD + " CS-2103-T";

    public static final String MESSAGE_DELETE_PART_SUCCESS = "Deleted Part: %1$s";

    private final String userString;

    public DeletePartCommand(String userString) {
        this.userString = userString;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        PartMap pm = model.getPartMap();

        if (pm.contains(userString) == false) {
            throw new CommandException(String.format(MESSAGE_INVAID_PART_REQUESTED, DeletePartCommand.MESSAGE_USAGE));
        } else {
            pm.removePart(userString);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PART_SUCCESS, userString), Tab.PARTS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePartCommand // instanceof handles nulls
                && userString.equals(((DeletePartCommand) other).userString)); // state check
    }
}
