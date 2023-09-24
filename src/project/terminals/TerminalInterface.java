package src.project.terminals;

import src.project.vehicles.Vehicle;

public interface TerminalInterface{
    public abstract Boolean access(String type,Vehicle veh);
    public abstract void handle();
    public abstract void disableTerminal();
    public abstract void enableTerminal();
}
