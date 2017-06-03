package boot;

import model.data.*;
import searchLib.data.BFS;
import searchLib.data.Solution;
import strips.Clause;
import strips.SokoPredicate;

public class TestMain {
    public static void main(String[] args) {

        SokoPredicate p=new SokoPredicate("boxAt","?","6,1");


        MySokobanLoader loader=new MySokobanLoader("C:\\Users\\G-lad\\IdeaProjects\\milestone2\\Extras\\level2.txt");
        loader.load();
        SokobanPlannable sokobanPlannable=new SokobanPlannable(loader.getLvl());
        Clause goals=sokobanPlannable.getGoal();
        System.out.println(goals.getValue());

        Point test=new Point(3,1);
        //SokobanSearchable<Point> searchable=new SokobanSearchable<>(loader.getLvl(),"box",new Point(2,2),new Point(1,3));
        SokobanSearchable<Point> searchable=new SokobanSearchable<>(loader.getLvl(),"box",new Point(2,2),new Point(3,1));

        BFS<Point> searcher=new BFS<>();
        Solution sol=searcher.search(searchable);
        System.out.println(sol.getActions().toString());

    }
}
