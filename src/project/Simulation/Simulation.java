package src.project.Simulation;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import javax.swing.JFrame;

import src.project.Watcher.TerminalFileWatcher;
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
    public static Boolean simulationRunning=true;
    public static Long startTime=System.nanoTime();
    public static Boolean paused=false;
    private GridLayoutApp frame;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"Simulation"+System.nanoTime()+".log";
            log= Logger.getLogger(Id.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private Simulation(){
        simulationRunning=true;
    }
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
        terminals.addAll(List.of(new CustomsTerminal("C1"),new TruckCustomsTerminal("CK"),new PoliceTerminal("P1"), new PoliceTerminal("P2"), new TruckPoliceTerminal("P3")));
        TerminalFileWatcher tfw= new TerminalFileWatcher(terminals);
        tfw.start();    
        Vehicle.terminals=terminals;
        createAndShowGUI(vehicles,terminals);
        for(Vehicle veh:vehicles){
            veh.start();
        }
        for(Terminal ter:terminals){
            ter.start();
        }
        while(simulationRunning){
            if(!paused)frame.updateComponents();
            if(terminals.stream().noneMatch(o->Objects.nonNull(o.getVehicle()))&&vehicles.stream().noneMatch(Objects::nonNull)){
                simulationRunning=false;
            }
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                log.warning(e.getMessage());
            }
        }
        SimulationLog.getInstance().addMessage("Simulation finished");
        SimulationLog.getInstance().close();
    }
}
