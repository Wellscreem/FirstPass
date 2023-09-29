package software;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import software.DataBase.PasswordManager;
import software.Languages.English;
import software.Languages.French;
import software.Languages.LanguagesStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirstPassController {

    public static List<Password> listPassword = new ArrayList<>();

    @FXML
    private TextField searchBar;
    @FXML
    private MenuButton languageChoiceBox;
    @FXML
    private AnchorPane acSP;
    @FXML
    private VBox vb;
    @FXML
    private Button btnAdd, btnAllItems, btnPasswords;

    private static FirstPassController singleInstance;

    public FirstPassController() {
        singleInstance = this;
    }

    public static FirstPassController getInstance() {
        return singleInstance;
    }

    public void creerElement() {
        updateLanguage();
        afficherTous();
    }

    public void viderVB() {
        vb.getChildren().removeAll(vb.getChildren());
    }

    public void refreshElement(List<Password> listMdp) {
        updateLanguage();
        for (int i = 0; i < listMdp.size(); i++) {
            if ((i + 1) % 3 == 0) {
                HBoxElement e = new HBoxElement(listMdp.get(i - 2), listMdp.get(i - 1), listMdp.get(i));
                vb.getChildren().add(e);
            } if (i == (listMdp.size() - 1)) {
                if ((i + 1) % 3 == 1) {
                    HBoxElement e = new HBoxElement(listMdp.get(i));
                    vb.getChildren().add(e);
                } else if ((i + 1) % 3 == 2) {
                    HBoxElement e = new HBoxElement(listMdp.get(i - 1), listMdp.get(i));
                    vb.getChildren().add(e);
                }
            }
        }
        for (Node e : vb.getChildren()) {
            VBox.setMargin(e, new Insets(20, 0, 30, 0));
        }
        btnAdd.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(FirstPass.class.getResourceAsStream("img/add.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, false, false, false, false))));
        acSP.setPrefHeight(vb.getMinHeight());
    }

    public static ArrayList<Password> rechercheMdpParNomOuLogin(String nom, String login) {
        ArrayList<Password> listMdp = new ArrayList<>();
        for (Password mdp : listPassword) {
            if (mdp.getName().contains(nom) || mdp.getLogin().contains(login)) {
                listMdp.add(mdp);
            } else {
                if (mdp.getName().contains(nom.replace(" ", ""))) {
                    listMdp.add(mdp);
                }
            }
        }
        return listMdp;
    }

    public void afficherAjouterVue(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("fxml/updateAdd.fxml")));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        UpdateAdd controller = fxmlLoader.getController();
        controller.setType("Add");
        controller.styliser();
        stage.setScene(scene);
        stage.show();
    }

    public void search() {
        viderVB();
        ArrayList<Password> listMdp = rechercheMdpParNomOuLogin(searchBar.getText(), searchBar.getText());
        if (listMdp.isEmpty() && searchBar.getText().equals("")) {
            refreshElement(listPassword);
        } else {
            refreshElement(listMdp);
        }
    }

    public void afficherTous() {
        viderVB();
        searchBar.setText("");
        afficherMdp();
    }

    public void afficherMdp() {
        viderVB();
        searchBar.setText("");
        listPassword = PasswordManager.getInstance().getAll();
        refreshElement(listPassword);
    }

    public void afficherNotes() {
        viderVB();
        searchBar.setText("");
    }

    private void updateLanguage() {
        languageChoiceBox.setText(LanguagesStrategy.getInstance().getLanguage());
        btnAllItems.setText(LanguagesStrategy.getInstance().allElements());
        btnPasswords.setText(LanguagesStrategy.getInstance().passwords());
        if (LanguagesStrategy.getInstance().getLanguage().equals("English")) {
            btnAllItems.getStyleClass().removeAll("btnAccueilFr");
            btnAllItems.getStyleClass().add("btnAccueilEn");
            btnPasswords.getStyleClass().removeAll("btnMdpFr");
            btnPasswords.getStyleClass().add("btnMdpEn");
        } else if (LanguagesStrategy.getInstance().getLanguage().equals("French")) {
            btnAllItems.getStyleClass().removeAll("btnAccueilEn");
            btnAllItems.getStyleClass().add("btnAccueilFr");
            btnPasswords.getStyleClass().removeAll("btnMdpEn");
            btnPasswords.getStyleClass().add("btnMdpFr");
        }
    }

    public void setFr() {
        LanguagesStrategy.getInstance().setLanguage(French.getInstance());
        languageChoiceBox.setText(LanguagesStrategy.getInstance().getLanguage());
        updateLanguage();
    }

    public void setEn() {
        LanguagesStrategy.getInstance().setLanguage(English.getInstance());
        languageChoiceBox.setText(LanguagesStrategy.getInstance().getLanguage());
        updateLanguage();
    }
}
