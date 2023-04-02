package seedu.address.model.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.service.Service;
import seedu.address.model.service.ServiceList;
import seedu.address.model.service.Vehicle;

/**
 * Represents a mapping between vehicles and their associated customer and
 * services.
 */
public class VehicleDataMap {
    private final Map<Vehicle, ServiceList> vehicleToServicesMap = new HashMap<>();
    private final Map<Vehicle, Customer> vehicleToCustomerMap = new HashMap<>();

    /**
     * Constructs a VehicleDataMap object.
     *
     * @param customers The list of customers.
     * @param vehicles  The list of vehicles.
     * @param services  The list of services.
     */
    public VehicleDataMap(List<Vehicle> vehicles, List<Customer> customers,
            List<Service> services) {

        Map<Integer, Customer> idToCustomerMap = new HashMap<>();
        for (Customer customer : customers) {
            idToCustomerMap.put(customer.getId(), customer);
        }

        Map<Integer, Service> idToServiceMap = new HashMap<>();
        for (Service service : services) {
            idToServiceMap.put(service.getId(), service);
        }

        for (Vehicle vehicle : vehicles) {
            ServiceList vehicleServiceList = new ServiceList();
            Customer vehicleCustomer = idToCustomerMap.get(vehicle.getOwnerId());

            if (vehicleCustomer != null) {
                this.vehicleToCustomerMap.put(vehicle, vehicleCustomer);
            }

            for (int serviceId : vehicle.getServiceIds()) {
                Service service = idToServiceMap.get(serviceId);
                if (service != null) {
                    vehicleServiceList.add(service);
                }
            }
            this.vehicleToServicesMap.put(vehicle, vehicleServiceList);
        }
    }

    /**
     * Resets the mapping of vehicles to their associated customer and services
     *
     * @param customers The list of customers.
     * @param vehicles  The list of vehicles.
     * @param services  The list of services.
     */
    public void reset(List<Vehicle> vehicles, List<Customer> customers,
            List<Service> services) {
        this.vehicleToCustomerMap.clear();
        this.vehicleToServicesMap.clear();

        Map<Integer, Customer> idToCustomerMap = new HashMap<>();
        for (Customer customer : customers) {
            idToCustomerMap.put(customer.getId(), customer);
        }

        Map<Integer, Service> idToServiceMap = new HashMap<>();
        for (Service service : services) {
            idToServiceMap.put(service.getId(), service);
        }

        for (Vehicle vehicle : vehicles) {
            ServiceList vehicleServiceList = new ServiceList();
            Customer vehicleCustomer = idToCustomerMap.get(vehicle.getOwnerId());

            if (vehicleCustomer != null) {
                this.vehicleToCustomerMap.put(vehicle, vehicleCustomer);
            }

            for (int serviceId : vehicle.getServiceIds()) {
                Service service = idToServiceMap.get(serviceId);
                if (service != null) {
                    vehicleServiceList.add(service);
                }
            }
            this.vehicleToServicesMap.put(vehicle, vehicleServiceList);
        }
    }

    /**
     * Returns an unmodifiable observable list of services associated with the
     * specified vehicle.
     *
     * @param vehicle The vehicle whose services to retrieve.
     * @return An unmodifiable observable list of vehicles associated with the
     *         specified customer.
     */
    public ObservableList<Service> getVehicleServices(Vehicle vehicle) {
        return this.vehicleToServicesMap.get(vehicle).asUnmodifiableObservableList();
    }

    /**
     * Returns the Customer associated with the specified vehicle.
     *
     * @param vehicle The vehicle whose owner to retrieve.
     * @return The Vehicle owner
     */
    public Customer getVehicleCustomer(Vehicle vehicle) {
        return this.vehicleToCustomerMap.get(vehicle);
    }
}
