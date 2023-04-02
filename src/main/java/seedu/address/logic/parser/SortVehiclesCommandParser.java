package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortVehiclesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.service.Vehicle;

/**
 * Parses input arguments and creates new SortVehiclesCommand object
 */
public class SortVehiclesCommandParser implements Parser<SortVehiclesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortVehiclesCommand
     * and returns an SortVehiclesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortVehiclesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT_BY, PREFIX_REVERSE_SORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_BY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortVehiclesCommand.COMMAND_USAGE));
        }

        String sortAttribute = argMultimap.getValue(PREFIX_SORT_BY).get();

        Optional<Comparator<Vehicle>> cmpOption = generateComparator(sortAttribute);
        if (cmpOption.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortVehiclesCommand.COMMAND_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_REVERSE_SORT)) {
            return new SortVehiclesCommand(cmpOption.get().reversed());
        }
        return new SortVehiclesCommand(cmpOption.get());
    }

    private Optional<Comparator<Vehicle>> generateComparator(String input) {
        switch (input.toLowerCase()) {
        case "id":
            return Optional.of(Comparator.comparing(Vehicle::getId));
        case "owner id":
            return Optional.of(Comparator.comparing(Vehicle::getOwnerId));
        case "plate":
            return Optional.of(Comparator.comparing(Vehicle::getPlateNumber));
        case "brand":
            return Optional.of(Comparator.comparing(Vehicle::getBrand));
        case "color":
            return Optional.of(Comparator.comparing(Vehicle::getColor));
        case "type":
            return Optional.of(Comparator.comparing(Vehicle::getType));
        case "service qty":
            return Optional.of(Comparator.comparing(x -> x.getServiceIds().size()));
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
