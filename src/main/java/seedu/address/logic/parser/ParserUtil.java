package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.person.Address;
import seedu.address.model.entity.person.Email;
import seedu.address.model.entity.person.Name;
import seedu.address.model.entity.person.Phone;
import seedu.address.model.service.ServiceStatus;
import seedu.address.model.service.VehicleType;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses string into integer
     *
     * @param rawInt String to be parsed
     * @return Parsed integer
     * @throws ParseException If string cannot be parsed as integer
     */
    public static int parseInt(String rawInt) throws ParseException {
        try {
            return Integer.parseInt(rawInt);
        } catch (NumberFormatException ex) {
            throw new ParseException("Input not a number");
        }
    }

    /**
     * Parses string into vehicle type
     *
     * @param rawType String to be parsed
     * @return Parsed VehicleType
     * @throws ParseException If string cannot be parsed as vehicleType
     */
    public static VehicleType parseVehicleType(String rawType) throws ParseException {
        requireNonNull(rawType);
        switch (rawType.toLowerCase()) {
        case "car":
            return VehicleType.CAR;
        case "motorbike":
            return VehicleType.MOTORBIKE;
        default:
            throw new ParseException(VehicleType.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses string into ServiceStatus
     *
     * @param rawStatus String to be parsed
     * @return Parsed ServiceStatus
     * @throws ParseException If string cannot be parsed into ServiceStatus
     */
    public static ServiceStatus parseServiceStatus(String rawStatus) throws ParseException {
        requireNonNull(rawStatus);
        for (ServiceStatus p : ServiceStatus.values()) {
            if (p.isEqual(rawStatus)) {
                return p;
            }
        }
        throw new ParseException(ServiceStatus.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses string into LocalDate
     *
     * @param rawDate String to be parsed
     * @return Parsed LocalDate
     * @throws ParseException If string cannot be parsed as LocalDate
     */
    public static LocalDate parseDate(String rawDate) throws ParseException {
        requireNonNull(rawDate);
        try {
            return LocalDate.parse(rawDate);
        } catch (DateTimeParseException ex) {
            throw new ParseException("Date should be in the format 'YYYY-MM-DD'");
        }
    }

    /**
     * Parses string into LocalTime
     *
     * @param rawTime String to be parsed
     * @return Parsed LocalTime
     * @throws ParseException If string cannot be parsed as LocalTime
     */
    public static LocalTime parseTime(String rawTime) throws ParseException {
        requireNonNull(rawTime);
        try {
            return LocalTime.parse(rawTime);
        } catch (DateTimeParseException ex) {
            throw new ParseException("Time should be in the format 'HH:MM:SS' or 'HH:MM'");
        }
    }

    /**
     * Return given string.
     *
     * @param rawString The String
     * @return The String.
     */
    public static String parseString(String rawString) {
        return rawString;
    }
}
