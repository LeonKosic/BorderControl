package src.project.vehicles;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import src.project.passengers.Passenger;
public class Vehicle extends Thread implements Serializable {
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"vehicle"+System.nanoTime()+".log";
            Logger.getLogger(Vehicle.class.getName()).addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<Vehicle> queue;
    public static void setList(List<Vehicle> l){
        queue = l;
    }
    public Integer position=-2;
    public Integer maxCapacity=3;
    protected List<Passenger> passengers = Collections.synchronizedList(new ArrayList<>());
    public Vehicle(){

    }
    public Integer checkTerminals(String type){
        //TODO
        return -1;
    }
    public void run(){
        while(true){
            if(position == 0){
                Integer freeTerminal=checkTerminals("police");
                if(freeTerminal>=0){
                    //TODO
                }
            }else if(position ==-1){
                Integer freeTerminal=checkTerminals("customs");
                if(freeTerminal>=0){
                    //TODO
                }
            }else if(position >0){
                if(queue.get(position-1)==null){
                    queue.set(position-1,this);
                    queue.set(position,null);
                }
            }
        }
        
    }
}
