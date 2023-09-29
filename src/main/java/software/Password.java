package software;

import java.math.BigInteger;
import java.util.Arrays;

public class Password {
    private String login, name, notes, link, password;
    private String[] encryptedLogin, encryptedName, encryptedPassword;
    private Object[] publicKey;
    private BigInteger privateKey;

    public Password(String login, String name, String link, Object[] publicKey, BigInteger privateKey, String password, String notes) {
        this.login = login;
        this.name = name;
        this.link = link;
        this.password = password;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.notes = notes;
    }

    public Password(String login, String name, String link, String password) {
        this.login = login;
        this.name = name;
        this.link = link;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(Object[] publicKey) {
        this.publicKey = publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    public String toString() {
        return "Site link : " + link +
                " \nSite name : " + name +
                " \nLogin : " + login +
                " \nPassword : " + password +
                " \nPublic Key : " + Arrays.toString(publicKey) +
                " \nPrivate Key : " + privateKey +
                "\nNotes : " + notes;
    }

    public String[] getEncryptedLogin() {
        return encryptedLogin;
    }

    public void setEncryptedLogin(String[] encryptedLogin) {
        this.encryptedLogin = encryptedLogin;
    }

    public String[] getEncryptedName() {
        return encryptedName;
    }

    public void setEncryptedName(String[] encryptedName) {
        this.encryptedName = encryptedName;
    }

    public String[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
