package software;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


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
        this.setMaxSize(1599, 200);
        this.setMinSize(1599, 200);
        for (Node e : this.getChildren()) {
            e.getStyleClass().add("element");
            ((BorderPane)e).setMaxSize(430, 200);
            ((BorderPane)e).setMinSize(430, 200);
            setMargin(e, new Insets(0, 30, 0, 20));
        }
    }
}
