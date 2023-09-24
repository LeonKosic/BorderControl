package src.project.vehicles;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import src.project.passengers.items.Doc;
import src.project.passengers.Passenger;
import src.project.terminals.Terminal;
public abstract class Vehicle extends Thread implements Serializable {
    private static Logger log;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"log"+System.nanoTime()+"vehicle.log";
            log=Logger.getLogger(Vehicle.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected Doc declaration=null;
    public static List<Vehicle> queue;
    public Integer position;
    protected Integer maxCapacity=3;
    protected String name;
    private Boolean passedCustoms=false;
    private Boolean deniedPassage=false;
    public static List<Terminal> terminals=Collections.synchronizedList(new ArrayList<>());
    protected List<Passenger> passengers = Collections.synchronizedList(new ArrayList<>());
    public Vehicle(List<Passenger> pass, String name){
        this.name=name;
        this.passengers=pass;
    }
    public List<Passenger> getPassengers(){
        return passengers;
    }
    public void notifyPassedPolice(){
        Integer freeTerminal=checkTerminals("customs");
        while(freeTerminal==-1){
            freeTerminal=checkTerminals("customs");
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                log.warning(e.getMessage());
            }
        }
        position=-2;
    }
    public void notifyPassedCustoms(){
        numPassed++;
        passedCustoms=true;
    }
    public void notifyDeniedPassage(){
        deniedPassage=true;
    }
    public static void setList(List<Vehicle> l){
        queue = l;
        Integer i=0;
        for(Vehicle el:queue){
            el.position=i;
            i+=1;
        }
    }
    public Integer checkTerminals(String type){
        Integer i=0;
        for(Terminal el:terminals){
            if(el.access(type,this)){
                return i;
            }
            i++;
        }
        return -1;
    }
    public static int numPassed=0;
    public void run(){
        while(true){
            if(deniedPassage){
                position=-3;
                break;
            }else if(position >0){
                if(queue.get(position-1)==null){
                    queue.set(position-1,this);
                    queue.set(position,null);
                    position--;
                }
            }else if(position == 0){
                Integer freeTerminal=checkTerminals("police");
                if(freeTerminal>=0){
                    queue.set(0,null);
                    position=-1;
                }
            }else if(passedCustoms){
                break;
            }
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                log.warning(e.getMessage());
            }
        }
        
    }
}
