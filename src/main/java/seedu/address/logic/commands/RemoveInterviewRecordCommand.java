package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes an interview record from an existing person in the address book.
 */
public class RemoveInterviewRecordCommand extends Command {

    public static final String COMMAND_WORD = "removeInterviewRecord";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes an interview record from the person identified by the index number "
            + "used in the displayed person list.\n"
            + "Parameters: INDEX ID\n"
            + "Example: " + COMMAND_WORD + " 1 I-001";

    public static final String MESSAGE_SUCCESS = "Interview record removed from: %1$s";
    public static final String MESSAGE_INVALID_RECORD_ID = "The interview record index provided is invalid.";

    private final Index personIndex;
    private final String interviewId;

    /**
     * Creates a DeleteInterviewRecordCommand to delete the specified interview record
     * from the specified person.
     */
    public RemoveInterviewRecordCommand(Index personIndex, String interviewId) {
        requireNonNull(personIndex);
        requireNonNull(interviewId);
        this.personIndex = personIndex;
        this.interviewId = interviewId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        List<String> interviewIds = personToEdit.getInterviewIds();

        if (!interviewIds.contains(interviewId)) {
            throw new CommandException(String.format(MESSAGE_INVALID_RECORD_ID, interviewId));
        }

        // Removes ID from person
        Person editedPerson = personToEdit.removeInterviewId(interviewId);
        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveInterviewRecordCommand)) {
            return false;
        }

        RemoveInterviewRecordCommand otherCommand = (RemoveInterviewRecordCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && interviewId.equals(otherCommand.interviewId);
    }
}

