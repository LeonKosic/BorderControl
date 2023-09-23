package src.project.passengers;
import src.project.passengers.items.Cargo;
import src.project.passengers.items.Id;

public class Passenger extends Person {
    Cargo cargo;
    public Passenger(Id id,String name){
        super(id,name);
        cargo=new Cargo();
    }
    public Boolean checkCargo(){
        return cargo.checkCargo();
    }
}
