import java.util.HashMap;

public class Grid {
    public HashMap<Integer, Hex> cells = new HashMap<Integer, Hex>();

    public Grid(int width, int height) {
        for (int r = 0; r < height; r++) {
            int rOffset = (int) Math.floor(r / 2);
            for (int q = -rOffset; q < width - rOffset; q++) {
                Hex h = new Hex(q, r, -q - r);
                this.cells.put(h.getHash(), h);
            }
        }
    }

    public Hex getCell(Hex h) {
        return this.cells.get(h.getHash());
    }
}
