package strips;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Clause extends Predicate {



    HashSet<Predicate> predicates;

    private void updateDescription(){
        value="{";
        for(Predicate p : predicates){
            value+=p.toString()+" & ";
        }
        value+="}";
    }

    public Clause(Predicate...predicates) {
        super("And", "", "");
        if(predicates!=null){
            this.predicates=new HashSet<>();
            for(Predicate p : predicates){
                this.predicates.add(p);
            }
            updateDescription();
        }
    }

    public void update(Clause effect)
    {
        effect.predicates.forEach((Predicate p)->predicates.removeIf((Predicate r)->p.contradicts(r)));
        predicates.addAll(effect.predicates);
        updateDescription();
    }




    @Override
    public boolean satisfies(Predicate p)
    {
        for(Predicate pr: this.predicates)
        {
            if(pr.satisfies(p)) return true;
        }
        return false;
    }

    public boolean satisfies(Clause clause)
    {
        for(Predicate p: clause.predicates)
        {
            if(!satisfies(p)) return false;
        }
        return true;
    }


    public Set<Predicate> getPredicates() {
        return predicates;
    }

    public void add(Predicate p){
        if(predicates==null)
            predicates=new HashSet<>();
        this.predicates.add(p);
        updateDescription();
    }

    @Override
    public int hashCode()
    {
        return (type+id+value).hashCode();
    }

    @Override
    public String toString()
    {
        return type+"_"+id+"="+value;
    }

    public boolean equals(Predicate p)
    {
        return (type.equals(p.type) && (id.equals(p.id) && value.equals(p.value)));
    }
}
