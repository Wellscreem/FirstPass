package software;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;


public class HBoxElement extends HBox {

    public HBoxElement(Password mdp) {
        BorderPane bp1 = new Element(mdp);
        this.getChildren().addAll(bp1);
        styliser();
    }

    public HBoxElement(Password mdp1, Password mdp2) {
        BorderPane bp1 = new Element(mdp1);
        BorderPane bp2 = new Element(mdp2);
        this.getChildren().addAll(bp1, bp2);
        styliser();
    }

    public HBoxElement(Password mdp1, Password mdp2, Password mdp3) {
        BorderPane bp1 = new Element(mdp1);
        BorderPane bp2 = new Element(mdp2);
        BorderPane bp3 = new Element(mdp3);
        this.getChildren().addAll(bp1, bp2, bp3);
        styliser();
    }

    private void styliser() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        this.setMaxSize(screenWidth * (1599.0/1920.0), 200);
        this.setMinSize(screenWidth * (1599.0/1920.0), 200);
        for (Node e : this.getChildren()) {
            e.getStyleClass().add("element");
            ((BorderPane)e).setMaxSize(screenWidth * (430.0/1920.0), 200);
            ((BorderPane)e).setMinSize(screenWidth * (430.0/1920.0), 200);
            setMargin(e, new Insets(0, screenWidth * (30.0/1920.0), 0, screenWidth * (20.0/1920.0)));
        }
    }
}
