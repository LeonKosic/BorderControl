package src.project.terminals;



import java.io.File;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import src.project.Simulation.SimulationLog;
import src.project.passengers.Passenger;
import src.project.vehicles.Bus;

public class CustomsTerminal extends Terminal{
    private static Logger log;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"Customs"+System.nanoTime()+".log";
            log= Logger.getLogger(Terminal.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public CustomsTerminal(){
        type="customs";
    }
    protected Boolean specialCheck(){
        if(current instanceof Bus){
            List<Passenger> passengers=current.getPassengers();
            for (Passenger passenger : passengers) {
                if(!passenger.checkCargo()){
                    
                    SimulationLog.getInstance().CustomsStopped("Passenger "+passenger.getName()+" has illegal cargo");
                    if(passenger==passengers.get(0)){ //ako je prvi putnik, tj vozac
                        SimulationLog.getInstance().CustomsStopped("Driver has illegal cargo, vehicle "+current.getName()+" stopped.");
                        return false;
                    }else{
                        passenger=null;
                    }
                }
                try{
                     Thread.sleep(100);
                }catch(InterruptedException e){
                    log.warning(e.getMessage());
                }
            }
        }else{
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){
                log.warning(e.getMessage());
            }
        }
        return true;
    }
    public void handle(){
        
        if(!specialCheck()){
            current.notifyDeniedPassage();
        }else{
            current.notifyPassedCustoms();
        }
        clearTerminal(); 
    }
}
