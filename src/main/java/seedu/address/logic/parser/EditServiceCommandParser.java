package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERNAL_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VEHICLE_ID;

import seedu.address.logic.commands.EditServiceCommand;
import seedu.address.logic.commands.EditServiceCommand.EditServiceDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditServiceCommandParser implements Parser<EditServiceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditServiceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_INTERNAL_ID, PREFIX_VEHICLE_ID, PREFIX_SERVICE_START, PREFIX_SERVICE_END,
                PREFIX_SERVICE_STATUS, PREFIX_SERVICE_DESCRIPTION);


        // If no id present, don't know what to edit, throw error.
        if (!argMultimap.getValue(PREFIX_INTERNAL_ID).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditServiceCommand.MESSAGE_USAGE));
        }

        EditServiceDescriptor editServiceDescriptor = new EditServiceDescriptor();

        editServiceDescriptor.setId(ParserUtil.parseInt(argMultimap.getValue(PREFIX_INTERNAL_ID).get()));

        if (argMultimap.getValue(PREFIX_VEHICLE_ID).isPresent()) {
            editServiceDescriptor.setVehicleId(ParserUtil.parseInt(argMultimap.getValue(PREFIX_VEHICLE_ID).get()));
        }

        if (argMultimap.getValue(PREFIX_SERVICE_START).isPresent()) {
            editServiceDescriptor.setEntryDate(ParserUtil.parseDate(
                    argMultimap.getValue(PREFIX_SERVICE_START).get()));
        }

        if (argMultimap.getValue(PREFIX_SERVICE_END).isPresent()) {
            editServiceDescriptor.setFinishDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_SERVICE_END).get()));
        }

        if (argMultimap.getValue(PREFIX_SERVICE_STATUS).isPresent()) {
            editServiceDescriptor.setStatus(ParserUtil.parseServiceStatus(
                    argMultimap.getValue(PREFIX_SERVICE_STATUS).get()));
        }

        if (argMultimap.getValue(PREFIX_SERVICE_DESCRIPTION).isPresent()) {
            editServiceDescriptor.setDescription(ParserUtil.parseString(
                    argMultimap.getValue(PREFIX_SERVICE_DESCRIPTION).get()));
        }

        if (!editServiceDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditServiceCommand.MESSAGE_NOT_EDITED);
        }

        return new EditServiceCommand(editServiceDescriptor);
    }

}
