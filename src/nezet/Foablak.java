package nezet;

import defaul_Class.Vezerlo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import modell.Stilus;
import modell.tarolok.AblakFigyelo;
import modell.tarolok.Alap;

public class Foablak extends JFrame implements AblakFigyelo, Alap, ActionListener, MouseListener {

    private Vezerlo vezerlo;
    private Utmutato KajaUtmutato, elozmenyAblak, kedvencAblak;
    private Thread Ido;
    private int ora, perc, sec, kilepSzandek;
    private String allapot, Keresnev, vegido, KeresKaja;
    private JPanel allapotsav, Kajapan;
    private ArrayList<String> elozmenyLista;
    private JMenuBar meBar;
    private JMenu meElelmiszer, file;
    private Login login;
    private Reg reg;
    private JTextPane tpan, tpan2;
    private JLabel elozmenek;
    private JTextArea adatokArea;
    private JButton kedvencekhez, kedvSzamol, btKaja, btElozmenySzamol;
    private JList<String> ListElozmeny, ListKedvenc, ListKaja;
    private JTabbedPane tabpan;
    private AdminFelulet admin;

    public Foablak(Vezerlo vez) {
        init(vez);
        this.setTitle("Ch");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(new Dimension(KILYELZO_MERET.width / 2, KILYELZO_MERET.height / 2));
        this.setSize(new Dimension(KILYELZO_MERET.width / 2, KILYELZO_MERET.height / 2));
        this.setMinimumSize(new Dimension(KILYELZO_MERET.width / 3, KILYELZO_MERET.height / 3));
        this.setMaximumSize(new Dimension(KILYELZO_MERET.width - this.getPreferredSize().width, KILYELZO_MERET.height - this.getPreferredSize().height));
        this.setLocationRelativeTo(this);
        this.addWindowListener(this);
        Terület();
    }

    private void init(Vezerlo vez) {
        this.vezerlo = vez;
        this.admin = new AdminFelulet(vez, this);
        this.allapot = "Várakozik...";
        this.meBar = new JMenuBar();
        this.meElelmiszer = new JMenu("Élelmiszer");
        this.file = new JMenu("File");
        this.allapotsav = new JPanel(new BorderLayout());
        this.Kajapan = new JPanel(new BorderLayout());
        this.elozmenyLista = new ArrayList<>();
        this.tpan = new JTextPane();
        this.tpan2 = new JTextPane();
        this.adatokArea = new JTextArea();
        this.elozmenek = new JLabel("Előzmények");
        this.kedvencekhez = new JButton("Kedvencekhez");
        this.kedvSzamol = new JButton("Számol");
        this.btElozmenySzamol = new JButton("UjraSzámol");
        this.ListElozmeny = new JList<>();
        this.ListKedvenc = new JList<>();
        this.ListKaja = new JList<>();
        this.btKaja = new JButton("Kaja");
        this.tabpan = new JTabbedPane();
        this.kilepSzandek = 0;
        this.tpan.setText("1");
        this.tpan2.setText("1");
        this.KeresKaja = "";
    }

    public void frissitCim(String nev) {
        this.setTitle("Ch " + nev);
    }

    public void setElozmenyLista(ArrayList<String> elozmenyLista) {
        for (String string : elozmenyLista) {
            if (!this.elozmenyLista.contains(string)) {
                this.elozmenyLista.add(string);
            }
        }
//        this.elozmenyLista = elozmenyLista;
    }

    public int getKilepSzandek() {
        int kilepSzandek = this.kilepSzandek;
        return kilepSzandek;
    }

    public void NovelKilepSzandek() {
        this.kilepSzandek++;
    }

    public void setAllapot(String allapot) {
        this.allapot = allapot;
    }

    public ArrayList<String> getElozmenyLista() {
        ArrayList<String> elozmenyek = new ArrayList<>();
        elozmenyek.addAll(this.elozmenyLista);
        return elozmenyek;
    }

    public void kilép() {
        System.out.println("kilep");
        this.vezerlo.kilepesiProc();
        System.exit(0);
    }

    private void idoInit(JLabel hova) {
        Ido = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    GregorianCalendar napt = new GregorianCalendar();

                    ora = napt.get(GregorianCalendar.HOUR_OF_DAY);
                    perc = napt.get(GregorianCalendar.MINUTE);
                    sec = napt.get(GregorianCalendar.SECOND);
                    Date date = napt.getTime();
                    StringBuilder adat = new StringBuilder();
                    adat.append(FORMAT.format(date)).append("     ").append(allapot);
                    hova.setText(adat.toString());
                    hova.repaint();
                }
            }
        });
        Ido.start();
    }

    private void allapotsav() {
        JLabel felirat = new JLabel();
        idoInit(felirat);
        felirat.setBorder(Stilus.margin(felirat));
        this.allapotsav.add(felirat, BorderLayout.EAST);
        this.allapotsav.setSize(WIDTH, 20);
        this.allapotsav.setBorder(BorderFactory.createLineBorder(SZEGELY_SZIN, 1));
        this.add(allapotsav, BorderLayout.SOUTH);
    }

    private JScrollPane gorgo(JTextArea kinek, boolean csuszka) {
        JScrollPane scroll = null;
        if (csuszka) {
            scroll = new JScrollPane(kinek, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        } else {
            scroll = new JScrollPane(kinek);
        }
        scroll.setVisible(true);
        return scroll;
    }

    private JScrollPane gorgo(JList<String> kinek, boolean csuszka) {
        JScrollPane scroll = null;
        if (csuszka) {
            scroll = new JScrollPane(kinek, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        } else {
            scroll = new JScrollPane(kinek);
        }
        scroll.setVisible(true);
        return scroll;
    }

    public void menuepit() {
        ArrayList<File> filok = vezerlo.menuEpit();
        if (filok.size() > 0) {
            for (File file : filok) {
                JMenu men = new JMenu(file.getName().split(".csv")[0]);
                List<String> adatok = vezerlo.Fileadat(file);
                for (int i = 1; i < adatok.size(); i++) {
                    men.add(new JMenuItem(adatok.get(i).split(";")[0])).addActionListener(this);
                }
                this.meElelmiszer.add(men);
            }
        }
    }

    private void Terület() {
        menu();
        center();
        kaja();
        allapotsav();
    }

    private void kaja() {
        this.ListKaja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Stilus.formalas(this.ListKaja);
        Stilus.formalas(this.Kajapan);
        Stilus.formalas(this.btKaja);
        this.btKaja.addActionListener(this);
        this.Kajapan.add(this.btKaja, BorderLayout.SOUTH);
        kajalistafrissit();
        this.Kajapan.add(gorgo(this.ListKaja, true), BorderLayout.CENTER);
        this.Kajapan.setPreferredSize(new Dimension(260, 250));
        this.add(this.Kajapan, BorderLayout.EAST);
    }

    private void kajalistafrissit() {
        String[] szov = vezerlo.getKajanev(this.Keresnev).split(";");
        ArrayList<String> listaElem = new ArrayList<>();
        for (String kaja : szov) {
            if (kaja.length() > 0 && !kaja.equals(" ")) {
                listaElem.add(kaja);
            }
        }
        if (listaElem.size() > 0) {
            for (String elem : listaElem) {
                this.ListKaja.setModel(new AbstractListModel<String>() {
                    ArrayList<String> elemek = listaElem;

                    @Override
                    public int getSize() {
                        return elemek.size();
                    }

                    @Override
                    public String getElementAt(int i) {
                        return elemek.get(i);
                    }
                });
            }
        }
    }

    private void menu() {
        this.file.add(new JMenuItem("Login")).addActionListener(this);
        Stilus.meSzepaAdd(file);
        this.file.add(new JMenuItem("Regisztrálás")).addActionListener(this);
        Stilus.meSzepaAdd(file);
        this.file.add(new JMenuItem("Admin")).addActionListener(this);
        Stilus.meSzepaAdd(file);
        this.file.add(new JMenuItem("Kedvencek")).addActionListener(this);
        Stilus.meSzepaAdd(file);
        this.file.add(new JMenuItem("Kilép")).addActionListener(this);
        this.meBar.add(this.file);
        this.meBar.add(this.meElelmiszer);
        this.elozmenek.addMouseListener(this);
        this.meBar.add(this.elozmenek);
        this.setJMenuBar(meBar);
    }

    private void center() {
        JPanel kozTerulet = new JPanel(new BorderLayout());
        JLabel menyit = new JLabel("Menyit: "), menyert = new JLabel("Menyiert: ");
        Stilus.formalas(kozTerulet);
        Stilus.formalas(menyert);
        Stilus.formalas(menyit);
        JPanel bevitelipan = new JPanel(new GridLayout(1, 4));
        bevitelipan.add(menyit);
        bevitelipan.add(this.tpan);
        bevitelipan.add(menyert);
        bevitelipan.add(this.tpan2);
        kozTerulet.add(bevitelipan, BorderLayout.NORTH);
        this.adatokArea.setPreferredSize(new Dimension(350, 220));
        this.adatokArea.setMaximumSize(new Dimension(80, 50));
        this.adatokArea.setEditable(false);
        Stilus.formalas(this.adatokArea);
        tabAdd(gorgo(this.adatokArea, false), "eredmeny");
        kozTerulet.add(tabpan, BorderLayout.CENTER);
        this.add(kozTerulet, BorderLayout.CENTER);
    }

    private void tabAdd(JComponent comp, String cim) {
        this.tabpan.addTab(cim, comp);
    }

    private void kajaUtmutato() {
        this.KajaUtmutato = new Utmutato(this);
        this.KajaUtmutato.setTitle("Kajak - " + this.KeresKaja);
//        System.out.println(this.KeresKaja);
        String[] szov = vezerlo.getKajaleiras(this.KeresKaja).split(";");
//        for (String s : szov) {
//            System.out.print(s + " | ");
//        }
//        System.out.println("");
        JPanel panel = new JPanel(new GridLayout(2, 1));
        this.KajaUtmutato.CimMegadas(panel);
        if (szov.length > 1) {
            String[] hozaVal = szov[0].split("\\|");
            JPanel hozavalok = new JPanel(new GridLayout(hozaVal.length, 1));
            for (String lista : hozaVal) {
                hozavalok.add(new JLabel(lista));
            }
            panel.add(hozavalok);
            panel.add(gorgo(new JTextArea(szov[1]), true));
        } else {
            panel.add(new JLabel("Nincs Kaja hozzákötve"));
        }
        Stilus.margin(panel);
        Stilus.padding(panel);
        this.KajaUtmutato.ElemHozzaad(panel, BorderLayout.CENTER);
        this.KajaUtmutato.setSize(new Dimension(HIBA_DOBOZ.width, HIBA_DOBOZ.height * 2));
        this.KajaUtmutato.lathatosag();
    }

    public String getMeret() {
        return this.getSize().width + "X" + this.getSize().height;
    }

    public String getVegido() {
        idoStop();
        return vegido;
    }

    private void idoStop() {
        this.vegido = ora + ":" + perc + ":" + sec;
    }

    private void kedvencekAblak() {
        this.kedvencAblak = new Utmutato(this);
        this.kedvencAblak.setTitle("Kedvencek");
        this.ListKedvenc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.ListKedvenc.setModel(new AbstractListModel<String>() {
            ArrayList<String> kedv = vezerlo.FelahsznaloKedvencei();

            @Override
            public int getSize() {
                return kedv.size();
            }

            @Override
            public String getElementAt(int i) {
                return kedv.get(i);
            }
        });
        Stilus.margin(this.ListKedvenc);
        Stilus.padding(this.ListKedvenc);
        Stilus.Border(this.ListKedvenc);
        Stilus.formalas(this.kedvSzamol);
        this.kedvSzamol.addActionListener(this);
        this.kedvencAblak.ElemHozzaad(this.ListKedvenc, BorderLayout.CENTER);
        this.kedvencAblak.ElemHozzaad(this.kedvSzamol, BorderLayout.SOUTH);
        this.kedvencAblak.setLocationRelativeTo(this);
        this.kedvencAblak.lathatosag();
    }

    private void elozmenyAblak() {
        this.elozmenyAblak = new Utmutato(this);
        this.elozmenyAblak.setTitle("Előzmények");
        this.elozmenyAblak.setResizable(false);
        this.elozmenyAblak.setCim("Eddig meg nézetek listája");
        this.ListElozmeny.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        for (int i = 0; i < this.elozmenyLista.size(); i++) {
            this.ListElozmeny.setModel(new AbstractListModel<String>() {
                ArrayList<String> elemek = elozmenyLista;

                @Override
                public int getSize() {
                    return elemek.size();
                }

                @Override
                public String getElementAt(int i) {
                    return elemek.get(i);
                }
            });
        }

        Stilus.padding(this.ListElozmeny);
        Stilus.margin(this.ListElozmeny);
        this.kedvencekhez.addActionListener(this);
        Stilus.margin(this.kedvencekhez);

        JPanel btPanel = new JPanel(new BorderLayout());
        this.btElozmenySzamol.addActionListener(this);
        btPanel.add(this.btElozmenySzamol, BorderLayout.EAST);
        btPanel.add(this.kedvencekhez, BorderLayout.CENTER);
        Stilus.padding(btPanel);
        Stilus.margin(btPanel);

        this.elozmenyAblak.ElemHozzaad(this.ListElozmeny, BorderLayout.CENTER);
        this.elozmenyAblak.ElemHozzaad(btPanel, BorderLayout.SOUTH);

        this.elozmenyAblak.setMeretek(HIBA_DOBOZ.width, this.getSize().height - 150);
        this.elozmenyAblak.setLocationRelativeTo(this);
        this.elozmenyAblak.lathatosag();
    }

    private void kiir(String szov) {
        this.adatokArea.setText(this.adatokArea.getText() + szov);
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        if (KILYELZO_MERET.width <= this.getSize().width || KILYELZO_MERET.height <= this.getSize().height) {
            if (this.getSize().height >= KILYELZO_MERET.height) {
                this.setSize(new Dimension(this.getSize().width, KILYELZO_MERET.height - 150));
            } else if (this.getSize().width >= KILYELZO_MERET.width) {
                this.setSize(new Dimension(KILYELZO_MERET.width - 150, this.getSize().width));
            } else {
                this.setSize(new Dimension(KILYELZO_MERET.width - 150, KILYELZO_MERET.height - 150));
            }
        }
    }

    @Override
    public void componentMoved(ComponentEvent ce) {

    }

    @Override
    public void componentShown(ComponentEvent ce) {

    }

    @Override
    public void componentHidden(ComponentEvent ce) {

    }

    private void Adminfelulet() {
        this.admin.setMeretek(this.getSize().width / 2, this.getSize().height / 2);
        this.admin.setLocationRelativeTo(this);
        this.admin.lathatosag();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Kilép")) {
            windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (e.getActionCommand().equals("Login")) {
            this.login = new Login(this, this.vezerlo);
            this.login.lathatosag();
        } else if (e.getActionCommand().equals("Admin")) {
            Adminfelulet();
        } else if (e.getActionCommand().equals(this.elozmenek.getText())) {
            elozmenyAblak();
//        } else if (e.getActionCommand().equals(this.KAJA_BT.getText())) {
//            this.KeresKaja = this.jlLista.getSelectedValue();
//            kajaDialog();
//            this.kajaUt.setLathatosag();
        } else if (e.getActionCommand().equals("Regisztrálás")) {
            this.reg = new Reg(this, this.vezerlo);
            this.reg.lathatosag();
        } else if (e.getSource() instanceof JMenuItem) {
            if (((JMenuItem) e.getSource()).getText().equals("Kedvencek")) {
                kedvencekAblak();
            } else if (!e.getActionCommand().equals(this.kedvSzamol.getText())) {
                Szamolas(e.getActionCommand());
                if (!this.elozmenyLista.contains(this.Keresnev)) {
                    this.elozmenyLista.add(this.Keresnev);
                }
                kajalistafrissit();
//                for (int i = 0; i < 15; i++) {
//                    System.out.print("-");
//                }
//                System.out.println("Előzmények: ");
//                this.elozmenyLista.forEach(elozmenyek -> {
//                    System.out.println(elozmenyek);
//                });
//                for (int i = 0; i < 15; i++) {
//                    System.out.print("-");
//                }
//                System.out.println("");
            }
        } else if (e.getActionCommand().equals(this.kedvencekhez.getText())) {
            List<String> kedv = this.ListElozmeny.getSelectedValuesList();
            kedv.forEach(nev -> {
                this.vezerlo.saveKedvencek(nev);
            });
        } else if (e.getActionCommand().equals(this.kedvSzamol.getText())) {
            Szamolas(this.ListKedvenc.getSelectedValue());
            this.kedvencAblak.lathatosag();
        } else if (e.getActionCommand().equals(this.btKaja.getText())) {
            this.KeresKaja = this.ListKaja.getSelectedValue();
            kajaUtmutato();
        } else if (e.getActionCommand().equals(this.btElozmenySzamol.getText())) {
            Szamolas(this.ListElozmeny.getSelectedValue());
            this.elozmenyAblak.lathatosag();
        }
    }

    private void Szamolas(String nev) {
        this.Keresnev = nev;
        kajalistafrissit();
        double erteke = vezerlo.kereses(this.Keresnev);
        if (erteke > 0.0) {
            double ertek = vezerlo.szamitas(erteke, this.tpan.getText(), this.tpan2.getText());
            StringBuilder kiAddat = new StringBuilder(this.Keresnev);
            kiAddat.append("\tKiszámolt érték:    ").append(ertek).append("\n\t").append(vezerlo.getElelmiszerAdat(this.Keresnev));
//            System.out.println(kiAddat.toString());
            kiir(kiAddat.toString());
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
//        System.out.println(me.paramString());
//        System.out.println(me.getSource().getClass().getTypeName());
//        System.out.println(me.getClickCount());
        if (me.toString().contains(this.elozmenek.getText())) {
            elozmenyAblak();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
//        Megfog
    }

    @Override
    public void mouseReleased(MouseEvent me) {
//        Elenged
    }

    @Override
    public void mouseEntered(MouseEvent me) {
//        felette
//        if (me.getSource() instanceof JLabel) {
//            ((JLabel) me.getSource()).setCursor(HAND);
//        }
        ((JComponent) me.getSource()).setCursor(HAND);
    }

    @Override
    public void mouseExited(MouseEvent me) {
//        lerőla
        if (me.getSource() instanceof JLabel) {
            Stilus.Hatter((JLabel) me.getSource(), CIMKE_HATTER);
//            ((JLabel) me.getSource()).setBackground(CIMKE_HATTER);
        }
    }

}
