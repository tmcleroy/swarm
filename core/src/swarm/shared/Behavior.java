package swarm.shared;

import java.util.ArrayList;

public class Behavior {
    private Conditions conditions;
    private Actions actions;

    public Behavior (Conditions conditions, Actions actions) {
        this.conditions = conditions;
        this.actions = actions;
    }
}

class Conditions {
    private enum Evaluation { ALL, ANY };

    public ArrayList<Predicate> items;
    public Evaluation evalutation;

    public Conditions (ArrayList<Predicate> items, Evaluation evaluation) {
        this.items = items;
        this.evalutation = evaluation;
    }

    public boolean result () {
        boolean reduced = true;
        for (Predicate item : this.items) {
            boolean pass = item.evaluate();
            if (pass && this.evalutation == Evaluation.ANY) {
                reduced = true;
                break;
            } else if (this.evalutation == Evaluation.ALL) {
                if (pass) {
                    reduced = reduced && pass;
                } else {
                    reduced = false;
                    break;
                }
            }
        }
        return reduced;
    }
}

class Actions {
    public ArrayList<Predicate> items;

    public Actions (ArrayList<Predicate> items) {
        this.items = items;
    }
}
