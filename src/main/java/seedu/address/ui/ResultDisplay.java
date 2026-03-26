package seedu.address.ui;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

class ResultDisplay extends Region {
    private final TextArea resultDisplay = new TextArea();

    public ResultDisplay() {
        resultDisplay.setEditable(false);
        resultDisplay.setWrapText(true);
        resultDisplay.getStyleClass().add("result-display");

        // Manual styling to match your dark theme
        resultDisplay.setStyle(
                "-fx-control-inner-background: #1a1a1e; "
                        + "-fx-text-fill: #e8e8ec; "
                        + "-fx-font-family: 'JetBrains Mono'; "
                        + "-fx-background-color: transparent; "
                        + "-fx-border-color: #35353d; "
                        + "-fx-border-width: 1; "
                        + "-fx-border-radius: 5;"
        );

        resultDisplay.setPrefHeight(100);
        resultDisplay.setMinHeight(100);
    }

    public void setFeedbackToUser(String feedback) {
        resultDisplay.setText(feedback);
    }

    // Since this is a Region, we need to return the internal TextArea
    public TextArea getDisplay() {
        return resultDisplay;
    }
}
