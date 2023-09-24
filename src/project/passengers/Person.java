package src.project.passengers;

import java.io.Serializable;
import src.project.passengers.items.Id;
public abstract class Person implements Serializable {
    public Id id;
    private String name;
    
    public Person(Id id,String name){
        this.id=id;
        this.name=name;
    }
    public String getName(){
        return name;
    }
    @Override
    public boolean equals(Object o){
        if( o instanceof Person p){
            return id.equals((p.id));
        }
        return false;
    }
    public Boolean checkId(){
        return id.checkId();
    }
}
