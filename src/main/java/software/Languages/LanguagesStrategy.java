package software.Languages;

public class LanguagesStrategy {

    private static LanguagesStrategy singleInstance = null;

    private Languages selectedLanguage;

    public static LanguagesStrategy getInstance() {
        if (singleInstance == null)
            singleInstance = new LanguagesStrategy();
        if (singleInstance.selectedLanguage == null)
            singleInstance.setLanguage(French.getInstance());
        return singleInstance;
    }

    public void setLanguage(Languages language) {
        selectedLanguage = language;
    }

    public String getLanguage() {
        if (selectedLanguage.getClass() == French.class)
            return "French";
        if (selectedLanguage.getClass() == English.class)
            return "English";
        return null;
    }

    public String updateText() {
       return selectedLanguage.updateText();
    }

    public String createText() {
        return selectedLanguage.createText();
    }

    public String name() {
        return selectedLanguage.nameText();
    }

    public String login() {
        return selectedLanguage.loginText();
    }

    public String password() {
        return selectedLanguage.passwordText();
    }

    public String generate() {
        return selectedLanguage.generateText();
    }

    public String specialChar() {
        return selectedLanguage.specialCharText();
    }

    public String save() {
        return selectedLanguage.saveText();
    }

    public String cancel() {
        return selectedLanguage.cancelText();
    }

    public String allElements() {
        return selectedLanguage.allElementsText();
    }

    public String passwords() {
        return selectedLanguage.passwordsText();
    }

    public String errorNoCaseSelected() {
        return selectedLanguage.errorNoCaseSelectedText();
    }
}
