package nezet;

import defaul_Class.Vezerlo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import modell.Stilus;
import modell.tarolok.AblakFigyelo;
import modell.tarolok.Alap;

public class Loading extends JFrame implements Alap, AblakFigyelo {

    private Vezerlo vezerlo;
    private JProgressBar progressBar;
    private boolean redy;
    private JLabel felirat;

    public Loading(Vezerlo vez) {
        this.redy = false;
        this.vezerlo = vez;
        this.progressBar = new JProgressBar();
        this.setTitle("Betöltés...");
//        progressBar.setSize(500, 200);
        Stilus.formalas(progressBar);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        JPanel pan = new JPanel(new GridLayout(1, 1));
        pan.setBackground(Color.DARK_GRAY);
//        felirat = new JLabel("Betöltés...", SwingConstants.CENTER);
//        felirat.setForeground(Color.GREEN);
//        felirat.setVerticalAlignment(SwingConstants.CENTER);
//        Stilus.margin(felirat);
//        pan.add(felirat);
        pan.add(progressBar);
        this.add(pan, BorderLayout.CENTER);
        this.setSize(new Dimension(250, 70));
        this.setLocationRelativeTo(this);
        this.addWindowListener(this);
        this.setVisible(true);
        Csiktolas();
//        for (int i = 0; i < 100; i++) {
//            Csiktolas(i);
//        }
//        windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void Csiktolas() {
        int x = 0;
        this.setCursor(WAIT);
        do {
            this.progressBar.setValue(x);
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
            if (progressBar.getValue() == progressBar.getMaximum()) {
                x = 0;
                Redy();
            }
        } while (!redy);
//        windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void Redy() {
        this.redy = true;
        windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        this.setCursor(DEF);
        vezerlo.Betoltott();
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent ce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentMoved(ComponentEvent ce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
