package swarm.shared;

import java.util.ArrayList;

public class Behavior {
    private Conditions conditions;
    private Actions actions;

    public Behavior (Conditions conditions, Actions actions) {
        this.conditions = conditions;
        this.actions = actions;
    }

    public boolean behave () {
        boolean result = this.conditions.result();
        if (result) {
            this.actions.perform();
        }
        return result;
    }



    private static Predicate truePred = () -> true;
    private static Predicate falsePred = () -> false;
    private static Predicate logSomething = () -> {
        System.out.println("DO AN ACTION!!!");
        return true; // irrelevant, could be any bool
    };
    private static ArrayList<Predicate> truePreds = new ArrayList<Predicate>(){{
        add(truePred);
        add(() -> 1 + 1 == 3);
    }};
    private static ArrayList<Predicate> logEvals = new ArrayList<Predicate>(){{
        add(logSomething);
    }};

    private static Conditions cond = new Conditions(truePreds, Conditions.Evaluation.ALL);
    private static Actions acts = new Actions(logEvals);
    public static Behavior moveUp = new Behavior(cond, acts);
}

class Conditions {
    public enum Evaluation { ALL, ANY };

    public ArrayList<Predicate> items;
    public Evaluation evalutation;

    public Conditions (ArrayList<Predicate> items, Evaluation evaluation) {
        this.items = items;
        this.evalutation = evaluation;
    }

    public boolean result () {
        boolean reduced = true;
        for (Predicate func : this.items) {
            boolean pass = func.evaluate();
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

    public void perform () {
        this.items.forEach(Predicate::evaluate);
    }
}
