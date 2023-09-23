package src.project.generators;
import src.project.passengers.Passenger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import src.project.passengers.items.Id;
import java.util.Random;
public final class PassengerGenerator {
    private static int increment=0;
    private final static Random rand =  new java.util.Random();;
    public final static List<Passenger> generate(int NumPassengers){
         List<Passenger> res = Collections.synchronizedList(new ArrayList<>());
         for(int i=0;i<NumPassengers-rand.nextInt(NumPassengers);i++){
            res.add(new Passenger(new Id(increment++), NameGenerator.generateName()));
         }
         return res;
    }
}
