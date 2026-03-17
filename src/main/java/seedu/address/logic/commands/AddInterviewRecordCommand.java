package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.interview.InterviewRecord;

/**
 * Adds a new {@link InterviewRecord} to the interview database.
 * <p>
 * Usage example:
 * <pre>
 * {@code
 * addInterviewRecord id/INT-456 date/2026-04-10 notes/2nd technical round
 * }
 * </pre>
 * This command checks for duplicate interview IDs before adding the record.
 */
public class AddInterviewRecordCommand extends Command {

    public static final String COMMAND_WORD = "addInterviewRecord";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new interview record.\n"
            + "Format: " + COMMAND_WORD + " id/ID d/DATE nt/NOTES\n"
            + "Example: " + COMMAND_WORD + " id/I-001 d/2026-04-10 nt/2nd technical round";

    public static final String MESSAGE_SUCCESS = "New interview added: %s";
    public static final String MESSAGE_DUPLICATE = "Interview with this ID already exists: %s";

    private final InterviewRecord toAdd;

    public AddInterviewRecordCommand(InterviewRecord record) {
        this.toAdd = record;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        var db = model.getInterviewDatabase();

        if (db.contains(toAdd.getId())) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE, toAdd.getId()));
        }

        db.addInterviewRecord(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
