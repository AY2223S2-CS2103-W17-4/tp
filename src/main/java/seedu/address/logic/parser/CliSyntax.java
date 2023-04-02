package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_PLATE_NUM = new Prefix("p/");
    public static final Prefix PREFIX_BRAND = new Prefix("b/");
    public static final Prefix PREFIX_CUSTOMER_ID = new Prefix("c/");
    public static final Prefix PREFIX_VEHICLE_TYPE = new Prefix("t/");
    public static final Prefix PREFIX_PART_NAME = new Prefix("n/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_VEHICLE_ID = new Prefix("v/");
    public static final Prefix PREFIX_SERVICE_ID = new Prefix("s/");
    public static final Prefix PREFIX_APPOINTMENT_ID = new Prefix("a/");
    public static final Prefix PREFIX_TECHNICIAN_ID = new Prefix("t/");
    public static final Prefix PREFIX_SERVICE_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_SERVICE_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_SERVICE_DURATION = new Prefix("l/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_VEHICLE_COLOR = new Prefix("cl/");

    // EditService
    public static final Prefix PREFIX_SERVICE_START = new Prefix("t/");
    public static final Prefix PREFIX_SERVICE_END = new Prefix("e/");

    // Prefix internal id == view <ID> <--
    public static final Prefix PREFIX_INTERNAL_ID = new Prefix("i/");

    public static final Prefix PREFIX_SORT_BY = new Prefix("by/");
    public static final Prefix PREFIX_REVERSE_SORT = new Prefix("r/");
}
