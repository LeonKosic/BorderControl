package src.project.generators;
import src.project.passengers.Passenger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import src.project.passengers.items.Id;
public class PassengerGenerator {
    private static int increment=0;
    public static List<Passenger> generate(int NumPassengers){
         List<Passenger> res = Collections.synchronizedList(new ArrayList<>());
         for(int i=0;i<NumPassengers;i++){
            res.add(new Passenger(new Id(increment++), "Test test"));
         }
         return res;
    }
}
