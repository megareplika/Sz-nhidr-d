package nezet;

import defaul_Class.Parbeszed;
import defaul_Class.Vezerlo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import modell.Stilus;
import static modell.tarolok.Alap.TEXT_BEVITEL_MERET;

public class Login extends Parbeszed {

    private JPanel mezo;
    private JButton login;
    private Vezerlo vezerlo;
    private boolean show;

    public Login() {
        this(null, null);
    }

    public Login(JFrame szulo, Vezerlo vez) {
        super(szulo);
        this.mezo = new JPanel(new BorderLayout());
        this.login = new JButton("Login");
        this.setTitle("Belépés");
        this.setLocationRelativeTo(szulo);
        this.vezerlo = vez;
        this.show = false;
    }

    @Override
    protected void meretezes() {
        this.setResizable(false);
    }

    @Override
    public void ElemHozzaad(JComponent elem, String hova) {
        this.mezo.add(elem, hova);
    }

    @Override
    public void CimMegadas(JComponent elem) {

    }

    @Override
    public void hovaCim(int mertek) {

    }

    @Override
    protected void Elem() {
        JTextPane nev = new JTextPane();
        JPasswordField pass = new JPasswordField();
        Stilus.TextFormaz(nev);
        Stilus.TextFormaz(pass);
        pass.setEchoChar('*');
        JPanel adat = new JPanel(new GridLayout(3, 1));
        nev.setPreferredSize(TEXT_BEVITEL_MERET);
        pass.setPreferredSize(new Dimension(150, 30));
        JPanel pan = new JPanel(new GridLayout(1, 2));
        JButton show = new JButton("show");
        show.setMaximumSize(new Dimension(50, 50));
        show.addActionListener((ae) -> {
            this.show = !this.show;
            if (this.show) {
                pass.setEchoChar((char) 0);
            } else {
                pass.setEchoChar('*');
            }
        });
        pan.add(pass);
        pan.add(show);
        adat.add(nev);
        adat.add(Stilus.valasztoPan());
        adat.add(pan);
        Stilus.padding(adat, 1, 1);
        JPanel log = new JPanel(new BorderLayout());
        Stilus.padding(log);
        log.add(adat, BorderLayout.CENTER);
        this.mezo.add(log, BorderLayout.CENTER);
        this.login.addActionListener((ae) -> {
            this.vezerlo.Belepes(nev.getText(), pass.getPassword() + "");
        });
        JPanel gombtart = new JPanel(new BorderLayout());
        Stilus.padding(gombtart);
        gombtart.add(this.login, BorderLayout.CENTER);
        this.mezo.add(gombtart, BorderLayout.SOUTH);
        this.add(mezo, BorderLayout.CENTER);
    }

}
