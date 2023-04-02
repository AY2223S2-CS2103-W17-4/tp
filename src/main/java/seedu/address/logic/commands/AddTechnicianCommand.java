package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Technician;

/**
 * Manages adding of technicians
 */
public class AddTechnicianCommand extends AddStaffCommand {
    public static final String COMMAND_WORD = "addtechnician";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a technician to the shop. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "leader "
            + PREFIX_TAG + "leaving 2nd march";
    public static final String MESSAGE_SUCCESS = "New technician added: %1$s";
    public static final String MESSAGE_DUPLICATE_TECHNICIAN = "This technician already registered";


    /**
     * Constructs command that adds technician to the model
     *
     * @param technician Technician to be added
     */
    public AddTechnicianCommand(Technician technician) {
        super(technician);
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
        Technician toAdd = (Technician) this.toAdd;
        if (model.hasTechnician(toAdd.getId())) {
            throw new CommandException(MESSAGE_DUPLICATE_TECHNICIAN);
        }

        model.addTechnician(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), Tab.TECHNICIANS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTechnicianCommand // instanceof handles nulls
                && toAdd.equals(((AddTechnicianCommand) other).toAdd));
    }
}
