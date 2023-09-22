package src.project.generators;
import src.project.vehicles.Car;
import src.project.vehicles.Vehicle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import src.project.vehicles.Bus;
import src.project.vehicles.Truck;
public class VehicleGenerator {
    public static List<Vehicle> generate(){
        return generate(35,5,10);
    }
    public static List<Vehicle> generate(int numCars,int numBus, int numTrucks){
        List<Vehicle> res = Collections.synchronizedList(new ArrayList<>());
        for(int i=0;i<numCars;i++){
            res.add(new Car());
        }
        for(int i=0;i<numBus;i++){
            res.add(new Bus());
        }
        for(int i=0;i<numTrucks;i++){
            res.add(new Truck());
        }
        return res;
    }
}
