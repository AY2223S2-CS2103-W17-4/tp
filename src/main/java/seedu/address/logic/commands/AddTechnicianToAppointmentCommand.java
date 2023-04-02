package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICIAN_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Manages the adding of an exiting technician to a specific appointment
 */
public class AddTechnicianToAppointmentCommand extends RedoableCommand {
    public static final String COMMAND_WORD = "addappointmenttech";
    public static final String MESSAGE_TECHNICIAN_NOT_FOUND = "Technician %d does not exist";
    public static final String MESSAGE_APPOINTMENT_NOT_FOUND = "Appointment %d does not exist";
    public static final String MESSAGE_SUCCESS_FORMAT = "Technician %d added to Appointment %d";
    public static final String COMMAND_USAGE =
        COMMAND_WORD + ": Assigns an existing technician to an existing appointment. "
            + "Parameters: "
            + PREFIX_TECHNICIAN_ID + "TECHNICIAN_ID "
            + PREFIX_APPOINTMENT_ID + "APPOINTMENT_ID "
            + "Example Usage: "
            + PREFIX_TECHNICIAN_ID + "2 "
            + PREFIX_APPOINTMENT_ID + "3";

    private final int techId;
    private final int appointmentId;

    /**
     * @param techId    ID of technician
     * @param appointmentId ID of appointment
     */
    public AddTechnicianToAppointmentCommand(int techId, int appointmentId) {
        this.techId = techId;
        this.appointmentId = appointmentId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        if (!model.hasTechnician(this.techId)) {
            throw new CommandException(String.format(MESSAGE_TECHNICIAN_NOT_FOUND, this.techId));
        }
        if (!model.hasAppointment(this.appointmentId)) {
            throw new CommandException(String.format(MESSAGE_APPOINTMENT_NOT_FOUND, this.appointmentId));
        }
        model.addTechnicianToAppointment(this.techId, this.appointmentId);
        return new CommandResult(String.format(MESSAGE_SUCCESS_FORMAT, this.techId, this.appointmentId),
                Tab.APPOINTMENTS);
    }
}
