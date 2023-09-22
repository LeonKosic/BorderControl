package src.project.passengers;
import src.project.passengers.items.Id;

public class Passenger extends Person {
    
    public Passenger(Id id,String name){
        super(id,name);
        this.id=id;
        this.name=name;
    }
    
}
