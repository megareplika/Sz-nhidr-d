package modell;

import java.sql.ResultSet;
import defaul_Class.Vezerlo;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import modell.tarolok.Alap;

public class Kapcsolat implements Alap {

    private Vezerlo vezerlo;
    private Connection kapcsolat;
    private String ido;

    public Kapcsolat(Vezerlo vez) {
        this.vezerlo = vez;
    }

    private void kapcsolatNyit() throws ClassNotFoundException, SQLException {
        Class.forName(DATABASE_DRIVER);
        kapcsolat = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
    }

    private void kapcsolatzar() throws SQLException {
        if (!kapcsolat.isClosed()) {
            kapcsolat.close();
        }
    }

    public void putStatisztika(String adat) throws ClassNotFoundException, SQLException {
        kapcsolatNyit();
        if (!kapcsolat.isClosed()) {
            String[] s = adat.split("\\|");
            ido = s[0];
            String ablakmeret = s[1].split(": ")[1];
            double haszmemo = Double.parseDouble(s[2].split(": ")[1]);
            String openido = s[3].split(": ")[1];
            String closeido = s[4].split(": ")[1];
            String elozmeny = s[6].split(": ")[1];
            int kilepszan = Integer.parseInt(s[7].split(": ")[1]);
            StringBuilder adsa = new StringBuilder();
            adsa.append("'").append(ido.replaceAll(":", "-")).append(VALASZTOSQL).append(ablakmeret).append(VALASZTOSQL).append(haszmemo).append(VALASZTOSQL).append(openido.trim()).append(VALASZTOSQL).append(closeido.trim()).append(VALASZTOSQL).append(kilepszan).append("'");
            String sql = STAT_INSERT.replace("###Ertek###", adsa.toString());
//            sql.replace("###Ertek###", adsa.toString());
            System.out.println(sql);
            System.out.println(closeido);
            System.out.println(openido);
            kapcsolat.createStatement().execute(sql);
        }
        kapcsolatzar();
    }

    public void putHibaStat(HashMap<String, Integer> statisztika) throws ClassNotFoundException, SQLException {
        kapcsolatNyit();
        if (!kapcsolat.isClosed()) {
            for (Map.Entry<String, Integer> elem : statisztika.entrySet()) {
                String key = elem.getKey();
                Integer value = elem.getValue();
                StringBuilder szov = new StringBuilder();
                szov.append(key).append("','").append(ido.replaceAll(":", "-").replaceAll(" ", "")).append("')");
                String sql = HIBASTAT_INSERT.replace("###Ertek###", szov.toString());
                System.out.println(sql);
                kapcsolat.createStatement().execute(sql.toString());
            }
        }
        kapcsolatzar();
    }

    public boolean login(String felhasznalo, String jelszo) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException {
        kapcsolatNyit();
        ResultSet eredmeny;
        short s = 0;
        if (!kapcsolat.isClosed()) {
            jelszo = titkosit(jelszo).toString(16);
            String szov = LOGIN.replace("###NEV###", felhasznalo).replace("###PASS###", jelszo);
//            szov.replace("###NEV###", felhasznalo);
//            szov.replace("###PASS###", jelszo);
            System.out.println(szov);
            eredmeny = kapcsolat.createStatement().executeQuery(szov.toString());
            s = Short.parseShort(eredmeny.getString(0));
        }
        kapcsolatzar();
        return s == 1;
    }

    private BigInteger titkosit(String mit) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(mit.getBytes("UTF-8"));
        byte[] titkosTomb = md.digest();
        return new BigInteger(1, titkosTomb);
    }

    public void reg(String nev, char[] pass, String szul) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        kapcsolatNyit();
        if (!kapcsolat.isClosed()) {
//            StringBuilder pass2 = new StringBuilder(pass.toString());
//            for (char c : pass) {
//                pass2.append(c);
//            }
            StringBuilder pass2 = new StringBuilder(titkosit(pass.toString()).toString(16));
            StringBuilder adatok = new StringBuilder();
            adatok.append("'").append(nev).append(VALASZTOSQL).append(pass2.toString());
            if (szul.length() > 0) {
                adatok.append(VALASZTOSQL).append(szul);
            }
            adatok.append("'");
            String sql = REGISZTRALAS.replace("###Ertek###", adatok);
            System.out.println(sql + "\n" + adatok.toString());
//            kapcsolat.createStatement().execute(sql);
        }
        kapcsolatzar();
    }

    public ResultSet AdminadatokLekerdez(String sql) throws SQLException, ClassNotFoundException {
//        String elelmiszerAdatok = ElelmiszerAdatok;
        ResultSet ereddmeny = null;
        kapcsolatNyit();
        if (!kapcsolat.isClosed()) {
            ereddmeny = kapcsolat.createStatement().executeQuery(sql);
        }
        kapcsolatzar();
        return ereddmeny;
    }
}
