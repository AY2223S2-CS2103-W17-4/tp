package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.idgen.IdGenerator;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Technician;

/**
 * Deletes a vehicle identified using it's displayed index from viewcustomer.
 */
public class DeleteTechnicianCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "deletetechnician";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the technician identified by the id number displayed by the list command.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TECHNICIAN_SUCCESS = "Deleted Technician: %1$s";

    public static final String MESSAGE_TECHNICIAN_NOT_FOUND = "Technician is not registered";

    private static final Technician TECHNICIAN_DOES_NOT_EXIST = null;

    private final int id;

    public DeleteTechnicianCommand(int id) {
        this.id = id;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Technician> lastShownList = model.getFilteredTechnicianList();

        Technician technicianToDelete = lastShownList.stream().filter(technician->
                id == technician.getId()).findFirst().orElse(TECHNICIAN_DOES_NOT_EXIST);

        if (technicianToDelete == TECHNICIAN_DOES_NOT_EXIST) {
            throw new CommandException(MESSAGE_TECHNICIAN_NOT_FOUND);
        }

        model.deleteTechnician(technicianToDelete);
        IdGenerator.setStaffIdUnused(id);
        return new CommandResult(String.format(MESSAGE_DELETE_TECHNICIAN_SUCCESS, technicianToDelete), Tab.TECHNICIANS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTechnicianCommand // instanceof handles nulls
                && id == ((DeleteTechnicianCommand) other).id); // state check
    }
}
