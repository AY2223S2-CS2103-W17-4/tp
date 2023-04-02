package seedu.address.model.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.entity.person.UniqueTechnicianList;
import seedu.address.model.service.Service;
import seedu.address.model.service.Vehicle;

/**
 * Represents a mapping between services and their associated technicians
 */
public class ServiceDataMap {
    private final Map<Service, UniqueTechnicianList> serviceToTechniciansMap = new HashMap<>();
    private final Map<Service, Vehicle> serviceToVehicleMap = new HashMap<>();

    /**
     * Constructs a ServiceDataMap object.
     *
     * @param services  The list of services.
     * @param technicians  The list of technicians.
     * @param vehicles  The list of vehicles.
     */
    public ServiceDataMap(List<Service> services, List<Technician> technicians, List<Vehicle> vehicles) {
        reset(services, technicians, vehicles);
    }

    /**
     * Resets the mapping of services to their associated technicians and vehicle
     *
     * @param services  The list of services.
     * @param technicians  The list of technicians.
     * @param vehicles  The list of vehicles.
     */
    public void reset(List<Service> services, List<Technician> technicians, List<Vehicle> vehicles) {
        this.serviceToVehicleMap.clear();
        this.serviceToTechniciansMap.clear();

        Map<Integer, Technician> idToStaffMap = new HashMap<>();
        for (Technician technician : technicians) {
            idToStaffMap.put(technician.getId(), technician);
        }

        Map<Integer, Vehicle> idToVehicleMap = new HashMap<>();
        for (Vehicle vehicle : vehicles) {
            idToVehicleMap.put(vehicle.getId(), vehicle);
        }

        for (Service service : services) {
            UniqueTechnicianList serviceTechnicianList = new UniqueTechnicianList();
            Vehicle serviceVehicle = idToVehicleMap.get(service.getVehicleId());

            if (serviceVehicle != null) {
                this.serviceToVehicleMap.put(service, serviceVehicle);
            }

            for (int technicianId : service.getAssignedToIds()) {
                Technician technician = idToStaffMap.get(technicianId);
                if (technician != null) {
                    serviceTechnicianList.add(technician);
                }
            }
            this.serviceToTechniciansMap.put(service, serviceTechnicianList);
        }
    }

    /**
     * Returns an unmodifiable observable list of technicians associated with the
     * specified service.
     *
     * @param service The service whose technicians to retrieve.
     * @return An unmodifiable observable list of technicians associated with the
     *         specified service.
     */
    public ObservableList<Technician> getServiceTechnicians(Service service) {
        return this.serviceToTechniciansMap.get(service).asUnmodifiableObservableList();
    }

    /**
     * Returns the Vehicle associated with the specified service.
     *
     * @param service The service whose owner to vehicle.
     * @return The Vehicle to service
     */
    public Vehicle getServiceVehicle(Service service) {
        return this.serviceToVehicleMap.get(service);
    }
}
