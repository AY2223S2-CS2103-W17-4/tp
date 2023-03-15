package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Person;


/**
 * Jackson-friendly version of {@link Customer}.
 */
class JsonAdaptedCustomer extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Customer's %s field is missing!";

    private final int id;
    private final List<Integer> vehicleIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedCustomer(@JsonProperty("id") int id, @JsonProperty("name") String name,
                               @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                               @JsonProperty("address") String address,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                               @JsonProperty("vehicleIds") List<Integer> vehicleIds) {
        super(name, phone, email, address, tagged);
        this.id = id;
        if (vehicleIds != null) {
            this.vehicleIds.addAll(vehicleIds);
        }
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedCustomer(Customer source) {
        super(source);
        this.id = source.getId();
        vehicleIds.addAll(source.getVehicleIds().stream()
                .map(Integer::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public Customer toModelType() throws IllegalValueException {
        Person p = super.toModelType();
        final List<Integer> customerVehicleIds = new ArrayList<>();
        for (Integer id : vehicleIds) {
            customerVehicleIds.add(id);
        }

        if (id == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }
        final int id = this.id;

        final Set<Integer> modelVehicleIds = new HashSet<>(customerVehicleIds);

        return new Customer(id, p.getName(), p.getPhone(), p.getEmail(), p.getAddress(), p.getTags(), modelVehicleIds);
    }

}
