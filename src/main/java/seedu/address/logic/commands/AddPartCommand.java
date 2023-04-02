package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PART_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Command that manages adding parts
 */
public class AddPartCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "addpart";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a part to the shop. "
            + "Parameters: "
            + PREFIX_PART_NAME + "PART NAME "
            + PREFIX_QUANTITY + "PART QUANTITY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PART_NAME + "Cylinder Head "
            + PREFIX_QUANTITY + "50";

    public static final String MESSAGE_SUCCESS = "New part added: %1$s";
    public static final String MESSAGE_INCREASE_PART_QUANTITY = "%s quantity increased";

    private final String partName;
    private final int quantity;

    /**
     * Constructs command that adds part to the model
     *
     * @param partName Name of the part to add
     * @param quantity Quantity of the part to add
     */
    public AddPartCommand(String partName, int quantity) {
        this.partName = partName;
        this.quantity = quantity;
    }

    /**
     * Execution of command
     *
     * @param model {@code Model} which the command should operate on.
     * @return Result of command execution
     * @throws CommandException If error occurs during command execution
     */
    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasPart(partName)) {
            model.addPart(partName, quantity);
            return new CommandResult(String.format(MESSAGE_INCREASE_PART_QUANTITY, partName));
        }

        model.addPart(partName, quantity);
        return new CommandResult(String.format(MESSAGE_SUCCESS, partName), Tab.PARTS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPartCommand // instanceof handles nulls
                && partName.equalsIgnoreCase((((AddPartCommand) other).partName)));
    }
}
