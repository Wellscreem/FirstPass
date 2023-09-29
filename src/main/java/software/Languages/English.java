package software.Languages;

public class English implements Languages {

    private static English singleInstance = null;

    public static Languages getInstance() {
        if (singleInstance == null)
            singleInstance = new English();
        return singleInstance;
    }

    @Override
    public String updateText() {
        return "Update password";
    }

    @Override
    public String createText() {
        return "Create a new password";
    }

    @Override
    public String nameText() {
        return "Name";
    }

    @Override
    public String loginText() {
        return "User name";
    }

    @Override
    public String passwordText() {
        return "Password";
    }

    @Override
    public String generateText() {
        return "Generate a password";
    }

    @Override
    public String specialCharText() {
        return "Special characters";
    }

    @Override
    public String saveText() {
        return "Save";
    }

    @Override
    public String cancelText() {
        return "Cancel";
    }

    @Override
    public String allElementsText() {
        return "All Items";
    }

    @Override
    public String passwordsText() {
        return "Passwords";
    }

    @Override
    public String errorNoCaseSelectedText() {
        return "You need to select at least one case";
    }
}
