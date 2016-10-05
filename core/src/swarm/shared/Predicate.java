package swarm.shared;

@FunctionalInterface
public interface Predicate {
    boolean evaluate(Unit u);
}