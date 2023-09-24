package src.project.passengers.items;

import java.io.File;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Doc implements Serializable {
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"log.log";
            Logger.getLogger(Doc.class.getName()).addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
