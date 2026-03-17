package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes an interview record and automatically unlinks it from all persons.
 */
public class DeleteInterviewRecordCommand extends Command {

    public static final String COMMAND_WORD = "deleteInterviewRecord";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an interview record and removes it from all linked persons.\n"
            + "Parameters: ID\n"
            + "Example: " + COMMAND_WORD + " I-007";

    public static final String MESSAGE_SUCCESS = "Deleted interview record: %s";
    public static final String MESSAGE_NOT_FOUND = "No interview record found with ID: %s";

    private final String interviewId;

    public DeleteInterviewRecordCommand(String interviewId) {
        this.interviewId = interviewId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        var db = model.getInterviewDatabase();

        if (!db.contains(interviewId)) {
            throw new CommandException(String.format(MESSAGE_NOT_FOUND, interviewId));
        }

        // Deletes from global database
        if (!db.contains(interviewId)) {
            throw new CommandException(String.format(MESSAGE_NOT_FOUND, interviewId));
        }

        db.removeInterviewRecord(interviewId);

        // Unlinks from ALL persons who have this ID
        for (Person person : model.getAddressBook().getPersonList()) {
            if (person.getInterviewIds().contains(interviewId)) {
                Person updatedPerson = person.removeInterviewId(interviewId);
                model.setPerson(person, updatedPerson);
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, interviewId));
    }
}
