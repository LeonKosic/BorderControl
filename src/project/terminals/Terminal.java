package src.project.terminals;

import java.io.File;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import src.project.vehicles.Vehicle;


public class Terminal extends Thread implements Serializable {
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"terminal"+System.nanoTime()+".log";
            Logger.getLogger(Terminal.class.getName()).addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected Vehicle current;
    public synchronized void clearTerminal(){
        this.current=null;
    }
    @Override
    public void run(){
        while(true){
            Thread.yield();
            if(current!=null){
            }
        }
    }
}
