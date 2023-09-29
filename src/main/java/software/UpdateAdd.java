package software;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import software.DataBase.PasswordManager;
import software.Languages.LanguagesStrategy;

import java.io.IOException;
import java.util.Objects;

public class UpdateAdd {

    private String type;
    private Password password;
    private String mdpAjoutTemp;

    @FXML
    private Slider sliderLengthMdp;
    @FXML
    private Label labelTitre, labelSliderLenghtMdp, labelUserName, labelName, labelPassword, labelGeneratePassword;
    @FXML
    private Button valider, oeil, saveBtn, cancelBtn;
    @FXML
    private TextField nom, url, userName, mdp, tfMdpGenerer;
    @FXML
    private TextArea notes;
    @FXML
    private HBox hbPointsNoirs;
    @FXML
    private StackPane spMdp, spAc;
    @FXML
    private CheckBox cbMin, cbNb, cbMaj, cbCharSpe;

    private boolean showMdp = false;
    private boolean peutValider = false;
    private boolean mdpGenerated = false;
    private int nbChar = 18;


    public void initialize() {
        sliderLengthMdp.valueProperty().addListener((observable, oldValue, newValue) -> {
            nbChar = newValue.intValue();
            labelSliderLenghtMdp.setText(nbChar + "");
            updateMdpGenerated();
        });
    }

    public void setType(String choix) {
        type = choix;
        if (type.equals("Add")) {
            labelTitre.setText(LanguagesStrategy.getInstance().createText());
        } else if (type.equals("Update")) {
            labelTitre.setText(LanguagesStrategy.getInstance().updateText());
        }
    }

    public void modifierMdp() {
        if (type.equals("Update")) {
            password.setName(nom.getText());
            password.setLogin(userName.getText());
            password.setNotes(notes.getText());
            if (!mdp.getText().equals(""))
                password.setPassword(mdp.getText());
            String ancienLien = "";
            boolean lienModif = !url.getText().equals(password.getLink());
            if (lienModif) {
                ancienLien = password.getLink();
                password.setLink(url.getText());
            }
            PasswordManager.getInstance().updateMdp(password, lienModif, ancienLien);
        }
    }

    public void remplirChamps(Password password) {
        if (type.equals("Update")) {
            styliser();
            this.password = password;
            nom.setText(password.getName());
            url.setText(password.getLink());
            userName.setText(password.getLogin());
            notes.setText(password.getNotes());
        }
    }

    public void activerValider() {
        valider.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(FirstPass.class.getResourceAsStream("img/valider_vert.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(40, 40, false, false, false, false))));
        peutValider = true;
    }

    public void backToMainMenu(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("fxml/mainMenu.fxml")));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        FirstPassController controller = fxmlLoader.getController();
        controller.refreshElement(FirstPassController.listPassword);
        stage.setScene(scene);
        stage.show();
    }

    public void executeValider(ActionEvent event) {
        if (peutValider) {
            if (type.equals("Update")) {
                modifierMdp();
            } else if (type.equals("Add")) {
                PasswordManager.getInstance().createMdp(userName.getText(), nom.getText(), url.getText(), mdp.getText(), notes.getText());
            }
            backToMainMenu(event);
        }
    }

    public void switchMdp() {
        if (showMdp) {
            oeil.getStyleClass().remove(1);
            oeil.getStyleClass().add("btnOeil");
            if (type.equals("Add") || (type.equals("Update") && mdpGenerated)) {
                mdpAjoutTemp = mdp.getText();
            }
            mdp.setText("");
        }
        else {
            if (type.equals("Update")) {
                if (mdpGenerated) {
                    mdp.setText(mdpAjoutTemp);
                } else {
                    mdp.setText(password.getPassword());
                }
            } else if (type.equals("Add")) {
                mdp.setText(mdpAjoutTemp);
            }
            oeil.getStyleClass().remove(1);
            oeil.getStyleClass().add("btnOeilBarrer");
        }
        showMdp = !showMdp;
        spMdp.getChildren().get(spMdp.getChildren().size()-1).toBack();
    }

    private void styliserAfficherCacherMdp() {
        for (int i = 0; i < 11; i++) {
            ImageView img = new ImageView(new Image(Objects.requireNonNull(FirstPass.class.getResourceAsStream("img/pointNoirs.png"))));
            img.setFitHeight(17);
            img.setFitWidth(17);
            hbPointsNoirs.getChildren().add(img);
        }
        if (type.equals("Add")) {
            switchMdp();
        }
    }

    public void styliser() {
        updateLanguage();
        swapSPGererMdp();
        styliserAfficherCacherMdp();
    }

    public void swapSPGererMdp() {
        spAc.getChildren().get(spAc.getChildren().size()-1).toBack();
        if (spAc.getChildren().get(1).getId() != null && spAc.getChildren().get(1).getId().equals("acGenererMDP")) {
            tfMdpGenerer.setText(genereMdp());
        }
    }

    public void updateMdpGenerated() {
        tfMdpGenerer.setText(genereMdp());
    }

    private String genereMdp() {
        boolean selected = cbCharSpe.isSelected() || cbMaj.isSelected() || cbMin.isSelected() || cbNb.isSelected();
        String alphaNumericStr = "";
        if (cbCharSpe.isSelected())
            alphaNumericStr += "!#$%&()*+,-./:;<=>?@[]^_`{|}~";
        if (cbMaj.isSelected())
            alphaNumericStr += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (cbMin.isSelected())
            alphaNumericStr += "abcdefghijklmnopqrstuvxyz";
        if (cbNb.isSelected())
            alphaNumericStr += "0123456789";

        if (selected) {
            StringBuilder s = new StringBuilder(nbChar);
            int i;
            for (i = 0; i < nbChar; i++) {
                int ch = (int) (alphaNumericStr.length() * Math.random());
                s.append(alphaNumericStr.charAt(ch));
            }
            return s.toString();
        } else {
            return LanguagesStrategy.getInstance().errorNoCaseSelected();
        }
    }

    public void activerGenererMdp() {
        if (!tfMdpGenerer.getText().equals(LanguagesStrategy.getInstance().errorNoCaseSelected())) {
            swapSPGererMdp();
            if (spMdp.getChildren().get(1).getId().equals("hbPointsNoirs")) {
                switchMdp();
            }
            mdp.setText(tfMdpGenerer.getText());
            mdpGenerated = true;
        }
        if (type.equals("Update")) {
            activerValider();
        }
    }

    private void updateLanguage() {
        labelName.setText(LanguagesStrategy.getInstance().name());
        labelPassword.setText(LanguagesStrategy.getInstance().password());
        labelUserName.setText(LanguagesStrategy.getInstance().login());
        labelGeneratePassword.setText(LanguagesStrategy.getInstance().generate());
        cbCharSpe.setText(LanguagesStrategy.getInstance().specialChar());
        saveBtn.setText(LanguagesStrategy.getInstance().save());
        cancelBtn.setText(LanguagesStrategy.getInstance().cancel());
    }
}
