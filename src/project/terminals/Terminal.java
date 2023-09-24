package src.project.terminals;

import java.io.File;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import src.project.Simulation.Simulation;
import src.project.vehicles.Bus;
import src.project.vehicles.Car;
import src.project.vehicles.Vehicle;


public abstract class Terminal extends Thread implements TerminalInterface,Serializable {
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
    protected String type;
    protected Boolean enabled=true;
    protected Vehicle current;
    
    public synchronized final void disableTerminal(){
        enabled=false;
    }
    public synchronized final void enableTerminal(){
        enabled=true;
    }
    public Boolean checkType(Vehicle veh){
        return(veh instanceof Car || veh instanceof Bus);
    }
    public synchronized Boolean access(String type,Vehicle veh){
        if(current==null&&enabled&&type==this.type&&checkType(veh)){
            current=veh;
            return true;
        }
        return false;
    }
    public Vehicle getCurrent(){
        return current;
    }
    public synchronized void clearTerminal(){
        this.current=null;
    }
    public Vehicle getVehicle(){
        return current;
    }
    @Override
    public void run(){
        while(Simulation.simulationRunning){
            Thread.yield();
            if(current!=null){
                handle();
                clearTerminal();
            }
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                log.warning(e.getMessage());
            }
        }
    }
}
