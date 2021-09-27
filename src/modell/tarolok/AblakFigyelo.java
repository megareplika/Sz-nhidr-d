package modell.tarolok;

import defaul_Class.Parbeszed;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import nezet.Foablak;
import nezet.Loading;

public interface AblakFigyelo extends WindowListener, ComponentListener {

    @Override
    public default void windowOpened(WindowEvent we) {
        if (we.getSource() instanceof Foablak) {
            ((Foablak) we.getSource()).setAllapot("Kész");
        } else if (we.getSource() instanceof Parbeszed) {
            ((Parbeszed) we.getSource()).lathatosag();
        }
    }

    @Override
    public default void windowClosing(WindowEvent we) {
        if (we.getSource() instanceof Foablak) {
            Foablak ablak = (Foablak) we.getSource();
            if (JOptionPane.showConfirmDialog(ablak, "Biztos Kilép?", "KILÉPÉS", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
                ablak.kilép();
            } else {
                ablak.NovelKilepSzandek();
            }
        } else if (we.getSource() instanceof Parbeszed) {
            ((Parbeszed) we.getSource()).lathatosag();
        } else if (we.getSource() instanceof Loading) {
            ((Loading) we.getSource()).setVisible(false);
        }
    }

    @Override
    public default void windowClosed(WindowEvent we) {
        System.out.println(we.toString());
    }

    @Override
    public default void windowIconified(WindowEvent we) {
        System.out.println("tálcára");
    }

    @Override
    public default void windowDeiconified(WindowEvent we) {
        System.out.println("fel tálcáról");
    }

    @Override
    public default void windowActivated(WindowEvent we) {
        System.out.println("fokuszban");
    }

    @Override
    public default void windowDeactivated(WindowEvent we) {
        System.out.println("focus lost");
    }

    @Override
    public void componentHidden(ComponentEvent ce);

    @Override
    public void componentShown(ComponentEvent ce);

    @Override
    public void componentMoved(ComponentEvent ce);

    @Override
    public void componentResized(ComponentEvent ce);

}
