package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICIAN_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Manages the adding of a technician to a specific service
 */
public class AddTechnicianToServiceCommand extends RedoableCommand {
    public static final String COMMAND_WORD = "addservicetech";
    public static final String MESSAGE_TECHNICIAN_NOT_FOUND = "Technician %d does not exist";
    public static final String MESSAGE_SERVICE_NOT_FOUND = "Service %d does not exist";
    public static final String MESSAGE_SUCCESS_FORMAT = "Technician %d added to Service %d";
    public static final String COMMAND_USAGE =
        COMMAND_WORD + ": Assigns an existing technician to an existing service. "
            + "Parameters: "
            + PREFIX_TECHNICIAN_ID + "TECHNICIAN_ID "
            + PREFIX_SERVICE_ID + "SERVICE_ID "
            + "Example Usage: "
            + PREFIX_TECHNICIAN_ID + "2 "
            + PREFIX_SERVICE_ID + "3";

    private final int techId;
    private final int serviceId;

    /**
     * @param techId    ID of technician
     * @param serviceId ID of service
     */
    public AddTechnicianToServiceCommand(int techId, int serviceId) {
        this.techId = techId;
        this.serviceId = serviceId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        if (!model.hasTechnician(this.techId)) {
            throw new CommandException(String.format(MESSAGE_TECHNICIAN_NOT_FOUND, this.techId));
        }
        if (!model.hasService(this.serviceId)) {
            throw new CommandException(String.format(MESSAGE_SERVICE_NOT_FOUND, this.serviceId));
        }
        model.addTechnicianToService(this.serviceId, this.techId);
        return new CommandResult(String.format(MESSAGE_SUCCESS_FORMAT, this.techId, this.serviceId),
                Tab.SERVICES);
    }
}
