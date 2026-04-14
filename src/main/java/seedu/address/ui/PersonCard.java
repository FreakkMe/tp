package seedu.address.ui;

import java.util.Comparator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seedu.address.model.interview.InterviewDatabase;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
class PersonCard extends VBox {
    private static final Color BG_CARD = Color.web("#252529");
    private static final Color BORDER_COLOR = Color.web("#35353d");
    private static final String MONO = "JetBrains Mono";

    public PersonCard(Person person, int index, InterviewDatabase db) {
        setPadding(new Insets(15));
        setSpacing(8);
        setMinWidth(350);
        setMaxWidth(400);
        setBackground(new Background(new BackgroundFill(BG_CARD, new CornerRadii(8), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(BORDER_COLOR, BorderStrokeStyle.SOLID,
                new CornerRadii(8), new BorderWidths(1))));

        // 1. Header: Name and Index
        Label nameLabel = new Label(person.getName().fullName);
        nameLabel.setTextFill(Color.web("#e8e8ec"));
        nameLabel.setFont(Font.font(MONO, FontWeight.BOLD, 15));
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        nameLabel.setMinWidth(0);

        Label indexLabel = new Label("#" + index);
        indexLabel.setTextFill(Color.web("#5a5a70"));
        indexLabel.setFont(Font.font(MONO, 11));
        indexLabel.setMinWidth(Label.USE_PREF_SIZE);

        HBox header = new HBox(nameLabel, indexLabel);
        header.setAlignment(Pos.CENTER_LEFT);

        // 2. Contact Info
        VBox fields = new VBox(4,
                createField("Phone", person.getPhone().value),
                createField("Email", person.getEmail().value),
                createField("Address", person.getAddress().value)
        );

        getChildren().addAll(header, fields);

        // 3. Tags
        FlowPane tagsPane = new FlowPane();
        tagsPane.setHgap(4);
        tagsPane.setVgap(4);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.setTextFill(Color.web("#8888a0"));
                    tagLabel.setFont(Font.font(MONO, 10));
                    tagLabel.setPadding(new Insets(2, 5, 2, 5));
                    tagLabel.setBackground(new Background(new BackgroundFill(
                            Color.web("#35353d"), new CornerRadii(4), Insets.EMPTY)));
                    tagsPane.getChildren().add(tagLabel);
                });

        if (!tagsPane.getChildren().isEmpty()) {
            getChildren().add(tagsPane);
        }

        // 4. Interview Records
        String interviewId = person.getInterviewId();
        if (interviewId != null && db.getInterviewRecord(interviewId) != null) {
            String record = String.valueOf(db.getInterviewRecord(interviewId));
            Label recLabel = new Label("Interview:\n" + record);
            recLabel.setTextFill(Color.web("#5a5a70"));
            recLabel.setFont(Font.font(MONO, 11));
            recLabel.setWrapText(true);
            getChildren().add(recLabel);
        }
    }

    private HBox createField(String key, String value) {
        Label k = new Label(key + ": ");
        k.setTextFill(Color.web("#5a5a70"));
        k.setFont(Font.font(MONO, 12));
        Label v = new Label(value);
        v.setTextFill(Color.web("#8888a0"));
        v.setFont(Font.font(MONO, 12));
        return new HBox(k, v);
    }
}
