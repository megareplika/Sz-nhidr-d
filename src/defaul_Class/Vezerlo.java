package defaul_Class;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.table.TableModel;
import modell.tarolok.Alap;

abstract public class Vezerlo implements Alap {

    abstract protected void init();

    abstract public void kilepesiProc();

    abstract public void hibaAblak(String szoveg);

    abstract public ArrayList<File> menuEpit();

    abstract public double szamitas(double ch100, String menyiert, String menyit);

    abstract public double kereses(String szov);

    abstract public ArrayList<String> Fileadat(File f);

    abstract public String getElelmiszerAdat(String nev);

    abstract public String getKajanev(String nev);

    abstract public String getKajaleiras(String nev);

    abstract public void Belepes(String nev, String pass);

    abstract public void Betoltott();

    abstract public void regisztralas(String nev, char[] pass, String szul);

    abstract public void saveKedvencek(String nev);

    abstract public ArrayList<String> FelahsznaloKedvencei();

    abstract public TableModel abLekerdez(String mit);

    abstract public void abBeszúr(String mit);

    abstract public void abmodósit(String mit);

    protected boolean vanNet() throws UnknownHostException, IOException {
        int timeout = 2000;
        boolean vanE = false;
        InetAddress[] address = InetAddress.getAllByName("www.google.com");
        for (InetAddress addres : address) {
            if (addres.isReachable(timeout)) {
                vanE = true;
            }
        }
        return vanE;
    }
}
