package src.project.vehicles;

import java.util.List;
import src.project.passengers.Passenger;

public class Truck extends Vehicle {
    public Integer declaredWeight;
    public Integer realWeight;
    public static Integer maxCapacity=3;
    private final static java.util.Random rand = new java.util.Random();
    public Truck(List<Passenger> pass, String name){
        super(pass,name);
        declaredWeight=rand.nextInt(1500)+2000;
        realWeight=(int)((Math.random()<=0.2)?declaredWeight*(1+Math.random()*.3):declaredWeight*(Math.random()*.3+.7));
    }
}
