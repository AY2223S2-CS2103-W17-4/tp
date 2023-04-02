package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.idgen.IdGenerator;
import seedu.address.model.Model;
import seedu.address.model.service.appointment.Appointment;

/**
 * Deletes a vehicle identified using it's displayed index from viewvehicle.
 */
public class DeleteAppointmentCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "deleteappointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment identified by the id number displayed by the list command.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment: %1$s";
    public static final String MESSAGE_APPOINTMENT_NOT_FOUND = "Appointment not found";


    private final int id;

    public DeleteAppointmentCommand(int id) {
        this.id = id;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        Appointment appointmentToDelete = lastShownList.stream().filter(appointment ->
                id == appointment.getId()).findFirst().orElse(null);

        if (appointmentToDelete == null) {
            throw new CommandException(MESSAGE_APPOINTMENT_NOT_FOUND);
        }

        model.deleteAppointment(appointmentToDelete);
        IdGenerator.setAppointmentIdUnused(id);
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointmentToDelete),
                Tab.APPOINTMENTS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAppointmentCommand // instanceof handles nulls
                && id == ((DeleteAppointmentCommand) other).id); // state check
    }
}
