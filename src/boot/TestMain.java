package boot;

import model.data.*;
import strips.SokoPredicate;
import strips.*;
import java.util.LinkedList;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        MySokobanLoader loader = new MySokobanLoader("C:\\Users\\G-lad\\IdeaProjects\\milestone2\\Extras\\level5.txt");
        loader.load();
        SokobanPlannable plannable = new SokobanPlannable(loader.getLvl());

        Planner planner = new strips();
        System.out.println(plannable.getGoal().toString());
        List<Action> stripsactions = planner.plan(plannable);
        List<String> finalActions=new LinkedList<>();
        int i=1;
        for (Action a:stripsactions) {
            System.out.println("Action:"+i);
            System.out.println(a.getEffects().toString());

            if(a.getAct()!=null) finalActions.add(a.getAct());
            i++;
        }
        System.out.println("SIZE:"+stripsactions.size());
        System.out.println("PLANNABLE FINALE"+finalActions);

        
    }
}
