package nezet;

import defaul_Class.Parbeszed;
import defaul_Class.Vezerlo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class AdminFelulet extends Parbeszed implements ActionListener {

    private Vezerlo vezerlo;
    private JPanel fopan, adatok;
    private JButton upload, lekerdez;
    private JTable tabla;
    private TableModel modell;

    public AdminFelulet() {
        this(null, null);
    }

    public AdminFelulet(Vezerlo vez, JFrame szulo) {
        super(szulo);
        init(vez);
        setTitle("Admin Felület");
    }

    private void init(Vezerlo vez) {
        this.vezerlo = vez;
        this.fopan = new JPanel(new BorderLayout());
        this.adatok = new JPanel();
        this.upload = new JButton("Feltölt");
        this.lekerdez = new JButton("Lekerdez");
        this.modell = new DefaultTableModel();
        this.tabla = new JTable(modell);
    }

    @Override
    public void ElemHozzaad(JComponent elem, String hova) {

    }

    @Override
    public void CimMegadas(JComponent elem) {

    }

    @Override
    protected void Elem() {
        this.upload.setFont(GombFont);
        this.lekerdez.setFont(GombFont);
        this.upload.addActionListener(this);
        this.lekerdez.addActionListener(this);
        adatok.add(new JTable());
        JPanel jobb = new JPanel(new BorderLayout());
        JPanel pan = new JPanel(new GridLayout(2, 1));
        pan.add(this.lekerdez);
        pan.add(this.upload);
        jobb.add(pan, BorderLayout.SOUTH);
        fopan.add(adatok, BorderLayout.CENTER);
        fopan.add(jobb, BorderLayout.EAST);
        this.add(fopan);
    }

    @Override
    public void hovaCim(int mertek) {

    }

    private void akcioLekerdez() {
        String sql = ADMINLEKERDEZ.replace("###TABLA###", "elelmiszerek");
        this.modell = vezerlo.abLekerdez(sql);
        this.tabla.setModel(modell);
        this.adatok.add(this.tabla);
    }

    private void akcioInsert(String adat) {
        this.vezerlo.abBeszúr(adat);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(this.upload.getText())) {
            StringBuilder sql = new StringBuilder(ADMINLEKERDEZ);
            akcioInsert(sql.toString());
            System.out.println("upload");
        } else if (e.getActionCommand().equals(this.lekerdez.getText())) {
            akcioLekerdez();
            System.out.println("lekerdez");
        }
    }

}
