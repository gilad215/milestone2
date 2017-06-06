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
        Stack<Predicate> tempstack=new Stack<Predicate>();
        //System.out.println("GOALS TO SATISFY:"+plannable.getGoal().toString());
        stack.push(plannable.getGoal());
        while (!stack.isEmpty()) {
            Predicate top = stack.peek();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CURRENT STACK PEEK:"+top.toString());
            if (!(top instanceof Action)) {
                if (!plannable.getKnowledgeBase().satisfies(top)) {
                    if (top instanceof Clause) {
                        System.out.println("CLAUSE POPPED");
                        for (Predicate p : ((Clause) top).predicates) {
                            if(!plannable.getKnowledgeBase().satisfies(p)) stack.push(p);
                        }
                    }
                    else // single unsatisfied
                    {
                        Predicate reserve=null;
                        if(!stack.isEmpty()) reserve=stack.peek();
                        System.out.println("UNSATISFIED PREDICATE IN STRIPS:"+top.toString());
                        List<Action> actions=null;
                         actions= plannable.getSatisfyingActions(top);
                        if(actions!=null) {
                            System.out.println("FOUND SATISFYING ACTIONS IN STRIPS");
                            for (Action action : actions) {
                                tempstack.push(action);
                                System.out.println("PUSHING TO STACK:"+action.toString());
                                if(action.getPreconditions()!=null)
                                tempstack.push(action.getPreconditions());
                            }
                            while(!tempstack.isEmpty())
                            {
                                stack.push(tempstack.pop());
                            }
                            System.out.println("STACK SIZE:"+stack.size());
                        }
                        else {
                            if (!stack.isEmpty()) {
                                System.out.println("Could not satisfy Goal, trying to swap goals");
                                System.out.println("Trying to SOLVE:" + reserve.toString() + " Instead");
                                stack.pop();
                                stack.push(top);
                                stack.push(reserve);
                            }
                        }

                    }
                } else {
                    System.out.println("Predicate is satisfied! popping out!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    stack.pop();
                }

            } else {
                System.out.println("POPPED ACTION OUT!~~~~~~~~~~~~~~~!~#!!$@#%#$^$&%*&#$^#$%@#$@#$@#&$^%*^%&");
                Action a = (Action) stack.pop();
                plannable.getKnowledgeBase().update(a.getEffects());
                plan.add(a);
            }


        }
        return plan;
    }
}

