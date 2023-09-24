package src.project.terminals;

import java.io.File;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import src.project.Simulation.SimulationLog;
import src.project.passengers.Passenger;
import src.project.vehicles.Bus;

public class PoliceTerminal extends Terminal {
    private static Logger log;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+System.nanoTime()+File.separator+"log"+"terminal.log";
            log= Logger.getLogger(Terminal.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public PoliceTerminal(){
        type="police";
    }
    
    public void handle(){
        List<Passenger> passengers=current.getPassengers();
        for (Passenger passenger : passengers) {
             try{
                if(!passenger.checkId()){
                    if(passenger==passengers.get(0)){ //ako je prvi putnik, tj vozac
                        SimulationLog.getInstance().addMessage("Driver "+passenger.getName()+" has illegal ID, vehicle "+current.getName()+" stopped.");
                        current.notifyDeniedPassage();
                        clearTerminal();
                        return;
                    }else{
                        SimulationLog.getInstance().addMessage("Passenger "+passenger.getName()+" has illegal ID");
                        passenger=null;
                    }
                }
                 Thread.sleep((current instanceof Bus)?100:500);
            }catch(InterruptedException e){
                log.warning(e.getMessage());
            }
        }
        current.notifyPassedPolice();
        clearTerminal();
    }
}
