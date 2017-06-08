package searchLib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class BFS<T> extends CommonSearcher<T> {
    protected PriorityQueue<State> openList;
    private int evaluatedNodes=0;

    @Override
    public Solution search(Searchable<T> s) {
        openList=new PriorityQueue<>();
        openList.add(s.getInitialState());
        HashSet<State> closedSet=new HashSet<>();
        int iteration=0;
        Solution sol;

        while(openList.size()>0)
        {
            iteration++;
            System.out.println("ITERATION: "+iteration);
            System.out.println("openList size:"+openList.size()+": "+openList.toString());
            System.out.println("closedList size:"+closedSet.size()+": "+closedSet.toString());
            State<T> n=popOpenList();//hi
            System.out.println("ADDING "+n.getState().toString()+" TO CLOSED LIST");
            closedSet.add(n);
            System.out.println("N IS:"+n.getState().toString()+"GOAL IS:"+s.getGoalState().getState().toString());
            if(n.equals(s.getGoalState()))
            {
                System.out.println("FOUND GOAL");
                //if(!n.getCameFrom().getState().toString().isEmpty()) System.out.println("CAME FROM: "+n.getCameFrom().toString());
                sol=backTrace(n);
                return sol;
            }
            HashMap<SearchAction, State<T>> map=s.getAllPossibleMoves(n);
            System.out.println("POSSIBLE MOVES: "+map);
            for (SearchAction act:map.keySet()) {
                State<T> state=map.get(act);
                if(!closedSet.contains(state) && !openList.contains(state))
                {
                    state.setCameFrom(n);
                    state.setAction(act);
                    openList.add(state);
                }
                else {
                    if (openList.contains(state)) {
                        State<T> temp = state;
                        temp.setAction(act);
                        temp.setCameFrom(n);
                        openList.remove(state);
                        openList.add(temp);
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

