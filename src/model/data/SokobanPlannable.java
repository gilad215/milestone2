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
            if(p.getType().startsWith("goal")){
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
        Predicate a=top;
        ArrayList<Point> boxes=new ArrayList<>();
        for (Predicate p:kb.getPredicates()) {
            if(p.getType().startsWith("box"))
            {
                boxes.add(new Point(p.getX(),p.getY()));
            }
        }
        Solution sokoSolution=new Solution();

        PriorityQueue<Solution> possibleSolutions=new PriorityQueue<>();
        for (Point box:boxes) {
            System.out.println("Searching for GOAL on X:"+a.getX()+" Y:"+a.getY());
            Solution boxPath=Path("boxAt",box,new Point(a.getX(),a.getY()));
            System.out.println("WE HAVE A SOLUTION IN HAND ~~~~~~~~~~~~~~~~~~~~~~");
           if(boxPath!=null) {
               Point finalbox=box;
               Point sokoPos=getSoko();
               for (Object searchAction:boxPath.getActions()) {
                   SearchAction action=(SearchAction)searchAction;
                   Point boxPush = null;
                   System.out.println("box action is :"+action.toString());
                   switch (action.toString()) {
                       case ("right"): {
                           boxPush = new Point(finalbox.getX() - 1, finalbox.getY());
                           finalbox.setX(finalbox.getX()+1);
                           break;
                       }
                       case ("left"): {
                           boxPush = new Point(finalbox.getX() + 1, finalbox.getY());
                           finalbox.setX(finalbox.getX()-1);
                           break;
                       }
                       case ("up"): {
                           boxPush = new Point(finalbox.getX(), finalbox.getY() + 1);
                           finalbox.setY(finalbox.getY()-1);
                           break;
                       }
                       case ("down"): {
                           boxPush = new Point(finalbox.getX(), finalbox.getY() - 1);
                           finalbox.setY(finalbox.getY()+1);
                           break;
                       }
                   }
                   Solution sokoPath = Path("sokobanAt", sokoPos, boxPush);
                   if (sokoPath.actionSize() != 0) {
                       sokoSolution.addToActions(sokoPath.getActions());
                       sokoSolution.addToActions(action);
                       for (Object sa:sokoSolution.getActions()) {
                           SearchAction saction=(SearchAction)sa;
                           switch (saction.toString())
                           {
                               case ("right"): {
                                   sokoPos.setX(sokoPos.getX()+1);
                                   break;
                               }
                               case ("left"): {
                                   sokoPos.setX(sokoPos.getX()-1);
                                   break;
                               }
                               case ("up"): {
                                   sokoPos.setY(sokoPos.getY()-1);
                                   break;
                               }
                               case ("down"): {
                                   sokoPos.setY(sokoPos.getY()+1);
                                   break;
                               }
                           }
                       }
                    }

               }
            break;
           }
           else System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Didnt find solution for box:" + box.toString()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        }



        if(!sokoSolution.getActions().isEmpty()) {
            List<SearchAction> sokoActions=sokoSolution.getActions();
            System.out.println("~~~~~~~~~~~~OUR FINAL ACTIONS ~~~~~~~~~~~~");
            System.out.println(sokoActions.toString());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            List<Action> actions=new ArrayList<>();

            Point sokobanPos=getSoko();

            for (SearchAction sAction:sokoActions) { //soko path


                Action predicate=new Action();
                predicate.setAct(sAction.getAct());
                System.out.println("Building a Clause....ACTION:"+predicate.getAct());
                switch(sAction.getAct())
                {
                    case("right"):
                    {
                        predicate=newAction(sokobanPos,"right");
                        break;
                    }
                    case("left"):
                    {
                        predicate=newAction(sokobanPos,"left");
                        break;
                    }
                    case("up"):
                    {
                        predicate=newAction(sokobanPos,"up");
                        break;
                    }
                    case("down"):
                    {
                        predicate=newAction(sokobanPos,"down");
                        break;
                    }
                }
                actions.add(predicate);

            }
            return actions;
        }

        return null;
    }

    private Solution Path(String type,Point initial, Point target)
    {
        SokobanSearchable<Point> Search = null;
        Solution path=new Solution();

        if (type.equals("boxAt")){
            System.out.println("Searching for BOX PATH"); Search = new SokobanSearchable<>(level, "box", initial, target);}
        if (type.equals("sokobanAt")) {
            System.out.println("SEARCHING FOR SOKO MY LOVELY HERO~~~~~~~~~~~~~~~~");
            System.out.println("INITIAL SOKO POS:" + initial.toString() + " TARGET BOX POS:" + target.toString());
            Search = new SokobanSearchable<>(level, "soko", initial, target);
        }

        BFS<Point> searcher = new BFS<>();
        if (Search != null) {
            path = searcher.search(Search);
        }
        //System.out.println("DEBUGGGGG"+path.toString());
        //if(!path.getActions().isEmpty()) return path;
        return path;
    }

    @Override
    public Action getSatisfyingAction(Predicate top) {
        return null;
    }

    public Action newAction(Point soko,String move)
    {
        Action action=new Action();
        switch(move)
        {
            case("right"):
            {
                action.setPreconditions(new Clause(new SokoPredicate("clearAt","",(soko.getX()+1)+","+soko.getY())));
                if(level.getBoard().get(soko.getY()).get(soko.getX() + 1) =='@')
                {
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",(soko.getX()+1)+","+soko.getY())
                    ,new SokoPredicate("boxAt","?",(soko.getX()+2)+","+soko.getY())));
                    soko.setX(soko.getX()+1);
                    break;
                }
                else
                {
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",(soko.getX()+1)+","+soko.getY())));
                    soko.setX(soko.getX()+1);
                    break;
                }
            }
            case("left"):
            {
                action.setPreconditions(new Clause(new SokoPredicate("clearAt","",(soko.getX()-1)+","+soko.getY())));
                if(level.getBoard().get(soko.getY()).get(soko.getX() - 1) =='@')
                {
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",(soko.getX()-1)+","+soko.getY())
                            ,new SokoPredicate("boxAt","?",(soko.getX()-2)+","+soko.getY())));
                    soko.setX(soko.getX()-1);
                    break;
                }
                else
                {
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",(soko.getX()-1)+","+soko.getY())));
                    soko.setX(soko.getX()-1);
                    break;
                }
            }
            case("up"):
            {
                action.setPreconditions(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+(soko.getY()-1))));    //clear(target)
                if(level.getBoard().get(soko.getY()-1).get(soko.getX()) =='@')
                {
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",soko.getX()+","+(soko.getY()-1))
                            ,new SokoPredicate("boxAt","?",soko.getX()+","+(soko.getY()-2))));
                    soko.setY(soko.getY()-1);
                    break;
                }
                else
                {
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",soko.getX()+","+(soko.getY()-1))));
                    soko.setY(soko.getY()-1);
                    break;
                }
            }
            case("down"):
            {
                action.setPreconditions(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+(soko.getY()+1))));    //clear(target)
                if(level.getBoard().get(soko.getY()+1).get(soko.getX()) =='@')
                {
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",soko.getX()+","+(soko.getY()+1))
                            ,new SokoPredicate("boxAt","?",soko.getX()+","+(soko.getY()+2))));
                    soko.setY(soko.getY()+1);
                    break;
                }
                else
                {
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",soko.getX()+","+(soko.getY()-1))));
                    soko.setY(soko.getY()+1);
                    break;
                }
            }
        }
        return action;
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
