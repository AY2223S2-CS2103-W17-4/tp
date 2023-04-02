package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERNAL_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TECHNICIANS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.person.Address;
import seedu.address.model.entity.person.Email;
import seedu.address.model.entity.person.Name;
import seedu.address.model.entity.person.Phone;
import seedu.address.model.entity.person.Technician;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditTechnicianCommand extends RedoableCommand {

    public static final String COMMAND_WORD = "edittechnician";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the technician identified "
            + "by the id number. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_INTERNAL_ID + "TECHNICIAN_ID "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited technician: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_TECHNICIAN_NOT_FOUND = "Technician %d does not exist";
    private static final Technician TECHNICIAN_DOES_NOT_EXIST = null;
    private final EditTechnicianDescriptor editTechnicianDescriptor;


    /**
     * @param editTechnicianDescriptor details to edit the person with
     */
    public EditTechnicianCommand(EditTechnicianDescriptor editTechnicianDescriptor) {
        requireNonNull(editTechnicianDescriptor);
        this.editTechnicianDescriptor = new EditTechnicianDescriptor(editTechnicianDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Technician> lastShownList = model.getFilteredTechnicianList();

        // Locate entry containing id. By right each ID is unique.
        Technician personToEdit = lastShownList.stream().filter(person ->
                        editTechnicianDescriptor.getId() == person.getId()).findAny()
                .orElse(TECHNICIAN_DOES_NOT_EXIST);

        if (personToEdit == TECHNICIAN_DOES_NOT_EXIST) {
            throw new CommandException(String.format(MESSAGE_TECHNICIAN_NOT_FOUND,
                this.editTechnicianDescriptor.getId()));
        }

        Technician editedPerson = createEditedPerson(personToEdit, editTechnicianDescriptor);

        model.setTechnician(personToEdit, editedPerson);
        //model.selectTechnician(editedPerson);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.updateFilteredTechnicianList(PREDICATE_SHOW_ALL_TECHNICIANS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson), Tab.TECHNICIANS);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Technician createEditedPerson(Technician personToEdit,
                                                 EditTechnicianDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        int id = personToEdit.getId();

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Technician(id, updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTechnicianCommand)) {
            return false;
        }

        // state check
        EditTechnicianCommand e = (EditTechnicianCommand) other;
        return editTechnicianDescriptor.equals(e.editTechnicianDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTechnicianDescriptor {

        private int id;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditTechnicianDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTechnicianDescriptor(EditTechnicianDescriptor toCopy) {
            setId(toCopy.id);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTechnicianDescriptor)) {
                return false;
            }

            // state check
            EditTechnicianDescriptor e = (EditTechnicianDescriptor) other;

            return getId() == e.getId() //not sure if need this id checking
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
