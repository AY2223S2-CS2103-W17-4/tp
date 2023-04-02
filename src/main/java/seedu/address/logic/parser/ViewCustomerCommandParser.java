package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCustomerCommand object
 */
public class ViewCustomerCommandParser implements Parser<ViewCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCustomerCommand
     * and returns a ViewCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCustomerCommand parse(String args) throws ParseException {

        try {
            int index = ParserUtil.parseInt(args.trim());
            return new ViewCustomerCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCustomerCommand.MESSAGE_USAGE), pe);
        }
    }

}
