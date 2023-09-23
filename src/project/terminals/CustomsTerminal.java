package src.project.terminals;

import src.project.vehicles.Vehicle;

public class CustomsTerminal extends Terminal{
    protected String type="customs";
    public Boolean access(String type,Vehicle veh){
        return false;
    }
    public void handle(String type,Vehicle veh){
        
    }
}
