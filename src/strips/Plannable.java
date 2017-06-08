package strips;

import java.util.List;

public interface Plannable {
    Clause getGoal();
    Clause getKnowledgeBase();

    List<Action> getSatisfyingActions(Predicate top);
    Action getSatisfyingAction(Predicate top);
}
