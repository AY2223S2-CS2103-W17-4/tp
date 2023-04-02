package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERNAL_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VEHICLE_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SERVICES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VEHICLES;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.service.PartMap;
import seedu.address.model.service.Service;
import seedu.address.model.service.ServiceStatus;
import seedu.address.model.service.Vehicle;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditServiceCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "editservice";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the service identified "
            + "by the id number displayed by listservice. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_INTERNAL_ID + "SERVICE_INDEX "
            + "[" + PREFIX_VEHICLE_ID + " VEHICLE ID]"
            + "[" + PREFIX_SERVICE_START + " SERVICE START DATE]"
            + "[" + PREFIX_SERVICE_END + " SERVICE END DATE]"
            + "[" + PREFIX_SERVICE_STATUS + " STATUS]"
            + "[" + PREFIX_SERVICE_DESCRIPTION + " DESCRIPTION]"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_VEHICLE_ID + "10 "
            + PREFIX_SERVICE_DURATION + "8 "
            + PREFIX_SERVICE_STATUS + "in progress "
            + PREFIX_SERVICE_DESCRIPTION + "Customer says abc";

    public static final String MESSAGE_EDIT_SERVICE_SUCCESS = "Edited service: %1$s";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_VEHICLE_NOT_FOUND = "Vehicle %d does not exist";

    public static final String MESSAGE_SERVICE_NOT_FOUND = "Service %d does not exist.";
    private static final Service SERVICE_DOES_NOT_EXIST = null;
    private final EditServiceDescriptor editServiceDescriptor;

    /**
     * @param editServiceDescriptor details to edit the person with
     */
    public EditServiceCommand(EditServiceDescriptor editServiceDescriptor) {
        requireNonNull(editServiceDescriptor);
        this.editServiceDescriptor = new EditServiceDescriptor(editServiceDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Service> lastShownList = model.getFilteredServiceList();

        // Locate entry containing id. By right each ID is unique.
        Service serviceToEdit = lastShownList.stream().filter(service ->
                        editServiceDescriptor.getId() == service.getId()).findAny()
                .orElse(SERVICE_DOES_NOT_EXIST);

        if (serviceToEdit == null) {
            throw new CommandException(String.format(MESSAGE_SERVICE_NOT_FOUND, this.editServiceDescriptor.getId()));
        }

        Service editService = createEditedService(serviceToEdit, editServiceDescriptor);

        if (!model.hasVehicle(editService.getVehicleId())) {
            throw new CommandException(String.format(MESSAGE_VEHICLE_NOT_FOUND, editService.getVehicleId()));
        }

        if (serviceToEdit.getVehicleId() != editService.getVehicleId()) {
            Vehicle prevVehicle = model.getFilteredVehicleList().stream()
                .filter(v -> v.getId() == serviceToEdit.getVehicleId())
                .findFirst().orElseThrow();
            Vehicle newVehicle = model.getFilteredVehicleList().stream()
                .filter(v -> v.getId() == editService.getVehicleId())
                .findFirst().orElseThrow();
            prevVehicle.removeService(serviceToEdit);
            newVehicle.addService(editService);
        }

        model.setService(serviceToEdit, editService);
        model.selectService(editService);
        model.updateFilteredServiceList(PREDICATE_SHOW_ALL_SERVICES);
        model.updateFilteredVehicleList(PREDICATE_SHOW_ALL_VEHICLES);
        return new CommandResult(String.format(MESSAGE_EDIT_SERVICE_SUCCESS, editService), Tab.SERVICES);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Service createEditedService(Service serviceToEdit, EditServiceDescriptor editServiceDescriptor) {
        assert serviceToEdit != null;

        int id = serviceToEdit.getId();

        int vehicleId = editServiceDescriptor.getVehicleId().orElse(serviceToEdit.getVehicleId());
        LocalDate entryDate = editServiceDescriptor.getEntryDate().orElse(serviceToEdit.getEntryDate());
        PartMap requiredParts = serviceToEdit.getRequiredParts();
        String description = editServiceDescriptor.getDescription().orElse(serviceToEdit.getDescription());
        LocalDate estimatedFinishDate = editServiceDescriptor.getFinishDate().orElse(
                serviceToEdit.getEstimatedFinishDate());
        Set<Integer> assignedToIds = serviceToEdit.getAssignedToIdsSet();
        ServiceStatus status = editServiceDescriptor.getStatus().orElse(serviceToEdit.getStatus());

        return new Service(id, vehicleId, entryDate, requiredParts, description, estimatedFinishDate, status,
                assignedToIds);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditServiceCommand)) {
            return false;
        }

        // state check
        EditServiceCommand e = (EditServiceCommand) other;
        return editServiceDescriptor.equals(e.editServiceDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditServiceDescriptor {

        private int id;
        private Integer vehicleId;
        private LocalDate entryDate;
        private PartMap requiredParts;
        private String description;
        private LocalDate estimatedFinishDate;
        private Set<Integer> assignedToIds;

        private ServiceStatus status;

        public EditServiceDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditServiceDescriptor(EditServiceDescriptor toCopy) {
            // I've a feeling PartMap and Assigned are always null since no input.
            setId(toCopy.id);
            setVehicleId(toCopy.vehicleId);
            setEntryDate(toCopy.entryDate);
            setRequiredParts(toCopy.requiredParts);
            setDescription(toCopy.description);
            setFinishDate(toCopy.estimatedFinishDate);
            setAssignedToIds(toCopy.assignedToIds);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(vehicleId, entryDate, requiredParts, description, estimatedFinishDate,
                    assignedToIds, status);
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setVehicleId(Integer id) {
            this.vehicleId = id;
        }

        public Optional<Integer> getVehicleId() {
            return Optional.ofNullable(vehicleId);
        }

        public void setEntryDate(LocalDate entryDate) {
            this.entryDate = entryDate;
        }

        public Optional<LocalDate> getEntryDate() {
            return Optional.ofNullable(entryDate);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setFinishDate(LocalDate estimatedFinishDate) {
            this.estimatedFinishDate = estimatedFinishDate;
        }

        public Optional<LocalDate> getFinishDate() {
            return Optional.ofNullable(estimatedFinishDate);
        }


        public void setStatus(ServiceStatus status) {
            this.status = status;
        }

        public Optional<ServiceStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setAssignedToIds(Set<Integer> ids) {
            this.assignedToIds = (ids != null) ? new HashSet<>(ids) : null;
        }

        public Optional<Set<Integer>> getAssignedToIds() {
            return (assignedToIds != null) ? Optional.of(Collections.unmodifiableSet(assignedToIds)) : Optional.empty();
        }

        public void setRequiredParts(PartMap parts) {
            this.requiredParts = parts;
        }

        public PartMap getRequiredParts() {
            return requiredParts;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditServiceDescriptor)) {
                return false;
            }

            // state check
            EditServiceDescriptor e = (EditServiceDescriptor) other;

            return getId() == e.getId() //not sure if need this id checking
                    && getVehicleId().equals(e.getVehicleId())
                    && getEntryDate().equals(e.getEntryDate())
                    && getRequiredParts().equals(e.getRequiredParts())
                    && getFinishDate().equals(e.getFinishDate())
                    && getAssignedToIds().equals(e.getAssignedToIds())
                    && getDescription().equals(e.getDescription())
                    && getStatus().equals(e.getStatus());
        }
    }
}
