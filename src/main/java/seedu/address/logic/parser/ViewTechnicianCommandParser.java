package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewTechnicianCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewVehicleCommand object
 */
public class ViewTechnicianCommandParser implements Parser<ViewTechnicianCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewVehicleCommand
     * and returns a ViewVehicleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTechnicianCommand parse(String args) throws ParseException {

        try {
            int index = ParserUtil.parseInt(args);
            return new ViewTechnicianCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTechnicianCommand.MESSAGE_USAGE), pe);
        }
    }

}
