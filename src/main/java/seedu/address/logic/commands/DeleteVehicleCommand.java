package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.idgen.IdGenerator;
import seedu.address.model.Model;
import seedu.address.model.service.Vehicle;

/**
 * Deletes a vehicle identified using it's displayed index from viewvehicle.
 */
public class DeleteVehicleCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "deletevehicle";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the vehicle identified by the id number displayed by the list command.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_VEHICLE_SUCCESS = "Deleted Vehicle: %1$s";

    public static final String MESSAGE_VEHICLE_NOT_FOUND = "Vehicle not in AutoM8";

    private static final Vehicle VEHICLE_DOES_NOT_EXIST = null;

    private final int id;

    public DeleteVehicleCommand(int id) {
        this.id = id;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Vehicle> lastShownList = model.getFilteredVehicleList();

        // Get vehicle based on given id
        Vehicle vehicleToDelete = lastShownList.stream().filter(vehicle->
                id == vehicle.getId()).findFirst().orElse(VEHICLE_DOES_NOT_EXIST);

        if (vehicleToDelete == VEHICLE_DOES_NOT_EXIST) {
            throw new CommandException(MESSAGE_VEHICLE_NOT_FOUND);
        }

        model.deleteVehicle(vehicleToDelete);
        IdGenerator.setVehicleIdUnused(id);
        return new CommandResult(String.format(MESSAGE_DELETE_VEHICLE_SUCCESS, vehicleToDelete), Tab.VEHICLES);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteVehicleCommand // instanceof handles nulls
                && this.id == ((DeleteVehicleCommand) other).id); // state check
    }
}
