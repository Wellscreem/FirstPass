package software.DataBase;

import software.FirstPassController;
import software.Password;

import java.math.BigInteger;
import java.util.List;

public class PasswordManager {

    private static PasswordManager singleInstance = null;

    private final PasswordDataBaseStorage stockage = new PasswordDataBaseStorage();

    public PasswordManager() {
    }

    public static PasswordManager getInstance() {
        if (singleInstance == null)
            singleInstance = new PasswordManager();
        return singleInstance;
    }

    public void createMdp(String login, String nom, String lien, String mdp, String notes) {
        Password password = new Password(login, nom, lien, mdp);
        password.setNotes(notes);
        FirstPassController.listPassword.add(password);
        Object[] keys = Security.generateElGamalKey();
        password.setPublicKey((Object[]) keys[0]);
        password.setPrivateKey((BigInteger) keys[1]);
        Object[] temp = Security.elGamalEncrypt(Security.expoModEncrypt(password.getLogin()), keys);
        password.setEncryptedLogin(new String[]{temp[0].toString(), temp[1].toString()});
        temp = Security.elGamalEncrypt(Security.expoModEncrypt(password.getName()), keys);
        password.setEncryptedName(new String[]{temp[0].toString(), temp[1].toString()});
        temp = Security.elGamalEncrypt(Security.expoModEncrypt(password.getPassword()), keys);
        password.setEncryptedPassword(new String[]{temp[0].toString(), temp[1].toString()});
        stockage.create(password);
    }

    public void updateMdp(Password mdp, boolean lienModif, String ancienLien) {
        if (lienModif) {
            String nouveauLien = mdp.getLink();
            mdp.setLink(ancienLien);
            deleteMdp(mdp);
            mdp.setLink(nouveauLien);
        } else {
            deleteMdp(mdp);
        }
        stockage.updateDataChiffree(mdp);
        createMdp(mdp.getLogin(), mdp.getName(), mdp.getLink(), mdp.getPassword(), mdp.getNotes());
    }

    public void deleteMdp(Password mdp) {
        stockage.delete(mdp);
        FirstPassController.listPassword.remove(mdp);
    }

    public List<Password> getAll() {
        return stockage.getAll();
    }
}
