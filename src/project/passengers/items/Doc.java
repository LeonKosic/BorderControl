package src.project.passengers.items;

import java.io.File;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Doc implements Serializable {
    private Boolean valid=true;
    public Doc(){
        if(Math.random()<=0.03)valid=false;
    }
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"log"+System.nanoTime()+"document.log";
            Logger.getLogger(Doc.class.getName()).addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
