package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds an interview record to an existing person in the address book.
 */
public class LinkInterviewRecordCommand extends Command {

    public static final String COMMAND_WORD = "linkInterviewRecord";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Links an existing interview record to the person. \n"
            + "Parameters: INDEX "
            + "ID "
            + "Example: " + COMMAND_WORD + " 1 "
            + "I-001 ";

    public static final String MESSAGE_SUCCESS = "Interview record linked to: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person index provided is invalid.";
    public static final String MESSAGE_INTERVIEW_NOT_FOUND = "No interview record with ID: %s";
    public static final String MESSAGE_ALREADY_ASSIGNED = "This interview is already assigned to the person.";

    private final Index personIndex;
    private final String interviewId;

    /**
     * Creates an LinkInterviewRecordCommand to add the specified {@code InterviewRecord}
     * to the specified {@code Person}.
     */
    public LinkInterviewRecordCommand(Index personIndex, String interviewId) {
        requireNonNull(personIndex);
        requireNonNull(interviewId);
        this.personIndex = personIndex;
        this.interviewId = interviewId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        var lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        if (!model.getInterviewDatabase().contains(interviewId)) {
            throw new CommandException(String.format(MESSAGE_INTERVIEW_NOT_FOUND, interviewId));
        }

        if (personToEdit.getInterviewIds().contains(interviewId)) {
            throw new CommandException(MESSAGE_ALREADY_ASSIGNED);
        }

        Person editedPerson = personToEdit.addInterviewRecord(interviewId);

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, interviewId, personToEdit.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LinkInterviewRecordCommand)) {
            return false;
        }

        LinkInterviewRecordCommand otherCommand = (LinkInterviewRecordCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && interviewId.equals(otherCommand.interviewId);
    }
}

