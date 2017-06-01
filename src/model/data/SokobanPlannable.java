package model.data;

import strips.*;

import java.util.ArrayList;
import java.util.Set;

public class SokobanPlannable implements Plannable {
    private Level level;
    private Clause goals;
    private SokoPredicate Player;


    public SokobanPlannable(Level level)
    {
        this.level=level;
        goals=getGoal();





    }

    @Override
    public Clause getGoal() {
        Clause goal=new Clause(null);
        Clause kb=getKnowledgeBase();
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
            for (int j = 0; j < level.getBoard().get(i).size(); j++)
                switch (level.getBoard().get(i).get(j)) {
                    case '#':
                        kb.add(new SokoPredicate("wallAt", "", j + "," + i));
                        break;
                    case ' ':
                        kb.add(new SokoPredicate("clearAt", "", j + "," + i));
                        break;
                    case 'A': {
                        kb.add(new SokoPredicate("sokobanAt", "", j + "," + i));
                        this.Player = new SokoPredicate("soko", "", j + "," + i);
                        break;
                    }
                    case '@':
                        boxCount++;
                        kb.add(new SokoPredicate("boxAt", "b" + boxCount, j + "," + i));
                        break;
                    case 'o':
                        goalCount++;
                        kb.add(new SokoPredicate("goalAt", "t" + goalCount, j + "," + i));
                        break;
                }
        }
        return kb;
    }

    @Override
    public Set<Action> getSatisfyingActions(Predicate top) {
        Action a=(Action)top;
        for (Predicate pr:a.getPreconditions().getPredicates()) {
            for (Predicate pkb:getKnowledgeBase().getPredicates()) {
                if(pr.contradicts(pkb)) return null;
            }
        }




        return null;
    }

    @Override
    public Action getSatisfyingAction(Predicate top) {
        return null;
    }

    public ArrayList<ArrayList<Character>> getBoard()
    {
        return this.level.getBoard();
    }
}
