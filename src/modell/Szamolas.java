package modell;

import defaul_Class.Vezerlo;
import modell.tarolok.Alap;

public class Szamolas implements Alap {

    private Vezerlo vezerlo;

    public Szamolas(Vezerlo vez) {
        this.vezerlo = vez;
    }

    public double chSzamolas(double ch100, double menyiert, double menyit) {
        double ertek = 0.0;
        if (ch100 > 0) {
            if (menyiert <= 1000.0 && menyit <= 1000.0) {
                if (menyiert > 0) {
                    ertek = (ch100 / 100) * menyiert;
                } else {
                    ertek = (ch100 / 100) / menyit;
                }
            }
        }
//        System.out.println(ch100 + "/" + menyiert + "/" + menyit);
//        if (menyiert >= 0.0 && menyit >= 0.0) {
//            if (menyiert <= 1000.0 && menyit <= 1000.0) {
//                if (menyiert > 0.0) {
//
//                } else {
//                    ertek = (ch100 / 100) * menyiert;
//                }
//            }
//        }
        return ertek;
    }
}
