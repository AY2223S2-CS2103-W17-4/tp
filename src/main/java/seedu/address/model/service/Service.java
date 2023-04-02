package seedu.address.model.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.Findable;
import seedu.address.model.entity.person.Technician;

/**
 * The Service class contains information about what task to be performed on the vehicle.
 */
public class Service implements Findable {
    public static final int DEFAULT_SEVEN_DAYS = 7;
    private final int id;
    private final int vehicleId;
    private final LocalDate entryDate;
    private final PartMap requiredParts = new PartMap();
    private final String description;
    private LocalDate estimatedFinishDate;
    private final Set<Integer> assignedToIds = new HashSet<>();

    private ServiceStatus status;

    /**
     * This method is the constructor for a Service.
     */
    public Service(int id, int vehicleId, LocalDate entryDate,
                   PartMap requiredParts, String description,
                   LocalDate estimatedFinishDate, ServiceStatus status,
                   Set<Integer> assignedToIds) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.entryDate = entryDate;
        this.description = description;
        this.estimatedFinishDate = estimatedFinishDate;
        this.status = status;
        this.requiredParts.addAll(requiredParts);
        this.assignedToIds.addAll(assignedToIds);
    }

    /**
     * This method is the constructor for a Service.
     */
    public Service(int id, int vehicleId, LocalDate entryDate,
                   PartMap requiredParts, String description,
                   LocalDate estimatedFinishDate, ServiceStatus status) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.entryDate = entryDate;
        this.description = description;
        this.estimatedFinishDate = estimatedFinishDate;
        this.status = status;
        this.requiredParts.addAll(requiredParts);
    }

    /**
     * This method is the constructor for a Service.
     *
     * @param id                    The ID of service.
     * @param vehicleId             ID of vehicle that requires service
     * @param estimatedDaysRequired The amount of time estimated to be needed for repairs.
     * @param assignedToIds         The list of staffs ids that this service is assigned to.
     */
    public Service(int id, int vehicleId, int estimatedDaysRequired, Set<Integer> assignedToIds, String description) {
        this.id = id;
        entryDate = LocalDate.now();
        this.vehicleId = vehicleId;
        this.description = description;
        estimatedFinishDate = entryDate.plusDays(estimatedDaysRequired);
        this.assignedToIds.addAll(assignedToIds);
    }

    /**
     * Yet another Service constructor
     */
    public Service(int id, int vehicleId, int serviceDuration, String serviceDesc, ServiceStatus serviceStatus) {
        this(id, vehicleId, serviceDuration, new HashSet<>(), serviceDesc);
        this.status = serviceStatus;
    }

    /**
     * This method returns the id of this service.
     *
     * @return the id of this service.
     */
    public int getId() {
        return id;
    }

    /**
     * Get ID of vehicle that requires service
     *
     * @return Vehicle ID
     */
    public int getVehicleId() {
        return this.vehicleId;
    }

    /**
     * @return Entry date of service
     */
    public LocalDate getEntryDate() {
        return this.entryDate;
    }

    /**
     * This method returns the estimated finish date.
     *
     * @return The estimated finish date.
     */
    public LocalDate getEstimatedFinishDate() {
        return estimatedFinishDate;
    }

    /**
     * This method sets the estimated finish date.
     *
     * @param estimatedFinishDate The estimated finish date.
     */
    public void setEstimatedFinishDate(LocalDate estimatedFinishDate) {
        this.estimatedFinishDate = estimatedFinishDate;
    }

    /**
     * This method returns the PartMap of requiredParts needed to perform this Service.
     *
     * @return PartMap of requiredParts needed to fulfill this service.
     */
    public PartMap getRequiredParts() {
        return requiredParts;
    }

    /**
     * This method adds a part to this service.
     *
     * @param partName Part name
     * @param quantity Quantity of part to add
     */
    public void addPart(String partName, int quantity) {
        requiredParts.addPart(partName, quantity);
    }

    /**
     * Removes a part from this service.
     *
     * @param partName Name of the part to be removed.
     */
    public void removePart(String partName) {
        requiredParts.removePart(partName);
    }


    /**
     * Adds parts needed for this service.
     *
     * @param parts Another PartMap needed to be added to this service.
     */
    public void addParts(PartMap parts) {
        requiredParts.addAll(parts);
    }

    /**
     * This method removes some parts that was added to this service.
     *
     * @param parts The part to be removed.
     */
    public void removeParts(PartMap parts) {
        requiredParts.removeAll();
    }

    /**
     * This method returns the description of this service.
     *
     * @return the description of this service.
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method returns the list of technician ids assigned to this task.
     *
     * @return the list of technician ids assigned to this task.
     */
    public List<Integer> getAssignedToIds() {
        return new ArrayList<>(this.assignedToIds);
    }

    public Set<Integer> getAssignedToIdsSet() {
        return this.assignedToIds;
    }

    /**
     * This method returns the status of this service.
     *
     * @return the status of this service.
     */
    public ServiceStatus getStatus() {
        return status;
    }

    /**
     * This method adds a technician to this service.
     *
     * @param technician The technician assigned to this service.
     */
    public void assignTechnician(Technician technician) {
        this.assignedToIds.add(technician.getId());
    }

    /**
     * Assigns technician ID to this service
     *
     * @param technicianId ID of technician
     */
    public void assignTechnician(int technicianId) {
        this.assignedToIds.add(technicianId);
    }

    /**
     * This method removes a technician from this service.
     *
     * @param technician The technician to be removed from this service.
     */
    public void removeTechnician(Technician technician) {
        this.assignedToIds.remove(technician.getId());
    }

    /**
     * Returns true if both Services have the same id.
     */
    public boolean isSameService(Service otherService) {
        if (otherService == this) {
            return true;
        }

        return otherService != null
                && otherService.getId() == getId();

    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Service) {
            Service otherService = (Service) other;
            return this.getId() == otherService.getId() || super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String newline = System.lineSeparator();
        String parts = this.getRequiredParts().toString();
        String technicians = this.getAssignedToIds().stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + "\n" + b);
        if (technicians.length() > 0) {
            technicians = technicians.substring(1);
        }
        String status = this.status.getValue();
        String formatter = "<<Service>>" + newline
                + "ID: %d" + newline
                + "Desc: %s" + newline
                + "Status: %s" + newline
                + "Entry Date: %s" + newline
                + "Est Finish Date: %s" + newline
                + "Parts Required: %n %s" + newline
                + "Assigned Technicians: %n %s";

        return String.format(formatter,
                this.getId(),
                this.getDescription(),
                this.status,
                this.getEntryDate(),
                this.getEstimatedFinishDate(),
                StringUtil.indent(parts, 2),
                StringUtil.indent(technicians, 2));
    }

    @Override
    public boolean hasKeyword(String keyword) {
        boolean stringMatch = this.status.toString().toLowerCase().contains(keyword)
            || this.description.toLowerCase().contains(keyword);
        try {
            LocalDate date = LocalDate.parse(keyword);
            boolean dateMatch = this.entryDate.equals(date) || this.estimatedFinishDate.equals(date);
            return dateMatch || stringMatch;
        } catch (DateTimeParseException ex) {
            return stringMatch;
        }
    }
}
