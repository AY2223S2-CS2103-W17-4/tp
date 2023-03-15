package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.VehicleType;

/**
 * Jackson-friendly version of {@link seedu.address.model.service.Vehicle}.
 */
class JsonAdaptedVehicle {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Vehicle's %s field is missing!";

    private final int id;
    private final int ownerId;
    private final String plateNumber;
    private final String color;
    private final String brand;
    private final String type;
    private final List<Integer> serviceIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedVehicle} with the given vehicle details.
     */
    @JsonCreator
    public JsonAdaptedVehicle(@JsonProperty("id") int id, @JsonProperty("ownerId") int ownerId,
                              @JsonProperty("plateNumber") String plateNumber, @JsonProperty("color") String color,
                              @JsonProperty("brand") String brand, @JsonProperty("type") String type,
                              @JsonProperty("serviceIds") List<Integer> serviceIds) {
        this.id = id;
        this.ownerId = ownerId;
        this.plateNumber = plateNumber;
        this.color = color;
        this.brand = brand;
        this.type = type;
        if (serviceIds != null) {
            this.serviceIds.addAll(serviceIds);
        }
    }

    /**
     * Converts a given {@code Vehicle} into this class for Jackson use.
     */
    public JsonAdaptedVehicle(Vehicle source) {
        id = source.getId();
        ownerId = source.getOwnerId();
        plateNumber = source.getPlateNumber();
        color = source.getColor();
        brand = source.getBrand();
        type = source.getType().getValue();
        serviceIds.addAll(source.getServiceIds().stream()
                .map(Integer::new)
                .collect(Collectors.toList()));
    }

    /**
     * Returns the VehicleType based on the String input given
     * by iterating through the existing VehicleType values
     *
     * @return the VehicleType based on the String input given
     */
    private VehicleType getVehicleType(String input) {
        for (VehicleType v : VehicleType.values()) {
            if (v.isEqual(input)) {
                return v;
            }
        }
        return VehicleType.CAR;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Vehicle} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted vehicle.
     */
    public Vehicle toModelType() throws IllegalValueException {
        final List<Integer> vehicleServiceIds = new ArrayList<>();
        for (Integer id : serviceIds) {
            vehicleServiceIds.add(id);
        }

        if (id <= 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }
        final int modelId = id;

        if (ownerId <= 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Owner Id"));
        }
        final int modelOwnerId = ownerId;

        if (plateNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Plate Number"));
        }
        final String modelPlateNumber = plateNumber;

        if (color == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Color"));
        }
        final String modelColor = color;

        if (brand == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Brand"));
        }
        final String modelBrand = brand;

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Vehicle type"));
        }
        final VehicleType modelType = getVehicleType(type);
        final Set<Integer> modelServiceIds = new HashSet<>(vehicleServiceIds);
        return new Vehicle(modelId, modelOwnerId, modelPlateNumber, modelColor, modelBrand, modelType, modelServiceIds);
    }


}
