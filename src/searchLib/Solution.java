package searchLib;

import java.util.ArrayList;
import java.util.List;

public class Solution<T> implements Comparable<Solution> {
    private List<SearchAction> actions=null;


    private int sokoflag=0;

    public List<SearchAction> getActions() {
        return actions;
    }

    public void setActions(List<SearchAction> actions) {
        this.actions = actions;
    }
    public void addToActions(List<SearchAction> list)
    {
        if(actions==null) actions=new ArrayList<>();
        actions.addAll(list);
    }
    public void addToActions(SearchAction act)
    {
        if(actions==null) actions=new ArrayList<>();
        actions.add(act);
    }

    public int actionSize()
    {
        if(actions!=null)
        {
            return actions.size();
        }
        System.out.println("ACTIONS IS NULL");
        return 0;
    }

    @Override
    public int compareTo(Solution o) {
        return this.actionSize()-o.actionSize();
    }

    public Solution(Solution a, Solution b)
    {
        actions.addAll(a.getActions());
        actions.addAll(b.getActions());
    }

    public Solution(){}

    public int getSokoflag() {
        return sokoflag;
    }

    public void setSokoflag(int sokoflag) {
        this.sokoflag = sokoflag;
    }
    @Override
    public String toString()
    {
        return actions.toString();
    }

    public Solution(Solution s)
    {
        actions=new ArrayList<>(s.actionSize());
        for (Object sa:s.getActions()) {
            SearchAction searchAction=(SearchAction)sa;
            actions.add(searchAction);
        }
    }

}
