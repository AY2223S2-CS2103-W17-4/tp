
package seedu.address.model.service.appointment;

import static seedu.address.commons.util.StringUtil.NEWLINE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.Findable;

/**
 * The appointment class containing a meeting with a customer at a particular date.
 */
public class Appointment implements Findable {
    /**
     * Represents the status of the appointment date base on current date time
     */
    public enum DateStatus {
        NOW,
        UPCOMING,
        PASSED,
    }
    private static final String OUTPUT_FORMAT = "<<Appointment>>" + NEWLINE
                                                        + "Customer: %d" + NEWLINE
                                                        + "Date: %s";
    private final int id;
    private final int customerId;
    private final LocalDateTime timeDate;

    private final Set<Integer> staffIds = new HashSet<>();

    /**
     * This method is the constructor for Appointment.
     *
     * @param customerId The customer id to meet.
     * @param timeDate   The date time which this appointment occurs.
     */
    public Appointment(int id, int customerId, LocalDateTime timeDate) {
        this(id, customerId, timeDate, new HashSet<>());
    }

    /**
     * This method is the constructor for Appointment.
     *
     * @param customerId The customer id to meet.
     * @param timeDate   The date time which this appointment occurs.
     * @param staffIds   The list of staff ids involved in the appointment.
     */
    public Appointment(int id, int customerId, LocalDateTime timeDate, Set<Integer> staffIds) {
        this.id = id;
        this.customerId = customerId;
        this.timeDate = timeDate;
        this.staffIds.addAll(staffIds);
    }

    /**
     * @return ID of appointment
     */
    public int getId() {
        return this.id;
    }

    /**
     * This method returns the customer id who we are meeting.
     *
     * @return The customer id.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * This method returns the time which we are meeting this customer.
     *
     * @return the date time of this appointment.
     */
    public LocalDateTime getTimeDate() {
        return timeDate;
    }

    /**
     * This method returns the list of staff members who will be meeting this customer.
     *
     * @return a list of staff members.
     */
    public List<Integer> getStaffIds() {
        return new ArrayList<>(this.staffIds);
    }

    /**
     * Adds technician ID to the appointment
     *
     * @param technicianId ID of technician
     */
    public void addTechnician(int technicianId) {
        this.staffIds.add(technicianId);
    }

    /**
     * Removes technician ID from staffIds
     *
     * @param technicianId ID of technician
     */
    public void removeTechnician(int technicianId) {
        this.staffIds.remove(technicianId);
    }

    /**
     * Returns true if both staffs have the same id.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                       && otherAppointment.getId() == getId();
    }

    /***
     * This method returns a boolean indicating if a date falls on the specified date
     * @param ldt
     * @return a boolean indicating if the date falls on the same date as the input
     */
    // adapted from https://stackoverflow.com/questions/494180/how-do-i-check-if-a-date-is-within-a-certain-range
    // checks if the date is between arrival and endDate
    public boolean isWithinRange(LocalDateTime ldt) {
        LocalDate totalDate = this.getTimeDate().toLocalDate();
        LocalDate startDate = ldt.toLocalDate().minus(Period.ofDays(1));
        LocalDate endDate = ldt.toLocalDate().plusDays(1);
        return (totalDate.isAfter(startDate) && totalDate.isBefore(endDate));
    }

    public DateStatus getDateStatus() {
        LocalDate date = this.timeDate.toLocalDate();
        LocalDate today = LocalDate.now();

        if (date.isAfter(today)) {
            // Date is in the future
            long daysBetween = ChronoUnit.DAYS.between(today, date);
            if (daysBetween == 1) {
                return DateStatus.UPCOMING;
            } else {
                return DateStatus.UPCOMING;
            }
        } else if (date.isBefore(today)) {
            // Date is in the past
            long daysBetween = ChronoUnit.DAYS.between(date, today);
            if (daysBetween == 1) {
                return DateStatus.PASSED;
            } else {
                return DateStatus.PASSED;
            }
        } else {
            // Date is today
            return DateStatus.NOW;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Appointment) {
            Appointment other = (Appointment) obj;
            return this.id == other.id;
        }
        return false;
    }

    /***
     * This method returns a boolean indicating if a date falls on the specified date
     * @param ldt
     * @return a boolean indicating if the date falls on the same date as the input
     */
    // adapted from https://stackoverflow.com/questions/494180/how-do-i-check-if-a-date-is-within-a-certain-range
    // checks if the date is between arrival and endDate
    public boolean isWithinRange(LocalDateTime ldt) {
        LocalDate totalDate = this.getTimeDate().toLocalDate();
        LocalDate startDate = ldt.toLocalDate().minus(Period.ofDays(1));
        LocalDate endDate = ldt.toLocalDate().plusDays(1);
        return (totalDate.isAfter(startDate) && totalDate.isBefore(endDate));
    }

    @Override
    public String toString() {
        return String.format(OUTPUT_FORMAT, this.getCustomerId(),
                this.getTimeDate().format(DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.FULL, FormatStyle.SHORT)));
    }

    @Override
    public boolean hasKeyword(String keyword) {
        try {
            LocalDate date = LocalDate.parse(keyword);
            return this.timeDate.toLocalDate().equals(date);
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}
