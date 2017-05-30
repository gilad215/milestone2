package strips;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class strips implements Planner
{
    private Plannable plannable;

    /*
        Push goal into the stack
        Repeat until stack is empty
        If top is a multipart goal, push unsatisfied sub-goals into the stack V
        If top is a single unsatisfied goal, V
        Replace it with an action that satisfies the goal V
        Push the action preconditions into the stack V
        If top is an action, V
        Pop it from the stack V
        Simulate its execution and update the knowledge base with its effects V
        Add the action to the plan V
        If top is a satisfied goal, pop it from the stack V
             */
    @Override
    public List<Action> plan(Plannable plannable) {
        LinkedList<Action> plan = new LinkedList<Action>();
        this.plannable = plannable;
        Stack<Predicate> stack = new Stack<Predicate>();
        stack.push(plannable.getGoal());
        while (!stack.isEmpty()) {
            Predicate top = stack.peek();
            if (!(top instanceof Action)) {
                if (!plannable.getKnowledgeBase().satisfies(top)) {
                    if (top instanceof Clause) {
                        for (Predicate p : ((Clause) top).predicates) {
                            stack.push(p);
                        }
                    } else // single unsatisfied
                    {
                        stack.pop();
                        Action action = plannable.getSatisfyingAction(top);
                        stack.push(action);
                        stack.push(action.preconditions);

                    }
                } else
                    stack.pop();


            } else {
                Action a = (Action) stack.pop();
                plannable.getKnowledgeBase().update(a.effects);
                plan.add(a);
            }


        }
        return plan;
    }
}

