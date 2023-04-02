package seedu.address.model.entity.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * A technician is a special type of staff, in which they handle the works on the vehicle.
 */
public class Technician extends Staff {
    private final Set<Integer> serviceIds = new HashSet<>();
    private final Set<Integer> appointmentIds = new HashSet<>();
    /**
     * {@inheritDoc}
     */
    public Technician(int id, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(id, name, phone, email, address, tags);
    }

    /**
     * {@inheritDoc}
     */
    public Technician(int id, Name name, Phone phone, Email email, Address address,
                      Set<Tag> tags, Set<Integer> serviceIds, Set<Integer> appointmentIds) {
        super(id, name, phone, email, address, tags);
        this.serviceIds.addAll(serviceIds);
        this.appointmentIds.addAll(appointmentIds);
    }

    /**
     * This method returns a list of service ids this technician is assigned to.
     *
     * @return a list of services this technician is assigned to.
     */
    public List<Integer> getServiceIds() {
        return new ArrayList<>(this.serviceIds);
    }

    /**
     * This method returns a list of appointment ids this technician is assigned to.
     *
     * @return a list of appointment this technician is assigned to.
     */
    public List<Integer> getAppointmentIds() {
        return new ArrayList<>(this.appointmentIds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Technician) {
            Technician other = (Technician) obj;
            return this.getId() == other.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
