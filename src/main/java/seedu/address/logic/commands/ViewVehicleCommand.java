package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.service.Vehicle;

/**
 * Finds and returns the vehicle details of the provided id.
 */
public class ViewVehicleCommand extends Command {

    public static final String COMMAND_WORD = "viewvehicle";

    public static final String MESSAGE_VEHICLE_NOT_FOUND = "Vehicle %d not in system";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display vehicle details given the vehicle id (not"
            + "vehicle plate).\n"
            + "Parameters: ID\n"
            + "Example: " + COMMAND_WORD + " 8";

    private final int vehicleId;

    public ViewVehicleCommand(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasVehicle(this.vehicleId)) {
            throw new CommandException(String.format(MESSAGE_VEHICLE_NOT_FOUND, this.vehicleId));
        }
        model.updateFilteredVehicleList(v -> v.getId() == this.vehicleId);
        Vehicle current = model.getFilteredVehicleList().get(0);
        model.selectVehicle(current);
        return new CommandResult(
                String.format(Messages.MESSAGE_VEHICLE_VIEW_OVERVIEW, current.getId()),
                Tab.VEHICLES);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewVehicleCommand // instanceof handles nulls
                && vehicleId == ((ViewVehicleCommand) other).vehicleId); // state check
    }
}
