import java.util.ArrayList;

public class Hex {
    public final int q;
    public final int r;
    public final int s;

    public Hex(int q, int r, int s) throws IllegalArgumentException {
        if (q + r + s != 0) {
            throw new IllegalArgumentException("q, r, s must sum to 0");
        }
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public String toString() {
        return String.format("%d,%d,%d", this.q, this.r, this.s);
    }

    public int getHash() {
        return String.format("%d,%d,%d", this.q, this.r, this.s).hashCode();
    }

    static public Hex add(Hex a, Hex b) {
        return new Hex(a.q + b.q, a.r + b.r, a.s + b.s);
    }


    static public Hex subtract(Hex a, Hex b) {
        return new Hex(a.q - b.q, a.r - b.r, a.s - b.s);
    }


    static public Hex scale(Hex a, int k) {
        return new Hex(a.q * k, a.r * k, a.s * k);
    }


    static public Hex neighbor(Hex hex, int direction) {
        return Hex.add(hex, Hex.directions.get(direction));
    }

    static public ArrayList<Hex> neighbors(Hex hex) {
        ArrayList<Hex> neighbors = new ArrayList<Hex>();
        for (Hex direction : directions) {
            neighbors.add(Hex.add(hex, direction));
        }
        return neighbors;
    }

    static public Hex diagonalNeighbor(Hex hex, int direction) {
        return Hex.add(hex, Hex.diagonals.get(direction));
    }


    static public int length(Hex hex) {
        return (int)((Math.abs(hex.q) + Math.abs(hex.r) + Math.abs(hex.s)) / 2);
    }


    static public int distance(Hex a, Hex b) {
        return Hex.length(Hex.subtract(a, b));
    }

    static public ArrayList<Hex> directions = new ArrayList<Hex>(){{
        add(new Hex(1, 0, -1));
        add(new Hex(1, -1, 0));
        add(new Hex(0, -1, 1));
        add(new Hex(-1, 0, 1));
        add(new Hex(-1, 1, 0));
        add(new Hex(0, 1, -1));
    }};

    static public ArrayList<Hex> diagonals = new ArrayList<Hex>(){{
        add(new Hex(2, -1, -1));
        add(new Hex(1, -2, 1));
        add(new Hex(-1, -1, 2));
        add(new Hex(-2, 1, 1));
        add(new Hex(-1, 2, -1));
        add(new Hex(1, 1, -2));
    }};

}