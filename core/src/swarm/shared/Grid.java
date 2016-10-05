package swarm.shared;

import java.util.HashMap;

public class Grid {
    public HashMap<Integer, Hex> cells = new HashMap<Integer, Hex>();

    public Grid(int width, int height) {
        for (int q = 0; q < width; q++) {
            int qOffset = (int) Math.floor(q / 2);
            for (int s = -qOffset; s < height - qOffset; s++) {
                Hex h = new Hex(q, -q-s, s);
                this.cells.put(h.getHash(), h);
            }
        }
    }

    public Hex getCell(Hex h) {
        return this.cells.get(h.getHash());
    }
}
