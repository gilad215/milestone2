package strips;

import java.util.Set;

public interface Plannable {
    Clause getGoal();
    Clause getKnowledgeBase();

    Set<Action> getSatisfyingActions(Predicate top);
    Action getSatisfyingAction(Predicate top);
}
