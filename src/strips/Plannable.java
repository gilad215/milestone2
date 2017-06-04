package strips;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface Plannable {
    Clause getGoal();
    Clause getKnowledgeBase();

    List<Action> getSatisfyingActions(Predicate top);
    Action getSatisfyingAction(Predicate top);
}
