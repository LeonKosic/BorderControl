package src.project.vehicles;

import java.util.List;

import src.project.passengers.Passenger;

public class Bus extends Vehicle{
    public static Integer maxCapacity=52;
    public boolean needDeclaration;
    public Bus(List<Passenger> pass, String name){
        super(pass,name);
    }   
}
