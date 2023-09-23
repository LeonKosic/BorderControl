package src.project.terminals;

import src.project.vehicles.Vehicle;
import src.project.vehicles.Truck;

public class CustomsTerminal extends Terminal{
    public CustomsTerminal(){
        type="customs";
    }
    public Boolean checkType(Vehicle veh){
        return false;
    }
    public synchronized Boolean access(String type,Vehicle veh){
        if(current==null&&type==this.type&&(veh instanceof Truck)){
            current=veh;
            return true;
        }
        return false;
    }
    public void handle(String type,Vehicle veh){
        
    }
}
