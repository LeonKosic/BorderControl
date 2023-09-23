package src.project.vehicles;
import src.project.passengers.Passenger;
import java.util.List;
public class Car extends Vehicle{
    public Car(List<Passenger> pass, String name){
        super(pass,name);
    }
    public static Integer maxCapacity=5;
}
