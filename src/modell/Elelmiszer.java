package modell;

import java.io.Serializable;

public class Elelmiszer implements Serializable {

    private String nev;
    private double ch, feherje, zsir;

    public Elelmiszer(String sor) {
        String[] s = sor.split(";");
        this.nev = s[0];
        this.ch = Double.parseDouble(s[1]);
        switch (s.length) {
            case 3:
                this.feherje = Double.parseDouble(s[2]);
                this.zsir = 0.0;
                break;
            case 4:
                this.feherje = Double.parseDouble(s[2]);
                this.zsir = Double.parseDouble(s[3]);
                break;
            default:
                this.feherje = 0.0;
                this.zsir = 0.0;
                break;
        }
    }

    public String getNev() {
        return nev;
    }

    public double getCh() {
        return ch;
    }

    public double getFeherje() {
        return feherje;
    }

    public double getZsir() {
        return zsir;
    }

    @Override
    public String toString() {
        StringBuilder szov = new StringBuilder();
        szov.append(this.nev).append(":\t(ch/feherje/zsír)\n\t\t\t").append(ch).append(",   ").append(feherje).append(",   ").append(zsir);
        return szov.toString();
    }

}
