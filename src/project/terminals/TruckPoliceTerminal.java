package src.project.terminals;

import src.project.vehicles.Truck;
import src.project.vehicles.Vehicle;

public class TruckPoliceTerminal extends PoliceTerminal {
    public TruckPoliceTerminal(String name){
        super(name);
    }
    public Boolean checkType(Vehicle veh){
        return(veh instanceof Truck);
    }
}
