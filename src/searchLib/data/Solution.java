package searchLib.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solution<T> implements Comparable<Solution> {
    private List<SearchAction> actions=new ArrayList<>();

    public List<SearchAction> getActions() {
        return actions;
    }

    public void setActions(List<SearchAction> actions) {
        this.actions = actions;
    }
    public void addToActions(List<SearchAction> list)
    {
        actions.addAll(list);
    }
    public void addToActions(SearchAction act)
    {
        actions.add(act);
    }

    public int actionSize()
    {
        if(!actions.isEmpty())
        {
            return actions.size();
        }
        return 0;
    }

    @Override
    public int compareTo(Solution o) {
        return this.actionSize()-o.actionSize();
    }

    public Solution(Solution a,Solution b)
    {
        actions.addAll(a.getActions());
        actions.addAll(b.getActions());
    }

    public Solution(){}

}
