package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortCustomersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.person.Customer;

/**
 * Parses input arguments and creates new SortCustomerCommand object
 */
public class SortCustomersCommandParser implements Parser<SortCustomersCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCustomerCommand
     * and returns an SortCustomerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCustomersCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT_BY, PREFIX_REVERSE_SORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_BY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCustomersCommand.COMMAND_USAGE));
        }

        String sortAttribute = argMultimap.getValue(PREFIX_SORT_BY).get();

        Optional<Comparator<Customer>> cmpOption = generateComparator(sortAttribute);
        if (cmpOption.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCustomersCommand.COMMAND_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_REVERSE_SORT)) {
            return new SortCustomersCommand(cmpOption.get().reversed());
        }
        return new SortCustomersCommand(cmpOption.get());
    }

    private Optional<Comparator<Customer>> generateComparator(String input) {
        switch (input.toLowerCase()) {
        case "id":
            return Optional.of(Comparator.comparing(Customer::getId));
        case "name":
            return Optional.of(Comparator.comparing(x -> x.getName().toString()));
        case "email":
            return Optional.of(Comparator.comparing(x -> x.getEmail().toString()));
        case "address":
            return Optional.of(Comparator.comparing(x -> x.getAddress().toString()));
        case "phone":
            return Optional.of(Comparator.comparing(x -> x.getPhone().toString()));
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
