package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.idgen.IdGenerator;
import seedu.address.model.Model;
import seedu.address.model.service.appointment.Appointment;

/**
 * Manages adding appointments
 */
public class AddAppointmentCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "addappointment";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds appointment with customer. "
            + "Parameters: "
            + PREFIX_CUSTOMER_ID + "CUSTOMER ID "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER_ID + "5 "
            + PREFIX_DATE + "2023-02-03 "
            + PREFIX_TIME + "17:00";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_CUSTOMER_NOT_FOUND = "Customer not registered";

    private final Appointment toAdd;

    /**
     * Constructs command that adds appointment to the model
     *
     * @param appointment Appointment to be added
     */
    public AddAppointmentCommand(Appointment appointment) {
        this.toAdd = appointment;
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
        if (!model.hasCustomer(toAdd.getCustomerId())) {
            IdGenerator.setAppointmentIdUnused(toAdd.getId());
            throw new CommandException(MESSAGE_CUSTOMER_NOT_FOUND);
        }
        model.addAppointment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), Tab.APPOINTMENTS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAppointmentCommand) other).toAdd));
    }
}
