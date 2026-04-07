package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes the interview record linked to the person at the given index.
 *
 * <p>Usage: {@code delete-i INDEX}
 * <p>Example: {@code delete-i 2}
 *
 * <p>If the person has no interview record, a message is shown and nothing changes.
 */
public class DeleteInterviewCommand extends Command {

    public static final String COMMAND_WORD = "delete-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the interview record of the person at the given index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted interview record for: %s";
    public static final String MESSAGE_NO_INTERVIEW = "This person has no interview record.";

    private final Index targetIndex;
    /**
     * Constructs a {@code DeleteInterviewCommand} with the specified target index.
     *
     * @param targetIndex The index of the person whose interview record is to be deleted.
     */
    public DeleteInterviewCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());

        if (person.getInterviewIds().isEmpty()) {
            return new CommandResult(MESSAGE_NO_INTERVIEW);
        }

        String interviewId = person.getInterviewIds().get(0);

        // Remove from global DB
        model.getInterviewDatabase().removeInterviewRecord(interviewId);

        // Unlink from person
        Person updatedPerson = person.removeInterviewId(interviewId);
        model.setPerson(person, updatedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, person.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteInterviewCommand)) {
            return false;
        }
        DeleteInterviewCommand otherCommand = (DeleteInterviewCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }
}
