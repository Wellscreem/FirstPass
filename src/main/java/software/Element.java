package software;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import software.DataBase.PasswordManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class Element extends BorderPane {
    private Password mdp;
    public Element(Password mdp) {
        this.mdp = mdp;

        Label nameWebSite = new Label(mdp.getName());
        nameWebSite.setStyle("-fx-font-weight: bolder; -fx-font-size: 20;");

        //Label pour le nom d'utilisateur utilisé
        Label userNameWebSite = new Label(mdp.getLogin());
        userNameWebSite.setStyle("-fx-font-size: 13px");

        //VBox pour les labels
        VBox vbLabel = new VBox(nameWebSite, userNameWebSite);
        vbLabel.setMinWidth(250);
        vbLabel.setMaxWidth(250);
        vbLabel.setPadding(new Insets(0, 0, 0, 5));

        //Bouton pour copier le mdp
        Button password = new Button();
        password.setMinSize(40, 40);
        password.setPrefSize(40, 40);
        password.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(FirstPass.class.getResourceAsStream("img/password.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(40, 40, false, false, false, false))));
        password.setOnAction(a -> {
            String myString = mdp.getPassword();
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });

        //Bouton pour copier le nom d'utilisateur
        Button userName = new Button();
        userName.setMinSize(40, 40);
        userName.setPrefSize(40, 40);
        userName.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(FirstPass.class.getResourceAsStream("img/user-name.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(40, 40, false, false, false, false))));
        userName.setOnAction(e -> {
            String myString = mdp.getLogin();
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });

        //Bouton pour modifier les valeurs dans la BDD
        Button update = new Button();
        update.setMinSize(40, 40);
        update.setPrefSize(40, 40);
        update.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(FirstPass.class.getResourceAsStream("img/update.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(40, 40, false, false, false, false))));
        update.setOnAction(e -> {
            FXMLLoader fxmlLoader = new FXMLLoader(FirstPass.class.getResource("fxml/updateAdd.fxml"));
            try {
                Stage stage = (Stage)this.getScene().getWindow();
                stage.setScene(new Scene(fxmlLoader.load(), 1920, 1010));
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            UpdateAdd modif = fxmlLoader.getController();
            modif.setType("Update");
            modif.remplirChamps(mdp);
        });

        //Bouton pour supprimer l'élément dans la BDD
        Button delete = new Button();
        delete.setMinSize(40, 40);
        delete.setPrefSize(40, 40);
        delete.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(FirstPass.class.getResourceAsStream("img/delete.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(40, 40, false, false, false, false))));
        delete.setOnAction(e -> {
            PasswordManager.getInstance().deleteMdp(mdp);
            FirstPassController.getInstance().viderVB();
            FirstPassController.getInstance().refreshElement(FirstPassController.listPassword);
        });

        //HBox pour contenir les boutons
        HBox hbBtn = new HBox(update, delete, userName, password);
        hbBtn.setMinWidth(70);
        hbBtn.setAlignment(Pos.BASELINE_RIGHT);

        //HBox pour le bas
        HBox hbBas = new HBox(vbLabel, hbBtn);
        hbBas.setPadding(new Insets(0, 10, 10, 5));

        //Ajout de la HBox dans le BorderPane
        this.setBottom(hbBas);

        hbBtn.setVisible(false);

        this.setOnMouseEntered(e -> hbBtn.setVisible(true));

        this.setOnMouseExited(e -> hbBtn.setVisible(false));

        InputStream file = FirstPassController.class.getResourceAsStream("logoSites/" + clearUrl(mdp.getLink()) + ".png");
        if (file == null) {
            try {
                callAPI();
                file = FirstPassController.class.getResourceAsStream("logoSites/" + clearUrl(mdp.getLink()) + ".png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (file == null) {
                file = Objects.requireNonNull(FirstPassController.class.getResourceAsStream("logoSites/vide.png"));
            }
        }
        ImageView logoSite = new ImageView(new Image(file));
        this.setCenter(logoSite);
    }

    private void callAPI() throws IOException {
        String urlClear = clearUrl(mdp.getLink());
        URL url = new URL("https://logo.clearbit.com/" + urlClear);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                BufferedImage image = ImageIO.read(response.body().byteStream());
                //File outputFile = new File("" + urlClear + ".png"); //TODO modifier quand j'aurais créer le folder
                //ImageIO.write(image, "png", outputFile);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private String clearUrl(String url) {
        if (url.contains("//")) {
            url = url.substring(url.indexOf("//") + 2);
        }
        if (url.contains("www.")) {
            url = url.substring(4);
        }
        if (url.contains("/")) {
            url = url.substring(0, url.indexOf('/'));
        }
        return url;
    }
}
