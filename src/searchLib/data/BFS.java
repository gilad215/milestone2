package searchLib.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class BFS<T> extends CommonSearcher<T>{
    protected PriorityQueue<State> openList;
    private int evaluatedNodes;

    @Override
    public Solution search(Searchable<T> s) {
        openList.add(s.getInitialState());
        HashSet<State> closedSet=new HashSet<>();

        while(openList.size()>0)
        {
            State<T> n=popOpenList();//hi
            closedSet.add(n);

            if(n.equals(s.getGoalState()))
            {
                return backTrace(s.getGoalState());
            }
            HashMap<SearchAction,State<T>> map=s.getAllPossibleMoves(n);
            for (SearchAction act:map.keySet()) {
                State<T> state=map.get(act);
                if(!closedSet.contains(state) && !openList.contains(state))
                {
                    state.setCameFrom(n);
                    openList.add(state);
                    state.setAction(act);
                }
                else
                {
                    State<T> temp=null;
                    for(State<T> f: openList)
                    {
                        if(f.equals(state))
                        {
                            temp=f;
                            break;
                        }
                        if(temp!=null && state.getCost()<temp.getCost())
                        {
                            openList.remove(temp);
                            openList.add(state);
                        }
                    }
                }
            }
        }

        return null;
    }
    protected State<T> popOpenList()
    {
        evaluatedNodes++;
        return openList.poll();
    }
}

