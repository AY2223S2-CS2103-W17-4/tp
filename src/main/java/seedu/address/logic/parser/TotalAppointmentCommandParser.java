package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.TotalAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new TotalAppointmentCommand object
 */
public class TotalAppointmentCommandParser implements Parser<TotalAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TotalAppointment
     * and returns a TotalAppointment object for execution.
     * @param args
     * @throws ParseException
     */
    public TotalAppointmentCommand parse(String args) throws ParseException {

        // eg. totalappointment d/2023-02-03
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TotalAppointmentCommand.MESSAGE_USAGE));
        }

        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        LocalTime time = ParserUtil.parseTime("00:00");
        LocalDateTime dateTime = date.atTime(time);

        return new TotalAppointmentCommand(dateTime);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
