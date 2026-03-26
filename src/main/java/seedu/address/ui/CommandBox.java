package seedu.address.ui;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component responsible for receiving user command inputs.
 */
class CommandBox extends TextField {
    private static final String STYLE_DEFAULT =
            "-fx-background-color: transparent; -fx-text-fill: #e8e8ec; "
                    + "-fx-prompt-text-fill: #5a5a70; -fx-font-family: 'JetBrains Mono'; -fx-font-size: 14px;";
    private static final String STYLE_ERROR = STYLE_DEFAULT + "-fx-text-fill: #e05555;";

    private final CommandExecutor commandExecutor;

    public CommandBox(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        setStyle(STYLE_DEFAULT);
        setPromptText("Enter command here...");
        HBox.setHgrow(this, Priority.ALWAYS);

        // Reset to default style when user starts typing again
        textProperty().addListener((unused) -> setStyle(STYLE_DEFAULT));

        // Trigger command on Enter key
        setOnAction(event -> handleCommandEntered());
    }

    private void handleCommandEntered() {
        String commandText = getText();
        if (commandText.isEmpty()) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            setText("");
        } catch (CommandException | ParseException e) {
            // Only turn red for expected logic/parser errors
            setStyle(STYLE_ERROR);
        }
    }

    @FunctionalInterface
    public interface CommandExecutor {
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
