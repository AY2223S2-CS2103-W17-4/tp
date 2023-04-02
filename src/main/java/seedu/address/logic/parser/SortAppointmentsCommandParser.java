package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.util.SharedComparatorsUtil;
import seedu.address.logic.commands.SortAppointmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.service.appointment.Appointment;

/**
 * Parses input arguments and creates new SortAppointmentCommand object
 */
public class SortAppointmentsCommandParser implements Parser<SortAppointmentsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortAppointmentCommand
     * and returns an SortAppointmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortAppointmentsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_SORT_BY, PREFIX_REVERSE_SORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_BY)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortAppointmentsCommand.COMMAND_USAGE));
        }

        String sortAttribute = argMultimap.getValue(PREFIX_SORT_BY).get();

        Optional<Comparator<Appointment>> cmpOption = generateComparator(sortAttribute);
        if (cmpOption.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortAppointmentsCommand.COMMAND_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_REVERSE_SORT)) {
            return new SortAppointmentsCommand(cmpOption.get().reversed());
        }
        return new SortAppointmentsCommand(cmpOption.get());
    }

    private Optional<Comparator<Appointment>> generateComparator(String input) {
        switch (input.toLowerCase()) {
        case "id":
            return Optional.of(Comparator.comparing(Appointment::getId));
        case "customer id":
            return Optional.of(Comparator.comparing(Appointment::getCustomerId));
        case "date":
            return Optional.of(Comparator.comparing(Appointment::getTimeDate));
        case "date status":
            return Optional.of(SharedComparatorsUtil.getDefaultAppointmentSort());
        default:
            return Optional.empty();
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
