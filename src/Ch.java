
import defaul_Class.Vezerlo;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modell.Elelmiszer;
import modell.FileKezelo;
import modell.Kapcsolat;
import modell.Kivetelkezelo;
import modell.Felhasznalo;
import modell.Kaja;
import modell.Szamolas;
import modell.tarolok.Kitol;
import nezet.AdminFelulet;
import nezet.Foablak;
import nezet.Loading;

public class Ch extends Vezerlo {

    private Foablak ablak;
    private FileKezelo fileKibe;
    private Kivetelkezelo kivetel;
    private ArrayList<Elelmiszer> elelmiszerek;
    private ArrayList<Kaja> kajak;
    private Kapcsolat connect;
    private Loading betolt;
    private Felhasznalo felhasznalo;
    private ArrayList<String> forrasok;
    private String op;
    private Szamolas szamolas;
    private AdminFelulet admin;

    public Ch() {
        init();
        hibakodFeltolt();
//        kivetel.hibakodokKiirasaConsole();
        felhasználóreset();
        ablak.frissitCim(felhasznalo.getNev());
        elozmenyekBetolt();
//        System.out.println(felhasznalo);
        this.betolt.Redy();
//        System.out.println("redy");
//        ablak.setVisible(true);
    }

    @Override
    protected void init() {
        this.fileKibe = new FileKezelo(this);
        this.kajak = new ArrayList<>();
        this.elelmiszerek = new ArrayList<>();
        elelmiszerkFeltolt();
        setKajak();
        this.ablak = new Foablak(this);
        this.kivetel = new Kivetelkezelo(this);
        this.connect = new Kapcsolat(this);
        this.felhasznalo = new Felhasznalo();
        this.betolt = new Loading(this);
        this.szamolas = new Szamolas(this);
        this.ablak.menuepit();
        forrasok();
    }

    private void setKajak() {
        try {
            List<String> lista = this.fileKibe.filebe(KAJA_FAJL);
            for (int i = 1; i < lista.size(); i++) {
                /*egy sor = nev:ertekeCH;hozzávalók;elkészítés*/
                this.kajak.add(new Kaja(lista.get(i)));
            }
        } catch (IOException e) {
            this.kivetel.kezeles(e, Kitol.KAJAK.toString());
        }
    }

    private void felhasználóreset() {
        try {
            this.felhasznalo = this.fileKibe.felhasznalobetolt();
        } catch (IOException | ClassNotFoundException e) {
            this.kivetel.kezeles(e, Kitol.FELHASZNALO_RESET.toString());
        }
    }

    private void forrasok() {
        this.forrasok = new ArrayList<>();
        this.forrasok.add("idő beállitása és megjelenítése: https://www.youtube.com/watch?v=21r5FkzSUqU&t=699s (2019.május 16)");
        this.forrasok.add("https://www.educba.com/swing-components-in-java/ (2021 aprilis elején nézve)");
        this.forrasok.add("internet detektálása: https://stackoverflow.com/questions/6999306/java-quickly-check-for-network-conncetion (2011.08.11)");
        this.forrasok.add("mysql- hez való kapcsolódás: https://www.javapoint.com/example-to-connect-to-the-mysql-database");
        this.forrasok.add("https://stackoverflow.com/questions/40336374/how-do-i-check-if-a-java-string-contains-at-least-one-capital-letter-lowercase (password 2017.)");
        this.forrasok.add("https://stackoverflow.com/questions/415953/how-can-i-generate-an-md5-hash (md5 2009.)");
        this.forrasok.add("(Dátum formázás átalakítás) https://mkyong.com/java/java-convert-date-to-calendar-example/ (2015.01.22)");
        this.forrasok.add("(Dátum formázás átalakítás) https://stackoverflow.com/questions/4927856/how-to-calculate-time-difference-in-java/54428410 (2011)");
        this.forrasok.add("(double formázás) https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places");
        this.forrasok.add("(Curzor) https://www.splessons.com/lesson/swing-cursors/");
    }

    private void elozmenyekBetolt() {
        try {
//            ablak.setElozmenyLista((ArrayList<String>) fileKibe.beolvasBinaris(ELOZMENYEK));
            ablak.setElozmenyLista(fileKibe.elozmenyVisszaallit());
        } catch (IOException | ClassNotFoundException e) {
            kivetel.kezeles(e, Kitol.ELOMENYLOADING.toString());
        }
    }

    private void elelmiszerkFeltolt() {
        ArrayList<File> filok = fileKibe.getElelmiszerfile();
        try {
            for (File f : filok) {
                List<String> adatok = fileKibe.filebe(f);
                for (int i = 1; i < adatok.size(); i++) {
                    this.elelmiszerek.add(new Elelmiszer(adatok.get(i).replaceAll(",", ".")));
                }
            }
        } catch (IOException e) {
            this.kivetel.kezeles(e, Kitol.ELELMISZEUP.toString());
        }
    }

    private void hibakodFeltolt() {
        File[] hibaszov = FORRASOK_MAPPA.listFiles(TXT_FILTER);
        int i = 0, N = hibaszov.length;
        while (i < N && !(hibaszov[i].getName().toLowerCase().equals("hibak.txt"))) {
            i++;
        }
        if (i < N) {
            try {
                List<String> lista = this.fileKibe.filebe(hibaszov[i]);
                lista.forEach(sor -> {
                    kivetel.kodokfeltolt(sor);
                });
            } catch (IOException e) {
                kivetel.kezeles(e, Kitol.HIBAKODOLVASAS.toString());
            }
        } else {
            kivetel.kezeles(new IOException(), Kitol.HIBAKODOLVASAS.toString());
        }
    }

    @Override
    public void kilepesiProc() {
        HashMap<String, Integer> statisztika = kivetel.getStatisztika();
        try {
            this.fileKibe.SaveElozmenyek(ablak.getElozmenyLista());
            this.fileKibe.SaveStatisztika(kivetel.getStatisztika());
            this.fileKibe.FelhasznaloMentes(this.felhasznalo);
            this.fileKibe.kiirSzovegesen(this.forrasok, HASZNALT_FORRASOK);
            System.out.println("kilépési szándék: " + ablak.getKilepSzandek());
            if (vanNet()) {
                StringBuilder szoveg = new StringBuilder(NAP_FORMATUM.format(new GregorianCalendar().getTime()));
                szoveg.append(" |Ablak utólsó mérete: ").append(this.ablak.getMeret());
                double memo = (double) ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1024);
                szoveg.append(" |Használtmemória: ").append(memo);
                szoveg.append(" |KezdésIdeje: ").append(KEZDIDO);
                String veg = this.ablak.getVegido();
                Date vegDate = FORMATFULL.parse(veg);
                GregorianCalendar dat = new GregorianCalendar();
                dat.setTime(vegDate);
                System.out.println("ojiasgfjashgdj DAT: "+FORMATFULL.format(vegDate));
                szoveg.append(" |BefejezésIdeje: ").append(vegDate);
                Date kezdDate = FORMATFULL.parse(KEZDIDO);
                long kul = vegDate.getTime() - kezdDate.getTime();
                Date datekul = new Date(kul);
                FORMATFULL.format(datekul);
                GregorianCalendar kuldate = new GregorianCalendar();
                kuldate.setTime(datekul);
                long kulsec = kuldate.getTimeInMillis() / 1000 % 60, kulmin = kuldate.getTimeInMillis() / (60 * 1000) % 60, kulora = kuldate.getTimeInMillis() / (60 * 60 * 1000) % 24;
                szoveg.append(" |Eltelido: ").append(Math.abs(kulora)).append(":").append(Math.abs(kulmin)).append(":").append(Math.abs(kulsec));
                StringBuilder elozmenyek = new StringBuilder(" |Előzmények: ");
                /* ellenörzés
            for (String eloz : foablak.getElozmenyLista()) {
                if (foablak.getElozmenyLista().size() > 1) {
                    elozmenyek.append(eloz).append("/");
                } else {
                    elozmenyek.append(eloz);
                }
                System.out.println(eloz);
            }*/
                ArrayList<String> elozmeny = ablak.getElozmenyLista();
                if (elozmeny.size() > 0 || !elozmeny.isEmpty()) {
                    for (int i = 0; i < elozmeny.size(); i++) {
                        if (elozmeny.size() - 1 == i) {
                            elozmenyek.append(elozmeny.get(i));
                        } else {
                            elozmenyek.append(elozmeny.get(i)).append("/");
                        }
                    }
                } else {
                    elozmenyek.append("Nem voltak");
                }
                szoveg.append(elozmenyek);
                szoveg.append(" |Kilépési szándék: ").append(ablak.getKilepSzandek());
//                System.out.println(szoveg.toString());
                this.connect.putHibaStat(statisztika);
                this.connect.putStatisztika(szoveg.toString());
            }
        } catch (IOException | ClassNotFoundException | SQLException | ParseException e) {
            kivetel.kezeles(e, Kitol.STATISZTIKA.toString());
        }
    }

    @Override
    public void hibaAblak(String szoveg) {
        JOptionPane.showConfirmDialog(ablak, szoveg, "Hiba", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public ArrayList<File> menuEpit() {
        return fileKibe.getElelmiszerfile();
    }

    @Override
    public double szamitas(double ch100, String menyiert, String menyit) {
        double ertek = 0.0;
//        System.out.println(ch100 + "/" + menyiert + "/" + menyit);
        try {
            if (menyiert.equals("") || menyit.equals("")) {
                this.kivetel.kezeles(new NullPointerException(), Kitol.URESERTEK.toString());
            } else {
                ertek = this.szamolas.chSzamolas(ch100, Double.parseDouble(menyiert), Double.parseDouble(menyit));
            }
//            ertek = Double.parseDouble(new DecimalFormat("####0.00").format(ertek));
        } catch (NumberFormatException e) {
            this.kivetel.kezeles(e, Kitol.SZAMITAS.toString());
        }
        return ertek;
    }

    @Override
    public double kereses(String szov) {
        int i = 0, N = this.elelmiszerek.size();
        while (i < N && !(this.elelmiszerek.get(i).getNev().equals(szov))) {
            i++;
        }
        if (i < N) {
            return this.elelmiszerek.get(i).getCh();
        }
        return 0.0;
    }

    @Override
    public ArrayList<String> Fileadat(File f) {
        ArrayList<String> adatok = new ArrayList<>();
        try {
            List<String> asd = fileKibe.filebe(f);
            for (String sor : asd) {
                adatok.add(sor);
            }
        } catch (IOException e) {
            kivetel.kezeles(e, "");
        }
        return adatok;
    }

    @Override
    public String getElelmiszerAdat(String nev) {
        int i = 0, N = this.elelmiszerek.size();
        while (i < N && !(this.elelmiszerek.get(i).getNev().equals(nev))) {
            i++;
        }
        if (i < N) {
            return this.elelmiszerek.get(i).toString();
        }
        return "";
    }

    @Override
    public String getKajanev(String nev) {
        String keres = "";
        StringBuilder szov = new StringBuilder();
        if (nev != "" && nev != null) {
            keres = nev.split(" ")[0];
        }
        for (Kaja kaja : this.kajak) {
            if (kaja.getHozzávalok().toLowerCase().contains(keres.toLowerCase())) {
                szov.append(kaja.getNev()).append(";");
            }
        }
        return szov.toString();
    }

    @Override
    public String getKajaleiras(String nev) {
        StringBuilder szov = new StringBuilder();
//        System.out.println(nev);
//        if (nev != null) {
        try {
            int i = 0, N = this.kajak.size();
            while (i < N && !(this.kajak.get(i).getNev().toLowerCase().contains(nev.toLowerCase()))) {
                i++;
            }
            if (i < N) {
                szov.append(this.kajak.get(i).getHozzávalok()).append(";").append(this.kajak.get(i).getElkezítés());
            }
        } catch (NullPointerException e) {
            this.kivetel.kezeles(e, Kitol.KAJALEIRAS.toString());
        }

//        }else {
//            this.kivetel.kezeles(new NullPointerException(), Kitol.KAJALEIRAS.toString());
//    }
        return szov.toString();
    }

    @Override
    public void Belepes(String nev, String pass) {
        try {
            if (vanNet()) {
                if (connect.login(nev, pass)) {
                    this.felhasznalo.setNev(nev);
//                    this.felhasznalo.setKedvencek(kedvencek);
                    this.ablak.setTitle(this.felhasznalo.getNev());
                }
            }
        } catch (SQLException | ClassNotFoundException | IOException | NoSuchAlgorithmException e) {
            this.kivetel.kezeles(e, Kitol.LOGIN.toString());
        }
    }

    @Override
    public void Betoltott() {
//        for (String string : this.ablak.getElozmenyLista()) {
//            System.out.println(string);
//        }
        this.ablak.setVisible(true);
    }

    @Override
    public void regisztralas(String nev, char[] pass, String szul) {
        try {
            this.connect.reg(nev, pass, szul);
        } catch (ClassNotFoundException | NoSuchAlgorithmException | UnsupportedEncodingException | SQLException e) {
            this.kivetel.kezeles(e, Kitol.REGI.toString());
        }
    }

    @Override
    public void saveKedvencek(String nev) {
        int i = 0, N = this.elelmiszerek.size();
        while (!this.elelmiszerek.get(i).getNev().equals(nev)) {
            i++;
        }
//        System.out.println(this.elelmiszerek.get(i));
        this.felhasznalo.addKedvenc(this.elelmiszerek.get(i));
    }

    @Override
    public ArrayList<String> FelahsznaloKedvencei() {
        ArrayList<String> nevek = new ArrayList<>();
        this.felhasznalo.getKedvencek().forEach(elelmiszer -> {
            nevek.add(elelmiszer.getNev());
        });
        return nevek;
    }

    @Override
    public TableModel abLekerdez(String mit) {
        TableModel model = new DefaultTableModel();
        try {
//            this.connect.AdminadatokLekerdez(mit);
            System.out.println(this.connect.AdminadatokLekerdez(mit));
        } catch (SQLException | ClassNotFoundException e) {
            kivetel.kezeles(e, "");
        }
        return model;
    }

    @Override
    public void abBeszúr(String mit) {

    }

    @Override
    public void abmodósit(String mit) {

    }

    public static void main(String[] args) {
        new Ch();
    }

}
