package src.project.passengers.items;

import java.io.File;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class Id implements Serializable {
    private Boolean valid=true;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"ID"+System.nanoTime()+".log";
            Logger.getLogger(Id.class.getName()).addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    int num;
    public Boolean checkId(){
        return valid;
    }
    public Id(int num){
        this.num=num;
        if(Math.random()<=0.03)valid=false;
    }
}
