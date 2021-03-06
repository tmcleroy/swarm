package swarm.shared;

import java.util.ArrayList;

public class Behavior {
    private Conditions conditions;
    private Actions actions;

    public Behavior (Conditions conditions, Actions actions) {
        this.conditions = conditions;
        this.actions = actions;
    }

    public boolean behave (Unit u) {
        boolean result = this.conditions.result(u);
        if (result) {
            this.actions.perform(u);
        }
        return result;
    }


    private static ArrayList<Predicate> truePredicates = new ArrayList<Predicate>(){{
        add((Unit u) -> true);
    }};
    private static ArrayList<Predicate> upActions = new ArrayList<Predicate>(){{
        add((Unit u) -> {
            u.move(Unit.north);
            return true;
        });
    }};
    private static ArrayList<Predicate> downActions = new ArrayList<Predicate>(){{
        add((Unit u) -> {
            u.move(Unit.south);
            return true;
        });
    }};


    public static Behavior moveUp = new Behavior(
            new Conditions(truePredicates, Conditions.Evaluation.ALL),
            new Actions(upActions)
    );

    public static Behavior moveDown = new Behavior(
            new Conditions(truePredicates, Conditions.Evaluation.ALL),
            new Actions(downActions)
    );
}

class Conditions {
    public enum Evaluation { ALL, ANY };

    public ArrayList<Predicate> items;
    public Evaluation evalutation;

    public Conditions (ArrayList<Predicate> items, Evaluation evaluation) {
        this.items = items;
        this.evalutation = evaluation;
    }

    public boolean result (Unit u) {
        boolean reduced = true;
        for (Predicate func : this.items) {
            boolean pass = func.evaluate(u);
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

    public void perform (Unit u) {
        this.items.forEach((item) -> item.evaluate(u));
    }
}
