package modell;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import modell.tarolok.Alap;

public class Stilus implements Alap {

    private Stilus() {;
    }

    public static JPanel valasztoPan() {
        JPanel valaszto = new JPanel();
        valaszto.setPreferredSize(VALASZTO_PAN);
        return valaszto;
    }

    private static boolean tartalmazELebalt(JComponent hol) {
        Component[] comp = hol.getComponents();
        int i = 0;
        while (i < comp.length && !(comp[i] instanceof JLabel)) {
            i++;
        }
        return i < comp.length;
    }

    public static CompoundBorder margin(JComponent o) {
        return new CompoundBorder(o.getBorder(), new EmptyBorder(MARGIN_MERET, MARGIN_MERET, MARGIN_MERET, MARGIN_MERET));
    }

    public static void formalas(JComponent o) {
        if (o instanceof JPanel) {
            Hatter(o, HATTAR_SZIN);
            o.setBorder(BorderFactory.createLineBorder(SZEGELY_SZIN, 2));
            if (tartalmazELebalt(o)) {
                Hatter(o, Color.WHITE);
            }
            padding(o);
        } else if (o instanceof JButton) {
            o.setMaximumSize(GOMB_MERET);
            o.setMinimumSize(GOMB_MERET);
            o.setBorder(margin(o));
        } else if (o instanceof JTextArea) {
            Hatter(o, SZOVEGDOBOZ_HATTER_SZIN);
            Betuszin(o, SZOVEGDOBOZ_TEXT_SZIN);
        }
        o.setBorder(margin(o));
    }

    public static void TextFormaz(JComponent o) {
        if (o instanceof JPasswordField) {
            TextBorderPass((JPasswordField) o);
        } else if (o instanceof JTextField) {
            TextBorderfield((JTextField) o);
        } else if (o instanceof JTextPane) {
            TextBorderPane((JTextPane) o);
        }
    }

    public static void Hatter(JComponent o, Color szin) {
        o.setBackground(szin);
    }

    public static void Betuszin(JComponent o, Color szin) {
        o.setForeground(szin);
    }

    public static void ErrorSzegely(JComponent o) {
        o.setBorder(BorderFactory.createLineBorder(HIBASZIN, 2));
    }

    public static void TextBorderPass(JPasswordField o) {
        o.setBorder(BorderFactory.createLineBorder(SZEGELY_SZIN, 1));
    }

    public static void TextBorderPane(JTextPane o) {
        o.setBorder(BorderFactory.createLineBorder(SZEGELY_SZIN, 1));
    }

    public static void TextBorderfield(JTextField o) {
        o.setBorder(BorderFactory.createLineBorder(SZEGELY_SZIN, 1));
    }

    public static void Border(JComponent o) {
        o.setBorder(BorderFactory.createLineBorder(SZEGELY_SZIN, 1));
    }

    public static void padding(JComponent o) {
        padding(o, PADDING_MERET, PADDING_MERET);
    }

    public static void padding(JComponent o, int top, int bottom) {
        padding(o, top, bottom, PADDING_MERET, PADDING_MERET);
    }

    public static void padding(JComponent o, int top, int bottom, int left, int right) {
        o.setBorder(new EmptyBorder(top, left, bottom, right));
    }

    public static void meSzepaAdd(JMenu hova) {
        hova.addSeparator();
    }

}
