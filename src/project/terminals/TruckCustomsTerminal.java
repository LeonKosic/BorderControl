package src.project.terminals;


import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import src.project.Simulation.SimulationLog;
import src.project.vehicles.Truck;
import src.project.vehicles.Vehicle;

public class TruckCustomsTerminal extends CustomsTerminal{
    private static Logger log;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"TruckCustoms.log";
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
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){
            log.warning(e.getMessage());
        }
        Truck truck = (Truck) current;
        if(truck.needDoc){
            truck.generateDocumentation();
            if(truck.declaredWeight<truck.realWeight){
                SimulationLog.getInstance().CustomsStopped("Truck "+current.getName()+" is heavier than declared, Vehicle stopped");
                return false;
            }
        }
        return true;
    }
}
