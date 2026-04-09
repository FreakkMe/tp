package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.logic.commands.FindInterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindInterviewCommand object.
 */
public class FindInterviewCommandParser implements Parser<FindInterviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindInterviewCommand
     * and returns a FindInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindInterviewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindInterviewCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        return new FindInterviewCommand(List.of(keywords));
    }
}

