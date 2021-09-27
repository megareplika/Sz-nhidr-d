package modell.tarolok;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public interface Alap {

    int BETU_MERET = 12;
    Dimension KILYELZO_MERET = Toolkit.getDefaultToolkit().getScreenSize(),
            TEXT_BEVITEL_MERET = new Dimension(40/*szélesség*/, 50/*magasság*/),
            GOMB_MERET = new Dimension(15, 8),
            HIBA_DOBOZ = new Dimension(320, 150),
            VALASZTO_PAN = new Dimension(20, 18);
    Font ABETU = new Font("Times New Roman", Font.ITALIC, BETU_MERET), ErRROR_BETU = new Font("Times New Roman", Font.TRUETYPE_FONT, 15), GombFont = new Font("Times New Roman", Font.ROMAN_BASELINE, 15);
    Color HATTAR_SZIN = Color.BLUE,
            SZEGELY_SZIN = Color.BLACK,
            SZOVEGDOBOZ_HATTER_SZIN = Color.DARK_GRAY,
            SZOVEGDOBOZ_TEXT_SZIN = Color.WHITE,
            CIMKE_HATTER_ON = Color.orange,
            CIMKE_HATTER = Color.RED,
            HIBASZIN = Color.RED;
    File FORRASOK_MAPPA = new File("./source"),
            ELELMISZER_MAPPA = new File(FORRASOK_MAPPA.getPath() + "/elelmiszerek/"),
            KAJA_FAJL = new File(FORRASOK_MAPPA.getPath() + "/kajak.csv"),
            STATISZTIKA_FAJL = new File(FORRASOK_MAPPA.getPath() + "/stat/hiba_statisztika.bin"),
            EGYEB_ADAT_FAJL = new File(FORRASOK_MAPPA.getPath() + "/stat/tecnikai_adatok.txt"),
            ELOZMENYEK = new File(FORRASOK_MAPPA.getPath() + "/stat/elozmenyek.bin"),
            FELHASZNALO = new File(FORRASOK_MAPPA.getPath() + "/user/felhasznalo.bin"),
            HASZNALT_FORRASOK = new File(FORRASOK_MAPPA.getPath() + "/forrasok.txt");
    int MARGIN_MERET = 6, PADDING_MERET = 8;
    String ALAP_CIMSOR_SZOVEG = "Dialog Ablak";

    FileFilter CSV_FILTER = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.getName().endsWith(".csv");
        }
    }, TXT_FILTER = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.getName().endsWith(".txt");
        }
    };
    Cursor DEF = new Cursor(Cursor.DEFAULT_CURSOR),
            WAIT = new Cursor(Cursor.WAIT_CURSOR),
            HAND = new Cursor(Cursor.HAND_CURSOR);

    SimpleDateFormat FORMATFULL = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm");
    SimpleDateFormat NAP_FORMATUM = new SimpleDateFormat("YYYY:MM:dd");

    String VALASZTOSQL = "','",
            KEZDIDO = new GregorianCalendar().get(GregorianCalendar.HOUR_OF_DAY) + ":" + new GregorianCalendar().get(GregorianCalendar.MINUTE) + ":" + new GregorianCalendar().get(GregorianCalendar.SECOND);


    /*Sql parancsok*/
    String DATABASE_DRIVER = "com.mysql.jdbc.Driver",//"oracle.jdbc.driver.OracleDriver",
            DATABASE_URL = "jdbc:mysql://localhost:3306/ch",//"jdbc:oracle:thin:@localhost:1521:xe",
            DATABASE_USER = "root",//"HR",
            DATABASE_PASS = "";//"hr";
    String HIBASTAT_INSERT = "INSERT INTO `hiba_stat` (`code`,`mikor`) VALUES (###Ertek###)",
            STAT_INSERT = "INSERT INTO `statisztika` (`mikor`, `Ablak_meret`, `hasznalt_memo`, `kezdes`, `veg`, `kilep_szandek`) VALUES (###Ertek###)",
            REGISZTRALAS = "regi(###Ertek###)",
            LOGIN = "belepes(###NEV###,###PASS###)",
            ElelmiszerInsert = "insert into elelmiszerek ('id', megnevezes, kateg, ch, feherje,zsir) values(###adatok###)",
            ElelmiszerAdatok = "select * from elelmiszerek",
            ADMINLEKERDEZ = "select * from ###TABLA### where 1";

}
