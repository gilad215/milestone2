package boot;

import model.data.*;
import searchLib.data.BFS;
import searchLib.data.Solution;
import strips.Clause;

import strips.SokoPredicate;
import strips.*;

import java.util.List;

public class TestMain {
    public static void main(String[] args) {

        SokoPredicate p = new SokoPredicate("boxAt", "?", "4,3");
        SokoPredicate p1 = new SokoPredicate("boxAt", "?", "4,2");
        SokoPredicate p2 = new SokoPredicate("boxAt", "?", "1,2");
        SokoPredicate p3 = new SokoPredicate("boxAt", "?", "6,3");


        MySokobanLoader loader = new MySokobanLoader("C:\\Users\\G-lad\\IdeaProjects\\milestone2\\Extras\\levelplannable.txt");
        loader.load();
//        SokobanPlannable sokobanPlannable=new SokobanPlannable(loader.getLvl());
//        Clause goals=sokobanPlannable.getGoal();
//        System.out.println(goals.getValue());
//
//        Point test=new Point(3,1);
        //SokobanSearchable<Point> searchable=new SokobanSearchable<>(loader.getLvl(),"box",new Point(2,2),new Point(1,3));
//        SokobanSearchable<Point> searchable=new SokobanSearchable<>(loader.getLvl(),"box",new Point(2,2),new Point(1,3));
//
//        BFS<Point> searcher=new BFS<>();
//        Solution sol=searcher.search(searchable);
//        System.out.println(sol.getActions().toString());


        SokobanPlannable plannable = new SokobanPlannable(loader.getLvl());
        List<Action> actions = plannable.getSatisfyingActions(p2);

        MySokobanDisplay display=new MySokobanDisplay(plannable.getLevel());
//        display.display();
//
//        List<Action> actions1=plannable.getSatisfyingActions(p);
//        actions.addAll(actions1);
        display.display();

        int i = 0;
        for (Action a : actions) {
            System.out.println("ACTION:" + i++);
            //System.out.println("PRECONDITIONS:"+a.getPreconditions().toString());
            System.out.println("EFFECTS:" + a.getEffects().toString());
            System.out.println("~~~~~~~~~~~~~~~");



//    }

//        Planner planner=new strips();
//        System.out.println(plannable.getGoal().toString());
//        List<Action> stripsactions= planner.plan(plannable);
//        System.out.println(stripsactions.toString());

        }
    }
}
