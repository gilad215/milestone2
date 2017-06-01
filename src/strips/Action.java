package strips;

public class Action extends Predicate{

    public Action(String type, String id, String value) {
        super(type, id, value);
        // TODO Auto-generated constructor stub
    }

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

    Clause preconditions,effects;

}
