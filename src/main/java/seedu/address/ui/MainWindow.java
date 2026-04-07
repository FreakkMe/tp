package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

class MainWindow {
    private final Stage primaryStage;
    private final Logic logic;
    private final VBox root = new VBox();
    private final FlowPane personContainer = new FlowPane(16, 16);
    private final ResultDisplay resultDisplay = new ResultDisplay();
    private final HelpWindow helpWindow = new HelpWindow();

    public MainWindow(Stage primaryStage, Logic logic) {
        this.primaryStage = primaryStage;
        this.logic = logic;

        root.setBackground(new Background(new BackgroundFill(Color.web("#1a1a1e"), null, null)));

        // 1. Menu Bar (Toolbar)
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: #252529; -fx-font-family: 'JetBrains Mono';");

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> handleExit());
        fileMenu.getItems().add(exitItem);

        Menu helpMenu = new Menu("Help");
        MenuItem helpItem = new MenuItem("Help");
        helpItem.setOnAction(e -> handleHelp());
        helpMenu.getItems().add(helpItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        // 2. Command Input Logic
        CommandBox commandBox = new CommandBox(commandText -> {
            try {
                CommandResult commandResult = logic.execute(commandText);
                resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
                updatePersons(logic.getFilteredPersonList());

                if (commandResult.isShowHelp()) {
                    handleHelp();
                }
                if (commandResult.isExit()) {
                    handleExit();
                }
                if (commandResult.isShowInterviewEditor()) {
                    handleInterviewEdit(commandResult.getInterviewPerson());
                }
                return commandResult;
            } catch (CommandException | ParseException e) {
                // Show specific message in result box and re-throw to trigger red text
                resultDisplay.setFeedbackToUser(e.getMessage());
                throw e;
            }
        });

        Label promptLabel = new Label(">");
        promptLabel.setTextFill(Color.web("#5a5a70"));
        promptLabel.setStyle("-fx-font-family: 'JetBrains Mono'; -fx-font-size: 14px;");

        HBox commandWrapper = new HBox(promptLabel, commandBox);
        commandWrapper.setAlignment(Pos.CENTER_LEFT);
        commandWrapper.setPadding(new Insets(10, 28, 5, 28));
        commandWrapper.setSpacing(10);

        // 3. Result Display
        VBox resultWrapper = new VBox(resultDisplay.getDisplay());
        resultWrapper.setPadding(new Insets(0, 28, 10, 28));

        // 4. Scrollable Contact List
        personContainer.setPadding(new Insets(10, 28, 28, 28));
        ScrollPane scrollPane = new ScrollPane(personContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(menuBar, commandWrapper, resultWrapper, scrollPane);
        primaryStage.setScene(new Scene(root, 900, 700));
    }

    public void fillInnerParts() {
        updatePersons(logic.getFilteredPersonList());
    }

    public void updatePersons(ObservableList<Person> persons) {
        personContainer.getChildren().clear();
        for (int i = 0; i < persons.size(); i++) {
            PersonCard card = new PersonCard(persons.get(i), i + 1, logic.getInterviewDatabase());
            card.setPrefWidth(360);
            personContainer.getChildren().add(card);
        }
    }

    public void show() {
        primaryStage.show();
    }

    public void handleInterviewEdit(seedu.address.model.person.Person person) {
        // Resolve existing notes from the person's first interview record (if any)
        String existingNotes = person.getInterviewIds().stream()
                .findFirst()
                .map(id -> logic.getInterviewDatabase().getInterviewRecord(id))
                .filter(java.util.Objects::nonNull)
                .map(record -> record.getNotes())
                .orElse("");

        InterviewEditWindow window = new InterviewEditWindow(
                primaryStage,
                person,
                existingNotes,
                notes -> {
                    logic.saveInterviewNotes(person, notes);
                    updatePersons(logic.getFilteredPersonList());
                }
        );
        window.showAndWait();
    }

    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    private void handleExit() {
        primaryStage.hide();
    }

    /**
     * Returns the primary stage of the application.
     * This is required by UiManager to display alert dialogs.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
