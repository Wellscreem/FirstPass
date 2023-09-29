package software.Languages;

public class French implements Languages {

    private static French singleInstance = null;

    public static Languages getInstance() {
        if (singleInstance == null)
            singleInstance = new French();
        return singleInstance;
    }

    @Override
    public String updateText() {
        return "Modifier le mot de passe";
    }

    @Override
    public String createText() {
        return "Ajouter un mot de passe";
    }

    @Override
    public String nameText() {
        return "Nom";
    }

    @Override
    public String loginText() {
        return "Nom d'utilisateur";
    }

    @Override
    public String passwordText() {
        return "Mot de passe";
    }

    @Override
    public String generateText() {
        return "Générer un mot de passe";
    }

    @Override
    public String specialCharText() {
        return "Caractères spéciaux";
    }

    @Override
    public String saveText() {
        return "Sauvegarder";
    }

    @Override
    public String cancelText() {
        return "Annuler";
    }

    @Override
    public String allElementsText() {
        return "Tous les éléments";
    }

    @Override
    public String passwordsText() {
        return "Mots de passe";
    }

    @Override
    public String errorNoCaseSelectedText() {
        return "Veuillez cocher au moins une des cases";
    }
}
