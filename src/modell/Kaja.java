package modell;

import modell.tarolok.Alap;

public class Kaja implements Alap {

    private String nev, elkezítés, hozzavalok;
    private int osszErtek;

    public Kaja(String sor) {
        String[] s = sor.split(";");
        if (s.length > 0) {
            String[] tomb = s[0].split(":");
            this.nev = tomb[0];
            this.osszErtek = Integer.parseInt(tomb[1].replace("ch", ""));
            this.hozzavalok = s[1];
            if (s.length == 3) {
                this.elkezítés = s[2];
            } else {
                this.elkezítés = "Nincs leírás";
            }
        }
    }

    public String getNev() {
        StringBuilder nev = new StringBuilder(this.nev);
        nev.append(" (").append(osszErtek).append("ch)");
        return nev.toString();
    }

    public String getElkezítés() {
        String elkezítés = this.elkezítés;
        return elkezítés;
    }

    public String getHozzávalok() {
        String hozzavalok = this.hozzavalok;
        return hozzavalok;
    }

    @Override
    public String toString() {
        StringBuilder szov = new StringBuilder();
        szov.append(getNev()).append(":\n\tHozzávalók: ");
        String[] s = this.hozzavalok.split("\\|");
        for (String elem : s) {
            szov.append(elem).append(", ");
        }
        szov.append("\n\tElkészítés: ").append(elkezítés);
        return szov.toString();
    }
}
