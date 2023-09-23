package src.project.vehicles;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import src.project.passengers.Passenger;
import src.project.terminals.TerminalInterface;
public abstract class Vehicle extends Thread implements Serializable {
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"vehicle"+System.nanoTime()+".log";
            Logger.getLogger(Vehicle.class.getName()).addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<Vehicle> queue;
    public Integer position=-2;
    protected Integer maxCapacity=3;
    protected String name;
    public static List<TerminalInterface> terminals=Collections.synchronizedList(new ArrayList<>());
    protected List<Passenger> passengers = Collections.synchronizedList(new ArrayList<>());
    public Vehicle(List<Passenger> pass, String name){
        this.name=name;
        this.passengers=pass;
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
        for(TerminalInterface el:terminals){
            if(el.access(type,this)){
                return i;
            }
            i++;
        }
        return -1;
    }
    public void run(){
        while(true){
            
            if(position >0){
                if(queue.get(position-1)==null){
                    queue.set(position-1,this);
                    queue.set(position,null);
                }
            }else if(position >= -1){
                Integer freeTerminal=(position==0)?checkTerminals("police"):checkTerminals("customs");
                if(freeTerminal>=0){
                    //TODO
                }
            }else{
                break;
            }
                
        }
        
    }
}
