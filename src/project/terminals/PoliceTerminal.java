package src.project.terminals;

import src.project.vehicles.Vehicle;

public class PoliceTerminal extends Terminal {
    
    protected String type="police";
    public Boolean access(String type,Vehicle veh){
        return false;
    }
    public void handle(String type,Vehicle veh){
        
    }
}
