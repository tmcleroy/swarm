package swarm.shared;

import java.util.ArrayList;

public class Unit {
    public Hex position;
    public Behavior behavior;

    public static Hex northEast = Hex.directions.get(0);
    public static Hex southEast = Hex.directions.get(1);
    public static Hex south = Hex.directions.get(2);
    public static Hex southWest = Hex.directions.get(3);
    public static Hex northWest = Hex.directions.get(4);
    public static Hex north = Hex.directions.get(5);

    public Unit (Hex position, Behavior behavior) {
        this.position = position;
        this.behavior = behavior;
    }

    public void behave () {
        this.behavior.behave(this);
    }

    public void move (Hex direction) {
        this.position = Hex.add(this.position, direction);
    }

    public ArrayList<Hex> getNeighbors () {
        return Hex.neighbors(this.position);
    }
}
