package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICIAN_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.service.Service;

/**
 * Manages the adding of a technician to a specific service
 */
public class RemoveTechnicianFromServiceCommand extends RedoableCommand {
    public static final String COMMAND_WORD = "removeservicetech";
    public static final String MESSAGE_TECHNICIAN_NOT_FOUND = "Technician not assigned to service";
    public static final String MESSAGE_SERVICE_NOT_FOUND = "Service does not exist";
    public static final String MESSAGE_SUCCESS_FORMAT = "Technician %d unassigned from Service %d";
    public static final String COMMAND_USAGE =
        COMMAND_WORD + ": Unassigns an existing technician from an existing service. "
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
    public RemoveTechnicianFromServiceCommand(int techId, int serviceId) {
        this.techId = techId;
        this.serviceId = serviceId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        if (!model.hasService(this.serviceId)) {
            throw new CommandException(MESSAGE_SERVICE_NOT_FOUND);
        }
        Service service = model.getFilteredServiceList().stream()
            .filter(s -> s.getId() == this.serviceId)
            .findFirst()
            .orElseThrow();
        if (!service.getAssignedToIds().contains(this.techId)) {
            throw new CommandException(MESSAGE_TECHNICIAN_NOT_FOUND);
        }
        Technician technician = model.getFilteredTechnicianList().stream()
            .filter(t -> t.getId() == this.techId)
            .findFirst()
            .orElseThrow();
        service.removeTechnician(technician);
        model.resetMaps();
        return new CommandResult(String.format(MESSAGE_SUCCESS_FORMAT, this.techId, this.serviceId),
                Tab.SERVICES);
    }
}
