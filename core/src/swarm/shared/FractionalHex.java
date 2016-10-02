package swarm.shared;

import java.util.ArrayList;

public class FractionalHex {
    public final double q;
    public final double r;
    public final double s;

    public FractionalHex(double q, double r, double s) {
        this.q = q;
        this.r = r;
        this.s = s;
    }

    static public Hex hexRound(FractionalHex h) {
        int q = (int)(Math.round(h.q));
        int r = (int)(Math.round(h.r));
        int s = (int)(Math.round(h.s));
        double q_diff = Math.abs(q - h.q);
        double r_diff = Math.abs(r - h.r);
        double s_diff = Math.abs(s - h.s);
        if (q_diff > r_diff && q_diff > s_diff) {
            q = -r - s;
        }
        else if (r_diff > s_diff) {
            r = -q - s;
        }
        else {
            s = -q - r;
        }
        return new Hex(q, r, s);
    }


    static public FractionalHex hexLerp(FractionalHex a, FractionalHex b, double t) {
        return new FractionalHex(a.q * (1 - t) + b.q * t, a.r * (1 - t) + b.r * t, a.s * (1 - t) + b.s * t);
    }


    static public ArrayList<Hex> hexLinedraw(Hex a, Hex b) {
        int N = Hex.distance(a, b);
        FractionalHex a_nudge = new FractionalHex(a.q + 0.000001, a.r + 0.000001, a.s - 0.000002);
        FractionalHex b_nudge = new FractionalHex(b.q + 0.000001, b.r + 0.000001, b.s - 0.000002);
        ArrayList<Hex> results = new ArrayList<Hex>(){{}};
        double step = 1.0 / Math.max(N, 1);
        for (int i = 0; i <= N; i++) {
            results.add(FractionalHex.hexRound(FractionalHex.hexLerp(a_nudge, b_nudge, step * i)));
        }
        return results;
    }

}