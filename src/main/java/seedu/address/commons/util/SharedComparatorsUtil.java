package seedu.address.commons.util;

import java.util.Comparator;

import seedu.address.model.service.appointment.Appointment;

/**
 * Utils class to store shared comparators
 */
public class SharedComparatorsUtil {
    public static Comparator<Appointment> getDefaultAppointmentSort() {
        return Comparator.comparing(Appointment::getDateStatus)
                .thenComparing(Appointment::getTimeDate);
    }
}
