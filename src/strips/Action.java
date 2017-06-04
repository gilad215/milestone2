package strips;

public class Action extends Predicate{

    private String act;

    private Clause preconditions,effects;

    public Action(String type, String id, String value) {
        super(type, id, value);
        // TODO Auto-generated constructor stub
    }
    public Action(){}

    public Clause getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(Clause preconditions) {
        this.preconditions = preconditions;
    }

    public Clause getEffects() {
        return effects;
    }

    public void setEffects(Clause effects) {
        this.effects = effects;
    }

    public int getX()
    {
        String[] arr=value.split(",");

        return Integer.parseInt(arr[0]);
    }
    public int getY()
    {
        String[] arr=value.split(",");

        return Integer.parseInt(arr[1]);
    }




    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

}
