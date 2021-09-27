package defaul_Class;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import modell.tarolok.AblakFigyelo;
import modell.tarolok.Alap;

abstract public class Parbeszed extends JDialog implements Alap, AblakFigyelo {

    private JFrame szulo;

    public Parbeszed() {
        this(null);
    }

    public Parbeszed(JFrame szulo) {
        if (szulo != null) {
            this.szulo = szulo;
            this.setLocationRelativeTo(this.szulo);
        } else {
            this.setLocationRelativeTo(this);
        }
        this.setSize(HIBA_DOBOZ);
    }

    public void lathatosag() {
        Elem();
        this.setVisible(!this.isVisible());
//        this.szulo.setEnabled(!this.szulo.isEnabled());
    }

    abstract public void ElemHozzaad(JComponent elem, String hova);

    abstract public void CimMegadas(JComponent elem);

    abstract protected void Elem();

    abstract public void hovaCim(int mertek);

    protected void meretezes() {
        this.setResizable(true);
    }

    public void setMeretek(int width, int height) {
        this.setSize(new Dimension(width, height));
    }

    public void setMeretek(JFrame kihez) {
        this.setSize(HIBA_DOBOZ);
        this.setMaximumSize(new Dimension(kihez.getWidth() - 150, kihez.getHeight() - 150));
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        if (this.getSize().height >= this.szulo.getSize().height || this.getSize().width >= this.szulo.getSize().width) {
            if (this.getSize().height >= this.szulo.getSize().height) {
                this.setSize(new Dimension(this.getSize().width, this.szulo.getSize().height - 150));
            } else if (this.getSize().width >= this.szulo.getSize().width) {
                this.setSize(new Dimension(this.szulo.getSize().width - 150, this.getSize().width));
            } else {
                this.setSize(new Dimension(this.szulo.getSize().width - 150, this.szulo.getSize().height - 150));
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

}
