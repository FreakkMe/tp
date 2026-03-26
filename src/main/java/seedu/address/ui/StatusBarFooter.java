package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";
    private static final String MONO = "JetBrains Mono";

    @FXML
    private Label saveLocationStatus;

    public StatusBarFooter(Path saveLocation) {
        super(FXML);

        Label icon = new Label("\u2913");
        icon.setTextFill(Color.web("#5a5a70"));
        icon.setFont(Font.font(MONO, 13));

        String locationText = "Download Contacts  \u00b7  "
                + Paths.get(".").resolve(saveLocation).toString();

        saveLocationStatus.setText(locationText);
        saveLocationStatus.setTextFill(Color.web("#5a5a70"));
        saveLocationStatus.setFont(Font.font(MONO, 11));

        if (saveLocationStatus.getParent() instanceof HBox parent) {
            parent.setAlignment(Pos.CENTER_LEFT);
            parent.setSpacing(6);
            parent.getChildren().add(0, icon);
        }
    }
}
