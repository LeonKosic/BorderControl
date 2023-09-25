package src.project.Simulation;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import src.project.passengers.Passenger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SimulationLog {
    private List<String> messages;
    private List<String> carsBlocked;
    private List<String> carsPassed;
    private Integer countObjects=0;
    private Integer readObjects=0;
    private String resPol="";
    private static Logger log;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"Simlog"+System.nanoTime()+".log";
            log=Logger.getLogger(SimulationLog.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    FileInputStream fileIn ;
    FileOutputStream f;
    ObjectOutputStream o;
    ObjectInputStream objIn ;
    private File policeIssues=new File("PoliceIssues"+System.nanoTime()+".data");
    private File customsIssues=new File("CustomIssues"+System.nanoTime()+".txt");
    private static SimulationLog single=null;
    private SimulationLog(){
        messages=new ArrayList<>();
        carsBlocked=new ArrayList<>();
        carsBlocked=new ArrayList<>();
        carsPassed=new ArrayList<>();
        resPol="";
        readObjects=0;
        try{
            policeIssues.createNewFile();
            customsIssues.createNewFile();
            fileIn = new FileInputStream(policeIssues);
            f = new FileOutputStream(policeIssues,true);
            o = new ObjectOutputStream(f);
            objIn = new ObjectInputStream(fileIn);
        }catch(IOException e){
            System.out.println(e.getMessage());
            log.warning(e.getMessage());
        }
    }
    public void carPassed(String veh){
        carsPassed.add(veh);
    }
    public void carBlocked(String veh){
        carsBlocked.add(veh);
    }
    public String getMessages(){
        return String.join("\n",messages);
    }
    public String getBlocked(){
        return String.join("\n", carsBlocked);
    }
    public String getPassed(){
        return String.join("\n", carsPassed);
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
            o.writeObject(pass);
            countObjects++;
        }catch(Exception e){
            log.warning(e.getMessage());
        }
    }
    public String getCustomsReport(){
        String res="";
        try{
            FileReader fr=new FileReader(customsIssues);
            BufferedReader br=new BufferedReader(fr);
            res= br.lines().collect(Collectors.joining(System.lineSeparator()));
            br.close();fr.close();
        }catch(Exception e){
            log.warning(e.getMessage());
        }
        return res;
    }
    public String getPoliceReport(){
        try{
            for(;readObjects<countObjects;readObjects++){
                resPol+=(Passenger)objIn.readObject()+System.lineSeparator();
            }
        }catch(Exception e){
            log.warning(e.getMessage());
        }
        return resPol;
    }
    public void close(){
        try{
            objIn.close();
            fileIn.close();
            o.close(); 
            f.close();
        }catch(IOException e){
            log.warning(e.getMessage());
        }
    }
    public void addMessage(String mess){
        messages.add(mess);
    }
}
