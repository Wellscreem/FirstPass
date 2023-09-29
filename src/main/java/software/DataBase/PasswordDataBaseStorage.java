package software.DataBase;

import software.Password;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PasswordDataBaseStorage {

    public void create(Password mdp) {
        Connection cn = DataBaseConnection.getInstance().getConnection();
        String rq = "INSERT INTO Passwords (siteNamePart1, siteNamePart2, siteLink, userNamePart1, userNamePart2, notes, publicKeyPart1, publicKeyPart2, publicKeyPart3, privateKey, passwordPart1, passwordPart2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(rq)) {
            ArrayList<String> arrayTabs = new ArrayList<>();
            arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");
            setTabs(mdp, arrayTabs);
            String note;
            if (mdp.getNotes() == null) note = "";
            else note = mdp.getNotes();
            ps.setString(1, arrayTabs.get(0));
            ps.setString(2, arrayTabs.get(1));
            ps.setString(3, mdp.getLink());
            ps.setString(4, arrayTabs.get(2));
            ps.setString(5, arrayTabs.get(3));
            ps.setString(6, note);
            ps.setString(7, arrayTabs.get(4));
            ps.setString(8, arrayTabs.get(5));
            ps.setString(9, arrayTabs.get(6));
            ps.setString(10, arrayTabs.get(7));
            ps.setString(11, arrayTabs.get(8));
            ps.setString(12, arrayTabs.get(9));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Password mdp) {
        Connection cn = DataBaseConnection.getInstance().getConnection();
        String rq = "DELETE FROM Passwords WHERE siteNamePart1 = ? AND siteNamePart2 = ? AND siteLink = ? AND userNamePart1 = ? AND userNamePart2 = ? AND privateKey = ? AND passwordPart1 = ? AND passwordPart2 = ?";
        try (PreparedStatement ps = cn.prepareStatement(rq)) {
            ArrayList<String> arrayTabs = new ArrayList<>();
            arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");arrayTabs.add("");
            setTabs(mdp, arrayTabs);
            ps.setString(1, arrayTabs.get(0));
            ps.setString(2, arrayTabs.get(1));
            ps.setString(3, mdp.getLink());
            ps.setString(4, arrayTabs.get(2));
            ps.setString(5, arrayTabs.get(3));
            ps.setString(6, arrayTabs.get(7));
            ps.setString(7, arrayTabs.get(8));
            ps.setString(8, arrayTabs.get(9));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Password> getAll() {
        ArrayList<Password> listMdp = new ArrayList<>();
        Connection cn = DataBaseConnection.getInstance().getConnection();
        String rq = "SELECT * FROM Passwords";
        try (PreparedStatement ps = cn.prepareStatement(rq)) {
            try (ResultSet result = ps.executeQuery()){
                while (result.next()) {
                    Object[] nomSiteChiffre = new Object[] {new BigInteger(result.getString("siteNamePart1")),
                            new BigInteger(result.getString("siteNamePart2"))};
                    Object[] clefPublique = new Object[] {new BigInteger(result.getString("publicKeyPart1")),
                            new BigInteger(result.getString("publicKeyPart2")),
                            new BigInteger(result.getString("publicKeyPart3"))};
                    BigInteger clefPrivee = new BigInteger(result.getString("privateKey"));
                    Object[] keys = new Object[] {clefPublique, clefPrivee};
                    String lienSite = result.getString("siteLink");
                    String notes = result.getString("notes");
                    Object[] userNameChiffre = new Object[] {new BigInteger(result.getString("userNamePart1")),
                            new BigInteger(result.getString("userNamePart2"))};
                    Object[] mdpChiffre = new Object[] {new BigInteger(result.getString("passwordPart1")),
                            new BigInteger(result.getString("passwordPart2"))};
                    String nomSite = Security.expoModDecrypt(Security.elGamalDecrypt(nomSiteChiffre, keys));
                    String userName = Security.expoModDecrypt(Security.elGamalDecrypt(userNameChiffre, keys));
                    String mdp = Security.expoModDecrypt(Security.elGamalDecrypt(mdpChiffre, keys));
                    Password password = new Password(userName, nomSite, lienSite, clefPublique, clefPrivee, mdp, notes);
                    password.setEncryptedLogin(new String[] {userNameChiffre[0].toString(), userNameChiffre[1].toString()});
                    password.setEncryptedName(new String[] {nomSiteChiffre[0].toString(), nomSiteChiffre[1].toString()});
                    password.setEncryptedPassword(new String[] {mdpChiffre[0].toString(), mdpChiffre[1].toString()});
                    listMdp.add(password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listMdp;
    }

    private void setTabs(Password mdp, ArrayList<String> arrayTabs) {
        String[] nom = mdp.getEncryptedName();
        String[] userName = mdp.getEncryptedLogin();
        String[] clefPublique = {mdp.getPublicKey()[0].toString(), mdp.getPublicKey()[1].toString(), mdp.getPublicKey()[2].toString()};
        String[] motDePasse = mdp.getEncryptedPassword();
        arrayTabs.set(0, nom[0]);
        arrayTabs.set(1, nom[1]);
        arrayTabs.set(2, userName[0]);
        arrayTabs.set(3, userName[1]);
        arrayTabs.set(4, clefPublique[0]);
        arrayTabs.set(5, clefPublique[1]);
        arrayTabs.set(6, clefPublique[2]);
        arrayTabs.set(7, mdp.getPrivateKey().toString());
        arrayTabs.set(8, motDePasse[0]);
        arrayTabs.set(9, motDePasse[1]);
    }

    public void updateDataChiffree(Password mdp) {
        Object[] keys = new Object[] {mdp.getPublicKey(), mdp.getPrivateKey()};
        Object[] temp = Security.elGamalEncrypt(Security.expoModEncrypt(mdp.getLogin()), keys);
        mdp.setEncryptedLogin(new String[] {temp[0].toString(), temp[1].toString()});
        temp = Security.elGamalEncrypt(Security.expoModEncrypt(mdp.getName()), keys);
        mdp.setEncryptedName(new String[] {temp[0].toString(), temp[1].toString()});
        temp = Security.elGamalEncrypt(Security.expoModEncrypt(mdp.getPassword()), keys);
        mdp.setEncryptedPassword(new String[] {temp[0].toString(), temp[1].toString()});
    }
}
