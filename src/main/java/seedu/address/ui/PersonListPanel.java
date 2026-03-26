package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.interview.InterviewDatabase;
import seedu.address.model.person.Person;

class PersonListPanel extends UiPart<Region> {

    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final InterviewDatabase interviewDatabase;
    private final ObservableList<Person> personList;

    private final FlowPane flowPane = new FlowPane();
    private final VBox wrapper = new VBox(flowPane);

    public PersonListPanel(ObservableList<Person> personList, InterviewDatabase interviewDatabase) {
        super(FXML);
        this.personList = personList;
        this.interviewDatabase = interviewDatabase;

        flowPane.setHgap(16);
        flowPane.setVgap(16);
        flowPane.setPadding(new Insets(0));
        flowPane.setBackground(new Background(new BackgroundFill(
                Color.web("#1a1a1e"), CornerRadii.EMPTY, Insets.EMPTY)));

        wrapper.setPadding(new Insets(16, 28, 80, 28));
        wrapper.setBackground(new Background(new BackgroundFill(
                Color.web("#1a1a1e"), CornerRadii.EMPTY, Insets.EMPTY)));

        populateCards();

        personList.addListener((ListChangeListener<Person>) c -> populateCards());
    }

    private void populateCards() {
        flowPane.getChildren().clear();
        for (int i = 0; i < personList.size(); i++) {
            // Create the card
            PersonCard card = new PersonCard(personList.get(i), i + 1, interviewDatabase);

            // Use 'card' directly instead of 'card.getRoot()'
            card.setPrefWidth(360);
            card.setMaxWidth(400);

            flowPane.getChildren().add(card);
        }
    }

    @Override
    public Region getRoot() {
        return wrapper;
    }
}
