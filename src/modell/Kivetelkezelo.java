package modell;

import defaul_Class.Vezerlo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import modell.tarolok.Alap;
import modell.tarolok.Kitol;

public class Kivetelkezelo extends Exception implements Alap {

    private Vezerlo vezerlo;
    private HashMap<String, Integer> statisztika;
    private ArrayList<String> hibakod;
    private ArrayList<String> hibaszoveg;

    public Kivetelkezelo(Vezerlo vez) {
        this.vezerlo = vez;
        this.statisztika = new HashMap<>();
        this.hibaszoveg = new ArrayList<>();
        this.hibakod = new ArrayList<>();
    }

    public void hibakodokKiirasaConsole() {
        for (int i = 0; i < hibakod.size() && i < hibaszoveg.size(); i++) {
            System.out.println(hibakod.get(i) + " :\t" + hibaszoveg.get(i));
        }
    }

    public HashMap<String, Integer> getStatisztika() {
        HashMap<String, Integer> statisztika = new HashMap<>();
        for (Map.Entry<String, Integer> elem : this.statisztika.entrySet()) {
            String key = elem.getKey();
            Integer value = elem.getValue();
            statisztika.put(key, value);
        }
        return statisztika;
    }

    public void kezeles(Exception e, String kitol) {
        String code = "#000", kiSzov = "Ismeretlen hiba";
        if (e instanceof IOException) {
            if (kitol.equals(Kitol.ELOMENYLOADING.toString())) {
                code = "#006";
            } else if (kitol.equals(Kitol.STATISZTIKA.toString())) {
                code = "#003";
            } else if (kitol.equals(Kitol.HIBAKODOLVASAS.toString())) {
                code = "#000";
            } else if (kitol.equals(Kitol.ELELMISZEUP.toString())) {
                code = "#010";
            } else if (kitol.equals(Kitol.LOGIN.toString())) {
                code = "#009";
            } else if (kitol.equals(Kitol.KAJAK.toString())) {
                code = "#014";
            }
//            switch (kitol) {
//                case Kitol.ELOMENYLOADING.toString():
//                    code = "#006";
//                    break;
//                case Kitol.STATISZTIKA.toString():
//                    code = "#003";
//                    break;
//                case Kitol.HIBAKODOLVASAS.toString():
//                    code = "#000";
//                    break;
//                case Kitol.ELELMISZEUP.toString():
//                    code = "#010";
//                    break;
//                case Kitol.LOGIN.toString():
//                    code = "#009";
//                    break;
//                default:
//                    code = "";
//            }
        } else if (e instanceof SQLException) {
            if (kitol.equals(Kitol.LOGIN.toString())) {
                code = "#009";
            } else if (kitol.equals(Kitol.REGI.toString())) {
                code = "#011";
            } else if (kitol.equals(Kitol.STATISZTIKA.toString())) {
                code = "#012";
            }
//            switch (kitol) {
//                case Kitol.LOGIN.toString():
//                    code = "#009";
//                    break;
//                case Kitol.REGI.toString():
//                    code = "#011";
//                    break;
//                case Kitol.STATISZTIKA.toString():
//                    code = "#012";
//                    break;
//                default:
//                    code = "";
//            }
        } else if (e instanceof ClassNotFoundException) {
            if (kitol.equals(Kitol.ELOMENYLOADING.toString())) {
                code = "#007";
            } else if (kitol.equals(Kitol.STATISZTIKA.toString())) {
                code = "#008";
            } else if (kitol.equals(Kitol.LOGIN.toString()) || kitol.equals(Kitol.REGI.toString())) {
                code = "#013";
            }
//            switch (kitol) {
//                case Kitol.ELOMENYLOADING.toString():
//                    code = "#007";
//                    break;
//                case Kitol.STATISZTIKA.toString():
//                    code = "#008";
//                    break;
//                case Kitol.LOGIN.toString():
//                    code = "#013";
//                    break;
//                case Kitol.REGI.toString():
//                    code = "#013";
//                    break;
//                default:
//                    code = "";
//            }
        } else if (e instanceof NumberFormatException) {
            if (kitol.equals(Kitol.SZAMITAS.toString())) {
                code = "#002";
            }
//            switch (kitol) {
//                case Kitol.SZAMITAS.toString():
//                    code = "#002";
//                    break;
//                default:
//                    code = "";
//            }
        } else if (e instanceof NullPointerException || e == null) {
            if (kitol.equals(Kitol.URESERTEK.toString())) {
                code = "#002";
            } else if (kitol.equals(Kitol.KAJALEIRAS.toString())) {
                code = "#015";
            }
//            switch (kitol) {
//                case Kitol.URESERTEK.toString():
//                    code = "#002";
//                    break;
//                default:
//                    throw new AssertionError();
//            }
        }
        int i = talalt(hibakod, code);
        if (i > 0) {
            kiSzov = hibaszoveg.get(i);
            vezerlo.hibaAblak(kiSzov + ": " + this.hibakod.get(i) /*+ ": " + e.getMessage()*/);
        } else {
            kiSzov = "Nem Sikerült betölteni vagy megtalálni a hibákat tartalmazó fájlt";
            vezerlo.hibaAblak(kiSzov + " : " + code);
        }
        System.out.println(e.getMessage());
        e.printStackTrace();
        setStatisztika(code);
    }

    public void setStatisztika(String kod) {
        if (this.statisztika.containsKey(kod)) {
            int ertek = this.statisztika.get(kod);
            ertek++;
            this.statisztika.put(kod, ertek);
        } else {
            this.statisztika.put(kod, 1);
        }
    }

    private int talalt(ArrayList<String> hol, String mit) {
        int i = 0, N = hol.size();
        while (i < N && !(hol.get(i).equals(mit))) {
            i++;
        }
        if (i < N) {
            return i;
        }
        return 0;
    }

    public void kodokfeltolt(String szov) {
        String[] s = szov.split(";");
        if (talalt(hibakod, s[0]) <= 0) {
            hibakod.add(s[0].trim());
            hibaszoveg.add(s[1]);
        }
    }
}
