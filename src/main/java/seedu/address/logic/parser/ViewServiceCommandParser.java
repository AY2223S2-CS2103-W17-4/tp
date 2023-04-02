package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewServiceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewServiceCommand object
 */
public class ViewServiceCommandParser implements Parser<ViewServiceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewServiceCommand
     * and returns a ViewServiceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewServiceCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseInt(args.trim());
            return new ViewServiceCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewServiceCommand.MESSAGE_USAGE), pe);
        }
    }

}
