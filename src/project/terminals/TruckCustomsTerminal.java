package src.project.terminals;


import java.io.File;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import src.project.passengers.Passenger;
import src.project.vehicles.Bus;
import src.project.vehicles.Truck;
import src.project.vehicles.Vehicle;

public class TruckCustomsTerminal extends CustomsTerminal{
    private static Logger log;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+"logs"+File.separator+"log"+System.nanoTime()+"terminal.log";
            log= Logger.getLogger(Terminal.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Boolean checkType(Vehicle veh){
        return(veh instanceof Truck);
    }
    @Override
    protected Boolean specialCheck(){
        Truck truck = (Truck) current;
        if(truck.needDoc){
            truck.generateDocumentation();
            if(truck.declaredWeight>truck.realWeight)return false;
        }
        return true;
    }
}
