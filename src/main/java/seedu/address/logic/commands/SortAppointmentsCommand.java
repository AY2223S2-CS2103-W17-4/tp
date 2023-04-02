package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.service.appointment.Appointment;

/**
 * Manages Sorting of appointments
 */
public class SortAppointmentsCommand extends Command {
    public static final String COMMAND_WORD = "sortappointments";
    public static final String MESSAGE_SUCCESS = "Sorted appointments";
    public static final String COMMAND_USAGE = COMMAND_WORD + ": Sorts appointments by attribute. "
        + "Parameters: "
        + PREFIX_SORT_BY + "[id | customer id | date | date status] "
        + "Optional: "
        + PREFIX_REVERSE_SORT;

    private final Comparator<Appointment> cmp;

    public SortAppointmentsCommand(Comparator<Appointment> cmp) {
        this.cmp = cmp;
    }
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateAppointmentComparator(cmp);
        return new CommandResult(MESSAGE_SUCCESS, Tab.APPOINTMENTS);
    }
}
