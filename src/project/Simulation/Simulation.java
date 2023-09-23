package src.project.Simulation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JFrame;

import src.project.generators.VehicleGenerator;
import src.project.gui.GridLayoutApp;
import src.project.passengers.items.Id;
import src.project.vehicles.Vehicle;
import java.util.List;
public class Simulation extends Thread {
    private static Logger log;
    private static GridLayoutApp frame;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"log"+System.nanoTime()+"Simulation.log";
            log= Logger.getLogger(Id.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
     private static void createAndShowGUI() {
        frame = new GridLayoutApp("Border control");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentsMain(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    } 
    @Override
    public void run(){
        List<Vehicle> vehicles=VehicleGenerator.generate();
        //System.out.println(Arrays.toString(vehicles.toArray()));
        createAndShowGUI();
        while(true){
            frame.updateComponents();
            if(vehicles.isEmpty())break;
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                log.warning(e.getMessage());
            }
        }
    }
}
