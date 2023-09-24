package src.project.Simulation;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import javax.swing.JFrame;
import src.project.generators.VehicleGenerator;
import src.project.gui.GridLayoutApp;
import src.project.passengers.items.Id;
import src.project.terminals.CustomsTerminal;
import src.project.terminals.PoliceTerminal;
import src.project.terminals.Terminal;
import src.project.terminals.TruckCustomsTerminal;
import src.project.terminals.TruckPoliceTerminal;
import src.project.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
public class Simulation extends Thread {
    private static Logger log;
    private static Simulation single=null;
    private GridLayoutApp frame;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"log"+System.nanoTime()+"Simulation.log";
            log= Logger.getLogger(Id.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private Simulation(){}
    public static synchronized Simulation getInstance(){
        if(single==null){
            single = new Simulation();
        }
        return single;
    }
    private void createAndShowGUI(List<Vehicle> veh,List<Terminal> ter) {
        frame = new GridLayoutApp("Border control",veh,ter);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentsMain(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    } 
    @Override
    public void run(){
        List<Vehicle> vehicles=VehicleGenerator.generate();
        Vehicle.setList(vehicles);
        List<Terminal> terminals = Collections.synchronizedList(new ArrayList<>());
        terminals.addAll(List.of(new CustomsTerminal(),new TruckCustomsTerminal(),new PoliceTerminal(), new PoliceTerminal(), new TruckPoliceTerminal()));
        Vehicle.terminals=terminals;
        createAndShowGUI(vehicles,terminals);
        for(Vehicle veh:vehicles){
            veh.start();
        }
        for(Terminal ter:terminals){
            ter.start();
        }
        while(true){
            frame.updateComponents();
            if(vehicles.stream().noneMatch(Objects::nonNull))break;
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                log.warning(e.getMessage());
            }
        }
    }
}
