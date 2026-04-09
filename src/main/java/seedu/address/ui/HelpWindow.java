package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for the help page showing a CLI-style command reference.
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s2-cs2103t-t14-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Full user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    /** One row in the command table. */
    public static class CommandEntry {
        private final String command;
        private final String format;
        private final String description;

        public CommandEntry(String command, String format, String description) {
            this.command = command;
            this.format = format;
            this.description = description;
        }

        public String getCommand() {
            return command;
        }

        public String getFormat() {
            return format;
        }

        public String getDescription() {
            return description;
        }
    }

    private static final ObservableList<CommandEntry> COMMAND_LIST =
            FXCollections.observableArrayList(
                new CommandEntry("add",
                        "add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]…",
                        "Add a new applicant"),
                new CommandEntry("edit",
                        "edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…",
                        "Edit an applicant's details"),
                new CommandEntry("delete",
                        "delete INDEX",
                        "Delete an applicant"),
                new CommandEntry("find",
                        "find KEYWORD [MORE_KEYWORDS]…",
                        "Search applicants by name, phone, email, address, or tag"),
                new CommandEntry("list",
                        "list",
                        "List all applicants"),
                new CommandEntry("clear",
                        "clear",
                        "Delete all applicants and interview records"),
                new CommandEntry("edit-i",
                        "edit-i INDEX",
                        "Open interview note editor for an applicant (creates or updates)"),
                new CommandEntry("delete-i",
                        "delete-i INDEX",
                        "Delete the interview record linked to an applicant"),
                new CommandEntry("list-i",
                        "list-i",
                        "List all interview records in the database"),
                new CommandEntry("help",
                        "help",
                        "Show this command reference window"),
                new CommandEntry("exit",
                        "exit",
                        "Exit the application")
            );

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TableView<CommandEntry> commandTable;

    @FXML
    private TableColumn<CommandEntry, String> commandCol;

    @FXML
    private TableColumn<CommandEntry, String> formatCol;

    @FXML
    private TableColumn<CommandEntry, String> descCol;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        commandCol.setCellValueFactory(new PropertyValueFactory<>("command"));
        formatCol.setCellValueFactory(new PropertyValueFactory<>("format"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        commandTable.setItems(COMMAND_LIST);
        commandTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
