package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICIAN_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTechnicianToAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates new AddTechnicianToAppointmentCommand object
 */
public class AddTechnicianToAppointmentCommandParser implements Parser<AddTechnicianToAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTechnicianToAppointmentCommand
     * and returns an AddTechnicianToAppointmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTechnicianToAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TECHNICIAN_ID, PREFIX_APPOINTMENT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_TECHNICIAN_ID, PREFIX_APPOINTMENT_ID)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTechnicianToAppointmentCommand.COMMAND_USAGE));
        }

        int techId = ParserUtil.parseInt(argMultimap.getValue(PREFIX_TECHNICIAN_ID).get());
        int appointmentId = ParserUtil.parseInt(argMultimap.getValue(PREFIX_APPOINTMENT_ID).get());

        return new AddTechnicianToAppointmentCommand(techId, appointmentId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
