package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PART_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.service.Service;

/**
 * Manages adding of parts to services
 */
public class RemovePartFromServiceCommand extends RedoableCommand {
    public static final String COMMAND_WORD = "removeservicepart";
    public static final String MESSAGE_SUCCESS_FORMAT = "%s (x %d) removed from service";
    public static final String MESSAGE_SERVICE_NOT_FOUND = "Service does not exist";
    public static final String MESSAGE_PART_NOT_FOUND = "Part not in system";
    public static final String MESSAGE_INSUFFICIENT_PART = "Not enough parts to remove from service";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Removes parts reserved for service and puts it back into global part storage. "
        + "Parameters: "
        + PREFIX_SERVICE_ID + "SERVICE ID "
        + PREFIX_PART_NAME + "PART NAME "
        + PREFIX_QUANTITY + "PART QUANTITY "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_SERVICE_ID + "1 "
        + PREFIX_PART_NAME + "Cylinder Head "
        + PREFIX_QUANTITY + "50";

    private final int serviceId;
    private final String partName;
    private final int quantity;

    /**
     * @param serviceId ID of service
     * @param partName  Name of part
     * @param quantity  Quantity of part
     */
    public RemovePartFromServiceCommand(int serviceId, String partName, int quantity) {
        this.serviceId = serviceId;
        this.partName = partName;
        this.quantity = quantity;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        if (!model.hasService(this.serviceId)) {
            throw new CommandException(MESSAGE_SERVICE_NOT_FOUND);
        }
        if (!model.getPartMap().contains(this.partName)) {
            throw new CommandException(MESSAGE_PART_NOT_FOUND);
        }
        Service service = model.getFilteredServiceList().stream()
            .filter(s -> s.getId() == this.serviceId)
            .findFirst()
            .orElseThrow();
        if (service.getRequiredParts().getPartQuantity(this.partName) < this.quantity) {
            throw new CommandException(MESSAGE_INSUFFICIENT_PART);
        }
        service.getRequiredParts().decreasePartQuantity(this.partName, this.quantity);
        model.resetMaps();
        return new CommandResult(String.format(MESSAGE_SUCCESS_FORMAT, this.partName, this.quantity,
                this.serviceId), Tab.SERVICES);
    }
}
