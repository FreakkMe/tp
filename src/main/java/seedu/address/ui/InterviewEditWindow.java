package seedu.address.ui;

import java.util.Comparator;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.model.person.Person;

/**
 * A modal popup window for viewing and editing a person's interview notes.
 *
 * <p>Opened via {@code edit-i INDEX}. Displays the person's details at the top
 * and a large text editor pre-filled with any existing notes.
 *
 * <p>Key bindings:
 * <ul>
 *   <li>Enter — saves the notes via the provided callback and closes the window</li>
 *   <li>Ctrl+Enter — inserts a newline (normal text entry)</li>
 *   <li>Window X button — discards changes and closes without saving</li>
 * </ul>
 */
public class InterviewEditWindow {

    private static final String MONO = "JetBrains Mono";
    private static final Color COLOR_BG = Color.web("#1a1a1e");
    private static final Color COLOR_CARD = Color.web("#252529");
    private static final Color COLOR_BORDER = Color.web("#35353d");
    private static final Color COLOR_TEXT = Color.web("#e8e8ec");
    private static final Color COLOR_MUTED = Color.web("#5a5a70");
    private static final Color COLOR_SECONDARY = Color.web("#8888a0");
    private static final Color COLOR_ACCENT = Color.web("#5a5aff");

    private final Stage stage;

    /**
     * Creates and configures the interview edit window.
     *
     * @param owner         The owner window (main window) — used to set modality.
     * @param person        The person whose interview notes are being edited.
     * @param existingNotes The current notes text, or empty string if none.
     * @param onSave        Callback invoked with the final notes text when the user presses Enter.
     *                      Not called if the user closes via X.
     */
    public InterviewEditWindow(Window owner, Person person, String existingNotes, Consumer<String> onSave) {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.setTitle("Interview Notes \u2014 " + person.getName().fullName);
        stage.setResizable(true);

        VBox root = new VBox(16);
        root.setPadding(new Insets(24));
        root.setBackground(new Background(new BackgroundFill(COLOR_BG, null, null)));

        // --- Person details header ---
        VBox header = buildHeader(person);

        // --- Divider label ---
        Label divider = new Label("Interview Notes");
        divider.setTextFill(COLOR_MUTED);
        divider.setFont(Font.font(MONO, FontWeight.BOLD, 11));
        VBox.setMargin(divider, new Insets(4, 0, 0, 0));

        // --- Notes text area ---
        TextArea notesArea = buildNotesArea(existingNotes);

        // --- Hint label ---
        Label hint = new Label("Enter -> save          Shift+Enter -> new line          Close Window -> discard");
        hint.setTextFill(COLOR_MUTED);
        hint.setFont(Font.font(MONO, 10));
        hint.setAlignment(Pos.CENTER_RIGHT);
        VBox.setMargin(hint, new Insets(2, 0, 0, 0));

        VBox.setVgrow(notesArea, Priority.ALWAYS);
        root.getChildren().addAll(header, divider, notesArea, hint);

        // --- Key handling ---
        // Intercept at scene level so it fires before TextArea default handling.
        Scene scene = new Scene(root, 600, 500);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (event.isShiftDown()) {
                    // Shift+Enter: insert newline at caret
                    int pos = notesArea.getCaretPosition();
                    notesArea.insertText(pos, "\n");
                    event.consume();
                } else {
                    // Enter: save and close
                    onSave.accept(notesArea.getText());
                    stage.close();
                    event.consume();
                }
            }
        });

        stage.setScene(scene);
    }

    // --- Private helpers ---

    private VBox buildHeader(Person person) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(14));
        card.setBackground(new Background(new BackgroundFill(COLOR_CARD, new CornerRadii(8), Insets.EMPTY)));
        card.setBorder(new Border(new BorderStroke(COLOR_BORDER, BorderStrokeStyle.SOLID,
                new CornerRadii(8), new BorderWidths(1))));

        // Name
        Label nameLabel = new Label(person.getName().fullName);
        nameLabel.setTextFill(COLOR_TEXT);
        nameLabel.setFont(Font.font(MONO, FontWeight.BOLD, 15));

        // Contact fields
        VBox fields = new VBox(4,
                createField("Phone", person.getPhone().value),
                createField("Email", person.getEmail().value),
                createField("Address", person.getAddress().value)
        );

        card.getChildren().addAll(nameLabel, fields);

        // Tags
        if (!person.getTags().isEmpty()) {
            FlowPane tagsPane = new FlowPane();
            tagsPane.setHgap(4);
            tagsPane.setVgap(4);
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        Label tagLabel = new Label(tag.tagName);
                        tagLabel.setTextFill(COLOR_SECONDARY);
                        tagLabel.setFont(Font.font(MONO, 10));
                        tagLabel.setPadding(new Insets(2, 5, 2, 5));
                        tagLabel.setBackground(new Background(new BackgroundFill(
                                COLOR_BORDER, new CornerRadii(4), Insets.EMPTY)));
                        tagsPane.getChildren().add(tagLabel);
                    });
            card.getChildren().add(tagsPane);
        }

        return card;
    }

    private TextArea buildNotesArea(String existingNotes) {
        TextArea area = new TextArea(existingNotes);
        area.setWrapText(true);
        area.setFont(Font.font(MONO, 13));
        area.setStyle(
                "-fx-control-inner-background: #252529;"
                + "-fx-text-fill: #e8e8ec;"
                + "-fx-highlight-fill: #5a5aff;"
                + "-fx-highlight-text-fill: #ffffff;"
                + "-fx-border-color: #35353d;"
                + "-fx-border-radius: 6;"
                + "-fx-background-radius: 6;"
                + "-fx-prompt-text-fill: #5a5a70;"
        );
        area.setPromptText("Type interview notes here…");
        // Move caret to end of any existing text
        area.positionCaret(existingNotes.length());
        return area;
    }

    private javafx.scene.layout.HBox createField(String key, String value) {
        Label k = new Label(key + ": ");
        k.setTextFill(COLOR_MUTED);
        k.setFont(Font.font(MONO, 12));
        Label v = new Label(value);
        v.setTextFill(COLOR_SECONDARY);
        v.setFont(Font.font(MONO, 12));
        return new javafx.scene.layout.HBox(k, v);
    }

    /**
     * Shows the window and blocks until it is closed (modal).
     */
    public void showAndWait() {
        stage.showAndWait();
    }
}
