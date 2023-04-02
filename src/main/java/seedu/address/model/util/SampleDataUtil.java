package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.idgen.IdGenerator;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyShop;
import seedu.address.model.entity.person.Address;
import seedu.address.model.entity.person.Customer;
import seedu.address.model.entity.person.Email;
import seedu.address.model.entity.person.Name;
import seedu.address.model.entity.person.Person;
import seedu.address.model.entity.person.Phone;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.entity.shop.Shop;
import seedu.address.model.service.PartMap;
import seedu.address.model.service.Service;
import seedu.address.model.service.ServiceStatus;
import seedu.address.model.service.Vehicle;
import seedu.address.model.service.VehicleType;
import seedu.address.model.service.appointment.Appointment;
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
        return new Customer[]{
            new Customer(IdGenerator.generateCustomerId(), new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("regular"), getIntegerSet(1, 2), getIntegerSet(1, 2, 3, 4)),
            new Customer(IdGenerator.generateCustomerId(), new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("difficult", "new driver"), getIntegerSet(3), getIntegerSet(5, 6)),
            new Customer(IdGenerator.generateCustomerId(), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new HashSet<>(), getIntegerSet(4, 5)),
            new Customer(IdGenerator.generateCustomerId(), new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("regular"), getIntegerSet(6)),
            new Customer(IdGenerator.generateCustomerId(), new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new HashSet<>(), new HashSet<>(),
                    getIntegerSet(7)),
            };
    }

    public static Technician[] getSampleTechnicians() {
        return new Technician[]{
            new Technician(IdGenerator.generateStaffId(), new Name("James Tan"), new Phone("89764362"),
                    new Email("jamestan@example.com"), new Address("Blk 586 Bedok Street 23, #08-46"),
                    getTagSet("big boss"), getIntegerSet(1, 4, 5, 6), getIntegerSet(1, 4)),
            new Technician(IdGenerator.generateStaffId(), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("good technician"), getIntegerSet(3, 7), getIntegerSet(2, 5)),
            new Technician(IdGenerator.generateStaffId(), new Name("Loh Jia Yu"), new Phone("968685152"),
                    new Email("lohjy@example.com"), new Address("Blk 73 Hahn Quay Street 67, #04-45"),
                    getTagSet("new technician"), getIntegerSet(3, 6), getIntegerSet(4, 5)),
        };
    }

    public static Appointment[] getSampleAppointments() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

        return new Appointment[] {
            new Appointment(IdGenerator.generateAppointmentId(), 1, LocalDateTime.parse("10/12/2022 02:00 PM", dtf),
                    getIntegerSet(1)),
            new Appointment(IdGenerator.generateAppointmentId(), 1, LocalDateTime.parse("21/12/2022 02:30 PM", dtf),
                    getIntegerSet(2)),
            new Appointment(IdGenerator.generateAppointmentId(), 1, LocalDateTime.parse("15/02/2023 03:00 PM", dtf),
                    getIntegerSet(3)),
            new Appointment(IdGenerator.generateAppointmentId(), 1, LocalDateTime.parse("01/05/2023 06:00 PM", dtf),
                    getIntegerSet(1, 3)),
            new Appointment(IdGenerator.generateAppointmentId(), 2, LocalDateTime.parse("19/06/2022 12:30 PM", dtf),
                    getIntegerSet(2, 3)),
            new Appointment(IdGenerator.generateAppointmentId(), 2, LocalDateTime.parse("25/04/2023 11:00 AM", dtf)),
            new Appointment(IdGenerator.generateAppointmentId(), 5, LocalDateTime.parse("28/05/2023 05:45 PM", dtf)),
        };
    }

    public static Vehicle[] getSampleVehicles() {
        return new Vehicle[] {
            new Vehicle(IdGenerator.generateVehicleId(), 1, "SKA1234A", "Red", "Toyota Corolla",
                VehicleType.CAR, getIntegerSet(1, 5)),
            new Vehicle(IdGenerator.generateVehicleId(), 1, "SGP5678B", "Blue", "Toyota Prius",
                VehicleType.CAR, getIntegerSet(3)),
            new Vehicle(IdGenerator.generateVehicleId(), 2, "SLK9123C", "Grey", "BMW X4",
                VehicleType.CAR, getIntegerSet(2)),
            new Vehicle(IdGenerator.generateVehicleId(), 3, "SBF4567D", "Grey", "Suzuki SV650X ABS",
                VehicleType.MOTORBIKE, getIntegerSet(4)),
            new Vehicle(IdGenerator.generateVehicleId(), 3, "SGC2345F", "Black and White", "Honda Adv 150",
                VehicleType.MOTORBIKE, getIntegerSet(7)),
            new Vehicle(IdGenerator.generateVehicleId(), 4, "SLM5678K", "Dark Blue", "Hyundai Tucson SUV",
                VehicleType.CAR),
        };
    }

    public static Map<String, Integer> getSampleParts() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Frame", 50);
        map.put("Bolt", 1000);
        map.put("Horn", 100);
        map.put("Light", 100);
        map.put("Wheels", 500);
        map.put("Suspension", 100);
        map.put("Gearbox", 100);
        map.put("Headlamp", 20);
        map.put("Steering", 20);
        map.put("Engine", 20);
        map.put("Brake Pads", 50);
        map.put("Car Battery", 50);
        return map;
    }

    // NOTE: Ensure Parts added to Services match with the parts added to getSampleParts*
    public static Service[] getSampleServices() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PartMap samplePartsA = new PartMap();
        samplePartsA.addPart("Wheels", 4);
        samplePartsA.addPart("Suspension", 4);

        PartMap samplePartsB = new PartMap();
        samplePartsB.addPart("Gearbox", 1);

        PartMap samplePartsC = new PartMap();
        samplePartsC.addPart("Engine", 1);

        PartMap samplePartsD = new PartMap();
        samplePartsD.addPart("Car Battery", 1);

        PartMap samplePartsE = new PartMap();
        samplePartsE.addPart("Brake Pads", 1);

        return new Service[]{
            new Service(IdGenerator.generateServiceId(), 1, LocalDate.parse("02/03/2022", dtf),
                samplePartsC, "Engine oil leak",
                LocalDate.parse("10/04/2022", dtf), ServiceStatus.COMPLETE, getIntegerSet(1)),
            new Service(IdGenerator.generateServiceId(), 3, LocalDate.parse("15/01/2023", dtf),
                samplePartsD, "Battery replacement",
                LocalDate.parse("30/01/2023", dtf), ServiceStatus.CANCELLED),
            new Service(IdGenerator.generateServiceId(), 2, LocalDate.parse("15/02/2023", dtf),
                samplePartsB, "Gearbox dead",
                LocalDate.parse("05/04/2023", dtf), ServiceStatus.IN_PROGRESS, getIntegerSet(2, 3)),
            new Service(IdGenerator.generateServiceId(), 4, LocalDate.parse("02/03/2022", dtf),
                samplePartsC, "Engine oil leak",
                LocalDate.parse("10/04/2022", dtf), ServiceStatus.ON_HOLD, getIntegerSet(1)),
            new Service(IdGenerator.generateServiceId(), 1, LocalDate.parse("16/03/2023", dtf),
                samplePartsA, "Wheels and suspension got rekt",
                LocalDate.parse("28/03/2023", dtf), ServiceStatus.IN_PROGRESS, getIntegerSet(1)),
            new Service(IdGenerator.generateServiceId(), 6, LocalDate.parse("16/03/2023", dtf),
                samplePartsE, "Brake pads worn out",
                LocalDate.parse("02/04/2023", dtf), ServiceStatus.TO_REPAIR, getIntegerSet(1, 3)),
            new Service(IdGenerator.generateServiceId(), 5, LocalDate.parse("16/03/2023", dtf),
                new PartMap(), "Just a simple inspection",
                LocalDate.parse("18/03/2023", dtf), ServiceStatus.TO_REPAIR, getIntegerSet(2)),
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

        for (Map.Entry<String, Integer> entry : getSampleParts().entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            sampleSh.addPart(key, value);
        }

        for (Service sampleService : getSampleServices()) {
            sampleSh.addService(sampleService);
        }

        for (Appointment sampleAppointment : getSampleAppointments()) {
            sampleSh.addAppointment(sampleAppointment);
        }

        for (Technician sampleTechnician : getSampleTechnicians()) {
            sampleSh.addTechnician(sampleTechnician);
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
