package searchLib.data;

import java.util.ArrayList;
import java.util.List;

public class Solution<T> {
    private List<Action> actions=new ArrayList<>();

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
