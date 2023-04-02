package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortServicesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.service.Service;

/**
 * Parses input arguments and creates new SortServiceCommand object
 */
public class SortServicesCommandParser implements Parser<SortServicesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortServiceCommand
     * and returns an SortServiceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortServicesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT_BY, PREFIX_REVERSE_SORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_BY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortServicesCommand.COMMAND_USAGE));
        }

        String sortAttribute = argMultimap.getValue(PREFIX_SORT_BY).get();

        Optional<Comparator<Service>> cmpOption = generateComparator(sortAttribute);
        if (cmpOption.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortServicesCommand.COMMAND_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_REVERSE_SORT)) {
            return new SortServicesCommand(cmpOption.get().reversed());
        }
        return new SortServicesCommand(cmpOption.get());
    }

    private Optional<Comparator<Service>> generateComparator(String input) {
        switch (input.toLowerCase()) {
        case "id":
            return Optional.of(Comparator.comparing(Service::getId));
        case "vehicle id":
            return Optional.of(Comparator.comparing(Service::getVehicleId));
        case "start date":
            return Optional.of(Comparator.comparing(Service::getEntryDate));
        case "end date":
            return Optional.of(Comparator.comparing(Service::getEstimatedFinishDate));
        case "desc":
            return Optional.of(Comparator.comparing(Service::getDescription));
        case "status":
            return Optional.of(Comparator.comparing(Service::getStatus));
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
