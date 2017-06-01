package boot;

import model.data.MySokobanLoader;
import model.data.SokobanPlannable;
import model.data.TXTUtil;
import strips.Clause;
import strips.Predicate;
import strips.SokoPredicate;

public class TestMain {
    public static void main(String[] args) {

        SokoPredicate p=new SokoPredicate("boxAt","?","6,1");


        MySokobanLoader loader=new MySokobanLoader("C:\\Users\\G-lad\\IdeaProjects\\milestone2\\Extras\\level1.txt");
        loader.load();
        SokobanPlannable sokobanPlannable=new SokobanPlannable(loader.getLvl());
        Clause goals=sokobanPlannable.getGoal();
        System.out.println(goals.getValue());
    }
}
