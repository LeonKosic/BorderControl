package src.project.passengers.items;

import java.io.File;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Doc implements Serializable {
    private boolean valid;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"document"+System.nanoTime()+".log";
            Logger.getLogger(Doc.class.getName()).addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Doc(){
        
    }
}
