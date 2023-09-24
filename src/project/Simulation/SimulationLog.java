package src.project.Simulation;
import java.util.List;

import src.project.passengers.Passenger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SimulationLog {
    private List<String> messages;
    private enum type{
        VEHICLE_DENIED,
        PASSENGER_DENIED,
        INFO
    }
    private File policeIssues=new File("PoliceIssues"+System.nanoTime()+".data");
    private File customsIssues=new File("CustomIssues"+System.nanoTime()+".txt");
    private static SimulationLog single=null;
    private SimulationLog(){
        messages=new ArrayList<>();
        try{
            policeIssues.createNewFile();
        }catch(IOException e){
        } 
        try{
            customsIssues.createNewFile();
        }catch(IOException e){
        }
    }
    public String getMessages(){
        return String.join("\n",messages);
    }
    public static synchronized SimulationLog getInstance(){
        if(single==null){
            single=new SimulationLog();
        }
        return single;
    }
    public void CustomsStopped(String mess){
        addMessage(type.VEHICLE_DENIED,mess);
    }
    public void policeStopped(String mess,Passenger pass){
        addMessage(type.PASSENGER_DENIED,mess);
        
    }
    public void addMessage(String mess){
        addMessage(type.INFO,mess);
    }
    public void addMessage(type a,String mess){
        messages.add(mess);
       // System.out.println(mess);
    }
}
