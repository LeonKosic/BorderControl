package src.project.terminals;

import src.project.vehicles.Truck;
import src.project.vehicles.Vehicle;

public class TruckCustomsTerminal extends CustomsTerminal{
    public Boolean checkType(Vehicle veh){
        return(veh instanceof Truck);
    }
}
