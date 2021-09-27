package modell;

import java.io.Serializable;
import java.util.ArrayList;

public class Felhasznalo implements Serializable {

    private String nev;
    private ArrayList<Elelmiszer> kedvencek;
    private String honnan;
    private String nyelv;

    public Felhasznalo() {
        this.nev = "Vendég";
        this.kedvencek = new ArrayList<>();
        this.honnan = System.getProperty("user.country");
        this.nyelv = System.getProperty("user.language");
    }

    public String getNev() {
        String nev = this.nev;
        return nev;
    }

    public String getHonnan() {
        String honnan = this.honnan;
        return honnan;
    }

    public String getNyelv() {
        String nyelv = this.nyelv;
        return nyelv;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public ArrayList<Elelmiszer> getKedvencek() {
        ArrayList<Elelmiszer> kedvencek = new ArrayList<>();
        kedvencek.addAll(this.kedvencek);
        return kedvencek;
    }

    public void setKedvencek(ArrayList<Elelmiszer> kedvencek) {
        this.kedvencek = kedvencek;
    }

    public void addKedvenc(Elelmiszer elelmiszer) {
        if (!this.kedvencek.contains(elelmiszer)) {
            this.kedvencek.add(elelmiszer);
        }
    }

    @Override
    public String toString() {
        StringBuilder szov = new StringBuilder("Név: ");
        szov.append(this.nev).append(", Kedvencek: ");
        for (Elelmiszer elelmiszer : this.kedvencek) {
            szov.append(elelmiszer.getNev());
        }
        szov.append("honnan=").append(this.honnan).append(", nyelv:").append(this.nyelv);
        return szov.toString();
    }

}
