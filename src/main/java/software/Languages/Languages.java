package software.Languages;

public interface Languages {

    static Languages getInstance() {
        return French.getInstance();
    }

    String updateText();

    String createText();

    String nameText();

    String loginText();

    String passwordText();

    String generateText();

    String specialCharText();

    String saveText();

    String cancelText();

    String allElementsText();

    String passwordsText();

    String errorNoCaseSelectedText();
}
