package model.data;

import searchLib.data.BFS;
import searchLib.data.SearchAction;
import searchLib.data.Solution;
import strips.*;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class SokobanPlannable implements Plannable {
    private Level level;
    private Clause goals;
    private Clause kb;
    private SokoPredicate Player;
    private int boxCount=0;
    private int goalCount=0;


    public SokobanPlannable(Level lvl)
    {
        this.level=lvl;
        kb=new Clause(null);

        if(!level.getBoard().isEmpty()) {
            System.out.println("loaded level!");
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
        }
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
        return kb;
    }

    @Override
    public List<Action> getSatisfyingActions(Predicate top) {
        Action a=(Action)top;
        for (Predicate pr:a.getPreconditions().getPredicates()) {
            for (Predicate pkb:getKnowledgeBase().getPredicates()) {
                if(pr.contradicts(pkb)) return null;
            }
        }
        ArrayList<Point> boxes=new ArrayList<>();
        for (Predicate p:kb.getPredicates()) {
            if(p.getType().startsWith("box"))
            {
                boxes.add(new Point(p.getX(),p.getY()));
            }
        }
        Solution boxSolution=new Solution();
        Solution sokoSolution=new Solution();

        PriorityQueue<Solution> possibleSolutions=new PriorityQueue<>();
        for (Point box:boxes) {
            Solution boxPath=Path("box",box,new Point(a.getX(),a.getY()));
            if(boxPath.actionSize()!=0)
            {
                String firstAction=boxPath.getActions().get(0).toString();
                Point boxPush=null;
                switch(firstAction) {
                    case ("right"): {
                        boxPush = new Point(box.getX() - 1, box.getY());
                        break;
                    }
                    case ("left"): {
                        boxPush = new Point(box.getX() + 1, box.getY());
                        break;
                    }
                    case ("up"): {
                        boxPush = new Point(box.getX(), box.getY() + 1);
                        break;
                    }
                    case("down"):
                    {
                        boxPush=new Point(box.getX(),box.getY()-1);
                        break;
                    }
                }

                Solution sokoPath=Path("soko",getSoko(),boxPush);
                if(sokoPath.actionSize()!=0)
                {
                    boxSolution.setActions(boxPath.getActions());
                    sokoSolution.setActions(sokoPath.getActions());

                    possibleSolutions.add(new Solution(sokoPath,boxPath));
                }
            }
        }



        if(!possibleSolutions.isEmpty()) {
            Solution finalsolution = possibleSolutions.poll();
            List<SearchAction> searchActions=finalsolution.getActions();

            List<Action> actions=new ArrayList<>();
            Point sokobanPos=getSoko();

            for (SearchAction sAction:searchActions) { //soko path

                Action predicate=new Action();
                predicate.setAct(sAction.getAct());
                switch(sAction.getAct())
                {
                    case("right"):
                    {
                        predicate.setPreconditions(new Clause(new SokoPredicate("clearAt","",(sokobanPos.getX()+1)+","+sokobanPos.getY())));    //clear(target)
                        predicate.setEffects(new Clause(new SokoPredicate("clearAt","",(sokobanPos.getX())+","+sokobanPos.getY()),new SokoPredicate("sokobanAt","",(sokobanPos.getX()+1)+","+sokobanPos.getY())));
                    }
                    case("left"):
                    {
                        predicate.setPreconditions(new Clause(new SokoPredicate("clearAt","",(sokobanPos.getX()-1)+","+sokobanPos.getY())));    //clear(target)
                        predicate.setEffects(new Clause(new SokoPredicate("clearAt","",(sokobanPos.getX())+","+sokobanPos.getY()),new SokoPredicate("sokobanAt","",(sokobanPos.getX()-1)+","+sokobanPos.getY())));
                    }
                    case("up"):
                    {
                        predicate.setPreconditions(new Clause(new SokoPredicate("clearAt","",(sokobanPos.getX()+1)+","+sokobanPos.getY())));    //clear(target)
                        predicate.setEffects(new Clause(new SokoPredicate("clearAt","",(sokobanPos.getX())+","+sokobanPos.getY()),new SokoPredicate("sokobanAt","",(sokobanPos.getX()+1)+","+sokobanPos.getY())));
                    }
                    case("down"):
                    {
                        predicate.setPreconditions(new Clause(new SokoPredicate("clearAt","",(sokobanPos.getX()+1)+","+sokobanPos.getY())));    //clear(target)
                        predicate.setEffects(new Clause(new SokoPredicate("clearAt","",(sokobanPos.getX())+","+sokobanPos.getY()),new SokoPredicate("sokobanAt","",(sokobanPos.getX()+1)+","+sokobanPos.getY())));
                    }
                }

            }

            return actions;
        }


















        return null;
    }

    private Solution Path(String type,Point initial, Point target)
    {
        SokobanSearchable<Point> Search = null;
        Solution path=null;

        if (type.equals("boxAt")) Search = new SokobanSearchable<>(level, "box", initial, target);
        if (type.equals("sokobanAt")) Search = new SokobanSearchable<>(level, "soko", initial, target);

        BFS<Point> searcher = new BFS<>();
        if (Search != null) {
            path = searcher.search(Search);
        }
        if(!path.getActions().isEmpty()) return path;
        else return null;
    }

    @Override
    public Action getSatisfyingAction(Predicate top) {
        return null;
    }

    public ArrayList<ArrayList<Character>> getBoard()
    {
        return this.level.getBoard();
    }


    public int getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(int boxCount) {
        this.boxCount = boxCount;
    }

    public int getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(int goalCount) {
        this.goalCount = goalCount;
    }

    private Point getSoko() {
        for (Predicate p : getKnowledgeBase().getPredicates()) {
            if (p.getType().startsWith("sokoban")) {
                return new Point(p.getX(), p.getY());
            }
        }
        return null;
    }

}
