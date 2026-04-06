package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Opens the interview notes editor popup for the person at the given index.
 *
 * <p>Usage: {@code edit-i INDEX}
 * <p>Example: {@code edit-i 3}
 *
 * <p>This command only validates the index and signals the UI to open the editor.
 * The actual saving of notes is handled by the popup callback in MainWindow.
 */
public class EditInterviewCommand extends Command {

    public static final String COMMAND_WORD = "edit-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the interview notes editor for the person at the given index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_OPEN_EDITOR = "Opening interview editor for: %s";

    private final Index targetIndex;

    /**
     * Constructs an {@code EditInterviewCommand} with the specified target index.
     *
     * @param targetIndex The index of the person whose interview notes are to be edited.
     */
    public EditInterviewCommand(Index targetIndex) {
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

        return new CommandResult(
                String.format(MESSAGE_OPEN_EDITOR, person.getName().fullName),
                person
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditInterviewCommand)) {
            return false;
        }
        EditInterviewCommand otherCommand = (EditInterviewCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }
}
