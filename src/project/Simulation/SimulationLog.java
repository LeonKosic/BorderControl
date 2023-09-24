package src.project.Simulation;
import java.util.List;
import java.util.ArrayList;

public class SimulationLog {
    private List<String> messages;
    private enum type{
        VEHICLE_DENIED,
        PASSENGER_DENIED,
        INFO
    }
    private static SimulationLog single=null;
    private SimulationLog(){
        messages=new ArrayList<>();
    }
    public static synchronized SimulationLog getInstance(){
        if(single==null){
            single=new SimulationLog();
        }
        return single;
    }
    public void vehicleStopped(String mess){
        addMessage(type.VEHICLE_DENIED,mess);
    }
    public void passengerStopped(String mess){
        addMessage(type.PASSENGER_DENIED,mess);
    }
    public void addMessage(String mess){
        addMessage(type.INFO,mess);
    }
    public void addMessage(type a,String mess){
        messages.add(mess);
        System.out.println(mess);
    }
}
