package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PART_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.RemovePartFromServiceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.service.PartMap;

/**
 * Parses input arguments and creates new RemovePartFromServiceCommand object
 */
public class RemovePartFromServiceCommandParser implements Parser<RemovePartFromServiceCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemovePartFromServiceCommand
     * and returns an RemovePartFromServiceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemovePartFromServiceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_PART_NAME, PREFIX_QUANTITY, PREFIX_SERVICE_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_PART_NAME, PREFIX_QUANTITY, PREFIX_SERVICE_ID)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePartFromServiceCommand.MESSAGE_USAGE));
        }

        String partName = argMultimap.getValue(PREFIX_PART_NAME).get();
        if (!PartMap.isValidName(partName)) {
            throw new ParseException(PartMap.MESSAGE_CONSTRAINTS);
        }
        int serviceId = ParserUtil.parseInt(argMultimap.getValue(PREFIX_SERVICE_ID).get());
        int qty = ParserUtil.parseInt(argMultimap.getValue(PREFIX_QUANTITY).get());

        return new RemovePartFromServiceCommand(serviceId, partName, qty);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
