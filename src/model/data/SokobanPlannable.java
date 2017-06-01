package model.data;

import strips.*;

import java.util.ArrayList;
import java.util.Set;

public class SokobanPlannable implements Plannable {
    private Level level;
    private Clause goals;


    public SokobanPlannable(Level level)
    {
        this.level=level;




    }

    @Override
    public Clause getGoal() {
        Clause goal=new Clause(null);
        for (Predicate p :getKnowledgeBase().getPredicates()) {
            if(p.getType().startsWith("goa")){
                goal.add(new SokoPredicate("boxAt", "?", p.getValue()));
            }
        }
        return goal;

//        for (Point p:level.getGoals()) {
//            goals.add(new SokoPredicate("BoxAt","?",p.getX()+","+p.getY()));
//        }
//        return goals;
    }

    @Override
    public Clause getKnowledgeBase() {
        Clause kb=new Clause(null);
        int boxCount=0;
        int goalCount=0;
        if(level.getBoard().isEmpty()) return null;
        for (int i = 0; i < level.getBoard().size(); i++) {
            for (int j = 0; j < level.getBoard().get(i).size(); j++) {
               switch(level.getBoard().get(i).get(j)){
                   case '#':kb.add(new SokoPredicate("wallAt", "", i+","+j));break;
                   case ' ':kb.add(new Predicate("clearAt", "", i+","+j));break;
                   case 'A':kb.add(new Predicate("sokobanAt", "", i+","+j));break;
                   case '@':boxCount++;kb.add(new Predicate("boxAt", "b"+boxCount, i+","+j));break;
                   case 'o':goalCount++;kb.add(new Predicate("targetAt", "t"+goalCount, i+","+j));break;
                }
            }
        }
        return kb;
    }

    @Override
    public Set<Action> getSatisfyingActions(Predicate top) {
        return null;
    }

    @Override
    public Action getSatisfyingAction(Predicate top) {
        return null;
    }
}
