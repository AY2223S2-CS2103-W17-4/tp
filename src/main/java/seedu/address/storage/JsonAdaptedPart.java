package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.service.Part;
import seedu.address.model.service.PartType;

/**
 * Jackson-friendly version of {@link Part}.
 */
class JsonAdaptedPart {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Part's %s field is missing!";

    private final int id;
    private final int quantity;
    private final String name;
    private final String description;
    private final int cost;
    private final String type;

    /**
     * Constructs a {@code JsonAdaptedPart} with the given part details.
     */
    @JsonCreator
    public JsonAdaptedPart(@JsonProperty("id") int id, @JsonProperty("quantity") int quantity,
                           @JsonProperty("name") String name, @JsonProperty("description") String description,
                           @JsonProperty("cost") int cost, @JsonProperty("type") String type) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.type = type;
    }

    /**
     * Converts a given {@code Part} into this class for Jackson use.
     */
    public JsonAdaptedPart(Part source) {
        id = source.getId();
        quantity = source.getQuantity();
        name = source.getName();
        description = source.getDescription();
        cost = source.getCost();
        type = source.getType().getValue();
    }

    /**
     * Returns the PartType based on the String input given
     * by iterating through the existing PartType values
     *
     * @return the PartType based on the String input given
     */
    private PartType getPartType(String input) {
        for (PartType p : PartType.values()) {
            if (p.isEqual(input)) {
                return p;
            }
        }
        return PartType.OTHERS;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Part} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted part.
     */
    public Part toModelType() throws IllegalValueException {
        if (id <= 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }
        final int modelId = id;

        if (quantity <= 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Quantity"));
        }
        final int modelQuantity = quantity;

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }
        final String modelName = name;

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description"));
        }
        final String modelDescription = description;

        if (cost <= 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Cost"));
        }
        final int modelCost = cost;

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Type"));
        }
        final PartType modelType = getPartType(type);
        return new Part(modelId, modelQuantity, modelName, modelDescription, modelCost, modelType);
    }


}
