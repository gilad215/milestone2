package searchLib.data;

import java.util.LinkedList;

public abstract class CommonSearcher<T> implements Searcher<T> {
    protected int evaluatedNodes;

    @Override
    public int getNumberOfNodesEvaluated(){return evaluatedNodes;}

    protected Solution backTrace(State<T> goalState)
    {
        LinkedList<SearchAction> actions=new LinkedList<>();
        State<T> currState=goalState;
        while(currState.getCameFrom()!=null)
        {
            System.out.println("Goal has Trace");
            actions.addFirst(currState.getAction());
            currState=currState.getCameFrom();
        }
        Solution sol=new Solution();
        sol.setActions(actions);
        return sol;
    }
}
