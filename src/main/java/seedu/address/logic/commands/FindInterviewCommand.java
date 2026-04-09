package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.InterviewRecordContainsKeywordsPredicate;

/**
 * Finds and lists all persons whose interview records contain any of the given keywords.
 * Keyword matching is case-insensitive.
 */
public class FindInterviewCommand extends Command {

    public static final String COMMAND_WORD = "find-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose interview records contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " java communication";

    private final List<String> keywords;

    public FindInterviewCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(
                new InterviewRecordContainsKeywordsPredicate(keywords, model.getInterviewDatabase()));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindInterviewCommand)) {
            return false;
        }

        FindInterviewCommand otherFindInterviewCommand = (FindInterviewCommand) other;
        return keywords.equals(otherFindInterviewCommand.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
