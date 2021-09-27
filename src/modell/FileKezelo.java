package modell;

import defaul_Class.Vezerlo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import modell.tarolok.Alap;

public class FileKezelo implements Alap {

    private Vezerlo vezerlo;
    private File[] elelmiszerfile;

    public FileKezelo(Vezerlo vez) {
        init(vez);
    }

    private void init(Vezerlo vez) {
        this.vezerlo = vez;
        csvfile();
    }

    private void csvfile() {
        File[] lista = new File(System.getProperty("user.dir")).listFiles();
        int fileindex = 0;
        for (int i = 0; i < lista.length && fileindex == 0; i++) {
            if (lista[i].getName().toLowerCase().equals("source")) {
                fileindex = i;
            }
        }
        boolean jo = false;
        File F = lista[fileindex];
        do {
            lista = F.listFiles();
//            for (File file : lista) {
//                System.out.println(file);
//            }
            fileindex = 0;
            for (int i = 0; i < lista.length && fileindex == 0; i++) {
                if (lista[i].getName().toLowerCase().equals("elelmiszerek")) {
                    fileindex = i;
                }
            }
            F = lista[fileindex];
            jo = F.isDirectory() && F.getName().equals("elelmiszerek");
        } while (!jo);
//        System.out.println(F.getPath());
//        for (File listFile : F.listFiles()) {
//            System.out.println(listFile);
//        }
        if (ELELMISZER_MAPPA.isDirectory() && ELELMISZER_MAPPA.exists()) {
            this.elelmiszerfile = ELELMISZER_MAPPA.listFiles(CSV_FILTER);
        }
    }

    public ArrayList<File> getElelmiszerfile() {
        ArrayList<File> fileok = new ArrayList<>();
        fileok.addAll(Arrays.asList(this.elelmiszerfile));
        return fileok;
    }

    public List<String> filebe(File f) throws IOException {
        return Files.readAllLines(Paths.get(f.getPath()));
    }

    public Felhasznalo felhasznalobetolt() throws FileNotFoundException, IOException, ClassNotFoundException {
        return (Felhasznalo) beolvasBinaris(FELHASZNALO);
    }

    public ArrayList<String> elozmenyVisszaallit() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream filebe = new FileInputStream(ELOZMENYEK);
        ObjectInputStream objBe = new ObjectInputStream(filebe);
        @SuppressWarnings("unchecked")
        ArrayList<String> lista = (ArrayList<String>) objBe.readObject();
        return lista;
    }

    private Object beolvasBinaris(File honnan) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream filebe = new FileInputStream(honnan);
        ObjectInputStream objBe = new ObjectInputStream(filebe);
        return objBe.readObject();
    }

    public void SaveStatisztika(HashMap<String, Integer> statisztika) throws FileNotFoundException, IOException {
        kiirBinaris(statisztika, STATISZTIKA_FAJL);
    }

    public void SaveElozmenyek(ArrayList<String> elozmenyek) throws FileNotFoundException, IOException {
        ArrayList<String> elozmeny = new ArrayList<>();
        elozmenyek.stream().filter(eloz -> (!eloz.equals("Szamol"))).forEachOrdered(eloz -> {
            elozmeny.add(eloz);
        });
        kiirBinaris(elozmeny, ELOZMENYEK);
    }

    public void FelhasznaloMentes(Felhasznalo user) throws FileNotFoundException, IOException {
        kiirBinaris(user, FELHASZNALO);
    }

    private void kiirBinaris(Object o, File hova) throws FileNotFoundException, IOException {
        do {
            ellenorzes(hova.getParentFile());
        } while (!hova.getParentFile().exists());
        if (hova.exists() && hova.isFile()) {
            hova.delete();
        }
        FileOutputStream fileki = new FileOutputStream(hova);
        ObjectOutputStream objki = new ObjectOutputStream(fileki);
        objki.writeObject(o);
        objki.close();
    }

    public void kiirSzovegesen(ArrayList<String> mit, File hova) throws FileNotFoundException, IOException {
        do {
            ellenorzes(hova.getParentFile());
        } while (!hova.getParentFile().exists());
        if (hova.exists()) {
            hova.delete();
        }
        Files.createFile(hova.toPath());
        for (String szov : mit) {
            Files.write(Paths.get(hova.getPath()), szov.getBytes("ISO-8859-2"), StandardOpenOption.APPEND);
        }
    }

    private void ellenorzes(File f) throws IOException {
//        System.out.println(f.getPath());
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                ellenorzes(f.getParentFile());
            } else {
                Files.createDirectories(Paths.get(f.getPath()));
            }
        }
    }
}
