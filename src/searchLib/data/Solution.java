package searchLib.data;

import java.util.ArrayList;
import java.util.List;

public class Solution<T> {
    private List<SearchAction> actions=new ArrayList<>();

    public List<SearchAction> getActions() {
        return actions;
    }

    public void setActions(List<SearchAction> actions) {
        this.actions = actions;
    }
}
