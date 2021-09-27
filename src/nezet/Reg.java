package nezet;

import defaul_Class.Parbeszed;
import defaul_Class.Vezerlo;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import modell.Stilus;

public class Reg extends Parbeszed {

    private Vezerlo vezerlo;
    private JLabel lbnev, lbpass1, lbpass2, lbszul, lbprofilKep;
    private JTextField nev;
    private JPasswordField pass1, pass2;
    private JButton btreg, btmegse;
    private JFormattedTextField szul;
    private StringBuilder hibaszov;
//    private JFileChooser profKep;

    public Reg() {
        this(null, null);
    }

    public Reg(JFrame szulo, Vezerlo vez) {
        super(szulo);
        this.vezerlo = vez;
        init();
        this.btreg.addActionListener((ae) -> {
            if (jolvanEkitöltve()) {
                Stilus.TextFormaz(this.nev);
                Stilus.TextFormaz(this.pass1);
                Stilus.TextFormaz(this.pass2);
                this.vezerlo.regisztralas(this.nev.getText(), this.pass1.getPassword(), this.szul.getText());
            } else {
                this.vezerlo.hibaAblak(this.hibaszov.toString());
            }
        });
        this.btmegse.addActionListener((ea) -> {
            lathatosag();
        });
        this.setTitle("Regisztrálás");
        this.setLocationRelativeTo(szulo);
        this.setLayout(new BorderLayout(3, 3));
    }

    private void init() {
        this.nev = new JTextField();
        this.pass1 = new JPasswordField();
        this.pass2 = new JPasswordField();
        this.lbnev = new JLabel("felhasznalonev*");
        this.lbpass1 = new JLabel("jelszó*");
        this.lbpass2 = new JLabel("jelszómegint*");
        this.lbszul = new JLabel("Születési idő");
        this.btmegse = new JButton("Mégsem");
        this.btreg = new JButton("regisztrálás");
        this.btreg.setSize(GOMB_MERET);
        this.btmegse.setSize(GOMB_MERET);
        this.szul = new JFormattedTextField();
        this.hibaszov = new StringBuilder("Nincs minden kitöltve");
    }

    private boolean jolvanEkitöltve() {
        this.hibaszov = new StringBuilder();
        boolean kiteltve = false;
        String nev = this.nev.getText(), pass1 = "", pass2 = "";
        pass1 = jelszofuzes(this.pass1.getPassword());
        pass2 = jelszofuzes(this.pass2.getPassword());
        System.out.println("nev: " + nev + "\tjelszo1: " + pass1 + "\tjelszo2: " + pass2);
        if (nev.length() >= 3 && Vanbenneszam(nev)) {
            kiteltve = true;
            System.out.println("jó név");
            Stilus.TextFormaz(this.nev);
        } else {
            if (!Vanbenneszam(nev)) {
                System.out.println("nem tartalmaz számot");
                this.hibaszov.append("nem tartalmaz számot").append(",\n");
            } else {
                System.out.println("hibás név");
                this.hibaszov.append("hibás név").append(",\n");
            }
            kiteltve = false;
            Stilus.ErrorSzegely(this.nev);
        }
        if (pass1.equals(pass2) && pass2.equals(pass1)) {
            if (nev.toLowerCase().contains(pass1.toLowerCase())) {
                kiteltve = false;
                System.out.println("nev tartalmazza a jelszót: " + nev.toLowerCase().contains(pass1.toLowerCase()));
                this.hibaszov.append("nev tartalmazza a jelszót").append(",\n");
                Stilus.ErrorSzegely(this.nev);
                Stilus.ErrorSzegely(this.pass1);
                Stilus.ErrorSzegely(this.pass2);
            } else {
                if (Vanbenneszam(pass1) && pass1.matches(".*[A-Z].*")) {
                    Stilus.TextFormaz(this.pass1);
                    Stilus.TextFormaz(this.pass2);
                    kiteltve = true;
                    System.out.println("jó jelszó");
                } else {
                    if (!Vanbenneszam(pass1)) {
                        System.out.println("nem tartalmaz számot");
                        this.hibaszov.append("nem tartalmaz számot").append(",\n");
                    }
                    if (!pass1.matches(".*[A-Z].*")) {
                        System.out.println("nincs benne Nagybetü");
                        this.hibaszov.append("nincs benne Nagybetü").append(",\n");
                    }
                    Stilus.ErrorSzegely(this.pass1);
                    Stilus.ErrorSzegely(this.pass2);
                    kiteltve = false;
                }
            }
        } else {
            System.out.println("nem egyeznek a jelszavak");
            this.hibaszov.append("nem egyeznek a jelszavak").append(",\n");
            kiteltve = false;
            if (!pass1.equals(pass2)) {
                Stilus.ErrorSzegely(this.pass2);
            } else {
                Stilus.ErrorSzegely(this.pass1);
            }
        }
        System.out.println(kiteltve);
        return kiteltve;
    }

    private String jelszofuzes(char[] mit) {
        StringBuilder pas1 = new StringBuilder();
        for (char c : mit) {
            pas1.append(c);
        }
        return pas1.toString();
    }

    private boolean Vanbenneszam(String szov) {
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(szov).matches();
    }

//    private String jelszofuzes(char[] pass) {
//        StringBuilder pass1 = new StringBuilder();
//        for (char c : pass) {
//            pass1.append(c);
//        }
//        return pass1.toString();
//    }
    @Override
    public void ElemHozzaad(JComponent elem, String hova) {

    }

    @Override
    public void CimMegadas(JComponent elem) {

    }

    @Override
    public void hovaCim(int mertek) {

    }

    @Override
    protected void Elem() {
        JPanel pan = new JPanel(new GridLayout(4, 2));
        Stilus.TextFormaz(this.nev);
        Stilus.TextFormaz(this.pass1);
        Stilus.TextFormaz(this.pass2);
//        this.nev.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//        this.pass1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//        this.pass2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pan.add(this.lbnev);
        pan.add(this.nev);
        pan.add(this.lbpass1);
        pan.add(this.pass1);
        pan.add(this.lbpass2);
        pan.add(this.pass2);
        pan.add(this.lbszul);
        this.szul.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter()));
        pan.add(this.szul);
        JPanel btpan = new JPanel(new GridLayout(1, 5));
        btpan.add(Stilus.valasztoPan());
        btpan.add(this.btreg);
        btpan.add(Stilus.valasztoPan());
        btpan.add(this.btmegse);
        btpan.add(Stilus.valasztoPan());
        this.add(pan, BorderLayout.CENTER);
        this.add(btpan, BorderLayout.SOUTH);
    }

}
