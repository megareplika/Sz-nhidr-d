package nezet;

import defaul_Class.Parbeszed;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modell.Stilus;

public class Utmutato extends Parbeszed {
    
    private JLabel cim;
    private JPanel informacio, cimsor;
    
    public Utmutato() {
        this(null);
    }
    
    public Utmutato(JFrame szulo) {
        super(szulo);
        this.setTitle(ALAP_CIMSOR_SZOVEG);
        this.cim = new JLabel("");
        this.hovaCim(SwingConstants.CENTER);
        this.informacio = new JPanel(new BorderLayout());
        this.cimsor = new JPanel(new GridLayout(1, 3, 3, 3));
        this.cimsor.add(cim);
        this.setLocationRelativeTo(szulo);
    }
    
    public void setCim(String szov) {
        this.cim.setText(szov);
        this.cim.setBorder(Stilus.margin(cim));
    }
    
    @Override
    public void ElemHozzaad(JComponent elem, String hova) {
        this.informacio.add(elem, hova);
    }
    
    @Override
    public void CimMegadas(JComponent elem) {
        this.cimsor.add(elem);
    }
    
    @Override
    protected void Elem() {
        this.setLayout(new BorderLayout());
        this.add(this.cimsor, BorderLayout.NORTH);
        this.add(this.informacio, BorderLayout.CENTER);
    }
    
    @Override
    public void hovaCim(int mertek) {
        if (mertek < 0) {
            this.cim.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            this.cim.setHorizontalAlignment(mertek);
        }
    }
}
