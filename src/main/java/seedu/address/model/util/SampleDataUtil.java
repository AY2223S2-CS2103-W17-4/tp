package seedu.address.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyShop;
import seedu.address.model.entity.person.Address;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Email;
import seedu.address.model.entity.person.Name;
import seedu.address.model.entity.person.Person;
import seedu.address.model.entity.person.Phone;
import seedu.address.model.entity.shop.Shop;
import seedu.address.model.service.Part;
import seedu.address.model.service.PartType;
import seedu.address.model.service.Service;
import seedu.address.model.service.ServiceStatus;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.VehicleType;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"))
        };
    }

    public static Customer[] getSampleCustomers() {
        return new Customer[] {
            new Customer(1, new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("regular"), getIntegerSet(1, 2)),
            new Customer(2, new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("difficult", "new driver"), getIntegerSet(3)),
            new Customer(3, new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new HashSet<>(), getIntegerSet(4, 5)),
            new Customer(4, new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("regular"), getIntegerSet(6)),
            new Customer(5, new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new HashSet<>(), new HashSet<>()),
        };
    }

    public static Vehicle[] getSampleVehicles() {
        return new Vehicle[] {
            new Vehicle(1, 1, "SKA1234A", "Red", "Toyota Corolla", VehicleType.CAR),
            new Vehicle(2, 1, "SGP5678B", "Blue", "Toyota Prius", VehicleType.CAR),
            new Vehicle(3, 2, "SLK9123C", "Grey", "BMW X4", VehicleType.CAR),
            new Vehicle(4, 3, "SBF4567D", "Grey", "Suzuki SV650X ABS", VehicleType.MOTORBIKE),
            new Vehicle(5, 3, "SGC2345F", "Black and White", "Honda Adv 150", VehicleType.MOTORBIKE),
            new Vehicle(5, 4, "SLM5678K", "Dark Blue", "Hyundai Tucson SUV", VehicleType.CAR),
        };
    }

    public static Part[] getSampleParts() {
        return new Part[]{
            new Part(1, 50, "Part A", "", 10, PartType.FRAME),
            new Part(2, 1000, "Part B", "Sample Description for Part B", 1, PartType.BOLT),
            new Part(3, 100, "Part C", "Sample Description for Part C", 100, PartType.HORN),
            new Part(4, 100, "Part D", "", 20, PartType.LIGHT),
            new Part(5, 100, "Part E", "", 80, PartType.WHEELS),
            new Part(6, 50, "Part F", "Sample Description for Part F", 1000, PartType.SUSPENSION),
            new Part(7, 200, "Part G", "", 1600, PartType.GEARBOX),
            new Part(8, 20, "Part H", "", 45, PartType.HEADLAMP),
            new Part(9, 20, "Part I", "", 2000, PartType.STEERING),
            new Part(10, 20, "Part J", "Some other part", 30, PartType.OTHERS),
            new Part(11, 20, "Part K", "Some other part", 55, PartType.OTHERS),
        };
    }

    public static Service[] getSampleServices() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Part[] partsSampleA = new Part[]{
            new Part(5, 4, "Part E", "", 80, PartType.WHEELS),
            new Part(6, 4, "Part F", "Sample Description for Part F", 1000, PartType.SUSPENSION),
        };

        Part[] partsSampleB = new Part[]{
            new Part(7, 200, "Part G", "", 1600, PartType.GEARBOX),
        };

        return new Service[]{
            new Service(1, 1, LocalDate.parse("16/03/2023", dtf),
                new ArrayList<>(Arrays.asList(partsSampleA)), "Wheels can't make it",
                LocalDate.parse("28/03/2023", dtf), ServiceStatus.TO_REPAIR),
            new Service(2, 5, LocalDate.parse("17/02/2023", dtf),
                new ArrayList<>(Arrays.asList(partsSampleB)), "Gearbox dead",
                LocalDate.parse("05/04/2023", dtf), ServiceStatus.TO_REPAIR),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyShop getSampleShop() {
        Shop sampleSh = new Shop();
        for (Customer sampleCustomer : getSampleCustomers()) {
            sampleSh.addCustomer(sampleCustomer);
        }
        for (Vehicle sampleVehicle : getSampleVehicles()) {
            sampleSh.addVehicle(sampleVehicle);
        }
        for (Part samplePart : getSampleParts()) {
            sampleSh.addPart(samplePart);
        }
        for (Service sampleService : getSampleServices()) {
            sampleSh.addService(sampleService);
        }
        return sampleSh;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a integer set containing the list of integers given.
     */
    public static Set<Integer> getIntegerSet(Integer... integers) {
        return Arrays.stream(integers)
                .map(Integer::new)
                .collect(Collectors.toSet());
    }

}
