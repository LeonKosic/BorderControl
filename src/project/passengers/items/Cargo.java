package src.project.passengers.items;
import java.io.File;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
public class Cargo implements Serializable{
    private boolean valid;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"log"+System.nanoTime()+File.separator+"Cargo.log";
            Logger.getLogger(Cargo.class.getName()).addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Cargo(){
        valid=(Math.random()>0.1);
    }
    public boolean checkCargo(){
        return valid;
    }
}
