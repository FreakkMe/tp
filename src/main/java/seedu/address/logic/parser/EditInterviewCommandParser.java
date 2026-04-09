package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditInterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link EditInterviewCommand}.
 *
 * <p>Expected format: {@code edit-i INDEX}
 */
public class EditInterviewCommandParser implements Parser<EditInterviewCommand> {

    @Override
    public EditInterviewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args.trim());
            return new EditInterviewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditInterviewCommand.MESSAGE_USAGE), pe);
        }
    }
}


