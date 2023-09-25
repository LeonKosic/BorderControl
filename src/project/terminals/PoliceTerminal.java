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
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"Police"+System.nanoTime()+".log";
            log= Logger.getLogger(Terminal.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public PoliceTerminal(String name){
        super(name);
        type="police";
    }
    
    public void handle(){
        SimulationLog.getInstance().addMessage("Vehicle "+current.getName()+" arrived at police terminal ");
        List<Passenger> passengers=current.getPassengers();
        for (Passenger passenger : passengers) {
             try{
                if(!passenger.checkId()){
                    SimulationLog.getInstance().addMessage("Passenger "+passenger.getName()+" has an illegal ID");
                    SimulationLog.getInstance().policeStopped(passenger);
                    if(passenger==passengers.get(0)){ //ako je prvi putnik, tj vozac
                        SimulationLog.getInstance().addMessage("Driver has an illegal ID, vehicle "+current.getName()+" stopped.");
                        current.notifyDeniedPassage();
                        SimulationLog.getInstance().carBlocked(current.getName());
                        clearTerminal();
                        return;
                    }else{
                        passenger=null;
                    }
                }
                 Thread.sleep((current instanceof Bus)?100:500);
            }catch(InterruptedException e){
                log.warning(e.getMessage());
            }
        }
        current.notifyPassedPolice();
        SimulationLog.getInstance().addMessage("Vehicle "+current.getName()+" passed the police terminal");
        clearTerminal();
    }
}
