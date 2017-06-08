package searchLib;

import java.util.LinkedList;

public abstract class CommonSearcher<T> implements Searcher<T> {
    protected int evaluatedNodes;

    @Override
    public int getNumberOfNodesEvaluated(){return evaluatedNodes;}

    protected Solution backTrace(State<T> goalState)
    {
        LinkedList<SearchAction> actions=null;
        State<T> currState=goalState;
        Solution sol=new Solution();
        if(currState.getCameFrom()!=null)
        {
            actions=new LinkedList<>();
            while(currState.getCameFrom()!=null) {
                System.out.println("Goal has Trace");
                actions.addFirst(currState.getAction());
                currState = currState.getCameFrom();
            }
            sol.setActions(actions);
        }
        else
        {
            System.out.println("INITIAL AND GOAL ARE SAME, RETURNING 0 ACTIONS");
            sol.setSokoflag(1);
        }

        return sol;
    }
}
