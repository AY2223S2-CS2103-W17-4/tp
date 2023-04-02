package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.service.appointment.Appointment;

/**
 ** Finds the number of appointments on the specified date.
 */
public class TotalAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "totalappointment";

    public static final String MESSAGE_SUCCESS = "Total cars on date xxx displayed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display number of customer appointments given a date."
                                                       + "Parameters: d/DATE"
                                                       + "Example: " + COMMAND_WORD + " "
                                                       + PREFIX_DATE + "2023-02-03";

    private int count = 0;
    private LocalDateTime date;

    /**
     * constructor for TotalAppointmentCommand
     * @param date
     */
    public TotalAppointmentCommand(LocalDateTime date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();
        for (Appointment appointment : lastShownList) {
            if (appointment.isWithinRange(date)) {
                count++;
            }
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_DATE_APPOINTMENT_OVERVIEW, count, date.toLocalDate()), Tab.APPOINTMENTS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                       || (other instanceof TotalAppointmentCommand // instanceof handles nulls
                                   && date.equals(((TotalAppointmentCommand) other).date)); // state check
    }
}
