package src.project.Simulation;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import src.project.passengers.Passenger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SimulationLog {
    private List<String> messages;
    private static Logger log;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"Simlog.log";
            log=Logger.getLogger(SimulationLog.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
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
            fw.close();
        }catch(IOException e){
            log.warning(e.getMessage());
        }
        addMessage(mess);
    }
    public void policeStopped(Passenger pass){
        try{
            FileOutputStream f = new FileOutputStream(policeIssues,true);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(pass);
            o.close();
            f.close();
        }catch(Exception e){
            log.warning(e.getMessage());
        }
    }
    public void addMessage(String mess){
        messages.add(mess);
    }
}
