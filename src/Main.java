package src;
import src.project.Simulation.Simulation;

public class Main{   
    public static void main(String[] args){
       Simulation sim=Simulation.getInstance();
       sim.start(); 
    }
}