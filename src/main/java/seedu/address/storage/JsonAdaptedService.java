package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.service.Part;
import seedu.address.model.service.Service;
import seedu.address.model.service.ServiceStatus;


/**
 * Jackson-friendly version of {@link Service}.
 */
class JsonAdaptedService {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Service's %s field is missing!";

    private final int id;
    private final int vehicleId;
    private final String entryDate;
    private final String description;
    private final String estimatedFinishDate;
    private final String status;
    private final List<JsonAdaptedPart> parts = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedService} with the given service details.
     */
    @JsonCreator
    public JsonAdaptedService(@JsonProperty("id") int id, @JsonProperty("vehicleId") int vehicleId,
                              @JsonProperty("entryDate") String entryDate,
                              @JsonProperty("description") String description,
                              @JsonProperty("estimatedFinishDate") String estimatedFinishDate,
                              @JsonProperty("status") String status,
                              @JsonProperty("parts") List<JsonAdaptedPart> parts) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.entryDate = entryDate;
        this.description = description;
        this.estimatedFinishDate = estimatedFinishDate;
        this.status = status;
        if (parts != null) {
            this.parts.addAll(parts);
        }
    }

    /**
     * Converts a given {@code Service} into this class for Jackson use.
     */
    public JsonAdaptedService(Service source) {
        id = source.getId();
        vehicleId = source.getVehicleId();
        entryDate = source.getEntryDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        description = source.getDescription();
        estimatedFinishDate = source.getEstimatedFinishDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        status = source.getStatus().getValue();
        parts.addAll(source.getRequiredParts().stream()
                .map(JsonAdaptedPart::new)
                .collect(Collectors.toList()));
    }

    /**
     * Returns the ServiceStatus based on the String input given
     * by iterating through the existing ServiceStatus values
     *
     * @return the ServiceStatus based on the String input given
     */
    private ServiceStatus getServiceStatus(String input) {
        for (ServiceStatus s : ServiceStatus.values()) {
            if (s.isEqual(input)) {
                return s;
            }
        }
        return ServiceStatus.ON_HOLD;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Service} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted service.
     */
    public Service toModelType() throws IllegalValueException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final List<Part> modelParts = new ArrayList<>();
        for (JsonAdaptedPart part : parts) {
            modelParts.add(part.toModelType());
        }

        if (id <= 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }
        final int modelId = id;

        if (vehicleId <= 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Vehicle Id"));
        }
        final int modelVehicleId = vehicleId;

        if (entryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Entry Date"));
        }
        final LocalDate modelEntryDate = LocalDate.parse(entryDate, dtf);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description"));
        }
        final String modelDescription = description;

        if (estimatedFinishDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Estimated Finish Date"));
        }
        final LocalDate modelEstimatedFinishDate = LocalDate.parse(estimatedFinishDate, dtf);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Status"));
        }
        final ServiceStatus modelStatus = getServiceStatus(status);
        return new Service(modelId, modelVehicleId, modelEntryDate, modelParts, modelDescription,
                modelEstimatedFinishDate, modelStatus);
    }


}
