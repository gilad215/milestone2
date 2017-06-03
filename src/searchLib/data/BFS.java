package searchLib.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class BFS<T> extends CommonSearcher<T>{
    protected PriorityQueue<State> openList;
    private int evaluatedNodes=0;

    @Override
    public Solution search(Searchable<T> s) {
        openList=new PriorityQueue<>();
        openList.add(s.getInitialState());
        HashSet<State> closedSet=new HashSet<>();
        int iteration=0;

        while(openList.size()>0)
        {
            iteration++;
            System.out.println("ITERATION: "+iteration);
            System.out.println("openList size:"+openList.size()+": "+openList.toString());
            System.out.println("closedList size:"+closedSet.size()+": "+closedSet.toString());
            State<T> n=popOpenList();//hi
            closedSet.add(n);
            //System.out.println("CLOSED SET SIZE: "+closedSet.size());
            if(n.equals(s.getGoalState()))
            {
                System.out.println("FOUND GOAL");
                System.out.println("CAME FROM: "+n.getCameFrom().toString());
                return backTrace(n);
            }
            HashMap<SearchAction,State<T>> map=s.getAllPossibleMoves(n);
            System.out.println("POSSIBLE MOVES: "+map);
            for (SearchAction act:map.keySet()) {
                State<T> state=map.get(act);
                if(!closedSet.contains(state) && !openList.contains(state))
                {
                    state.setCameFrom(n);
                    state.setAction(act);

                    openList.add(state);


                }
                else
                {
                    if(!closedSet.contains(state)) {
                        System.out.println("Entered ELSE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        State<T> temp = null;
                        for (State<T> f : openList) {
                            if (f.equals(state)) {
                                temp = f;
                                break;
                            }
                            if (temp != null && state.getCost() < temp.getCost()) {
                                openList.remove(temp);
                                openList.add(state);
                            }
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

