package src.project.Simulation;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import src.project.passengers.Passenger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SimulationLog {
    private List<String> messages;
    private static Logger log;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"log.log";
            log=Logger.getLogger(SimulationLog.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
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
        try{
            FileWriter fw = new FileWriter(customsIssues, true);
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(mess);
            bw.newLine();
            bw.close();
        }catch(IOException e){
            log.warning(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void policeStopped(String mess,Passenger pass){
        System.out.println("AAA");
        
    }
    public void addMessage(String mess){
        addMessage(type.INFO,mess);
    }
    public void addMessage(type a,String mess){
        messages.add(mess);
       // System.out.println(mess);
    }
}
