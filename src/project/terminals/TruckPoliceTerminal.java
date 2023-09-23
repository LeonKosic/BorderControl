package src.project.terminals;

import src.project.vehicles.Truck;
import src.project.vehicles.Vehicle;

public class TruckPoliceTerminal extends PoliceTerminal {
    public Boolean checkType(Vehicle veh){
        return(veh instanceof Truck);
    }
}
