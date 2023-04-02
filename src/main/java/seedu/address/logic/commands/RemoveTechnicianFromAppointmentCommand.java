package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICIAN_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.service.appointment.Appointment;

/**
 * Manages the adding of a technician to a specific service
 */
public class RemoveTechnicianFromAppointmentCommand extends RedoableCommand {
    public static final String COMMAND_WORD = "removeappointmenttech";
    public static final String MESSAGE_TECHNICIAN_NOT_FOUND = "Technician not assigned to appointment";
    public static final String MESSAGE_APPOINTMENT_NOT_FOUND = "Appointment does not exist";
    public static final String MESSAGE_SUCCESS_FORMAT = "Technician %d unassigned from Appointment %d";
    public static final String COMMAND_USAGE =
        COMMAND_WORD + ": Unassigns an existing technician from an existing appointment. "
            + "Parameters: "
            + PREFIX_TECHNICIAN_ID + "TECHNICIAN_ID "
            + PREFIX_SERVICE_ID + "SERVICE_ID "
            + "Example Usage: "
            + PREFIX_TECHNICIAN_ID + "2 "
            + PREFIX_SERVICE_ID + "3";

    private final int techId;
    private final int appointmentId;

    /**
     * @param techId    ID of technician
     * @param appointmentId ID of appointment
     */
    public RemoveTechnicianFromAppointmentCommand(int techId, int appointmentId) {
        this.techId = techId;
        this.appointmentId = appointmentId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        if (!model.hasAppointment(this.appointmentId)) {
            throw new CommandException(MESSAGE_APPOINTMENT_NOT_FOUND);
        }
        Appointment appointment = model.getFilteredAppointmentList().stream()
            .filter(a -> a.getId() == this.appointmentId)
            .findFirst()
            .orElseThrow();
        if (!appointment.getStaffIds().contains(this.techId)) {
            throw new CommandException(MESSAGE_TECHNICIAN_NOT_FOUND);
        }
        appointment.removeTechnician(this.techId);
        model.resetMaps();
        return new CommandResult(String.format(MESSAGE_SUCCESS_FORMAT, this.techId, this.appointmentId),
                Tab.APPOINTMENTS);
    }
}
