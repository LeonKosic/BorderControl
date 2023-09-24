package src.project.gui;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import src.project.Simulation.Simulation;
import src.project.Simulation.SimulationLog;
import src.project.terminals.Terminal;
import src.project.vehicles.Bus;
import src.project.vehicles.Car;
import src.project.vehicles.Truck;
import src.project.vehicles.Vehicle;
public class GridLayoutApp extends JFrame{
    GridLayout mainLayout = new GridLayout(0,3);
    public List<Vehicle> vehicles;
    public Long pauseTime=0L;
    public Long currentPause=0L;
    JLabel timer= new JLabel();
    public List<Terminal> terminals;
    private final List<JButton> termButtons=Collections.synchronizedList(new ArrayList<JButton>());
    private final List<JButton> firstVehs=Collections.synchronizedList(new ArrayList<JButton>());
    JTextPane textPane= new JTextPane();
    JTextPane otherCarsPane=new JTextPane();
    JTextPane policeStopped=new JTextPane();
    JTextPane customsStopped=new JTextPane();
    JFrame otherCarsFrame=new JFrame();
    JFrame policeFrame=new JFrame();
    JFrame customsFrame=new JFrame();
    JButton pause = new JButton("Pause");
    JButton others= new JButton("Others");
    JButton passIssues = new JButton("Police Terminal Issues");
    JButton vehicleIssues = new JButton ("Customs Terminal Issues");
    
    public GridLayoutApp(String name, List<Vehicle> veh, List<Terminal> ter){
       super(name);
       vehicles=veh;
       terminals=ter;
    }
    public JFrame generateVehicleModal(Vehicle veh){
        JFrame modal=new JFrame();
        modal.setSize(500,500);
        modal.add(new JLabel(veh.getName()));
        modal.add(new JLabel(veh.getPassengers().toString()));
        return modal;
    }
    public void setMainLayoutGap(int x){
        mainLayout.setHgap(x);
        mainLayout.setVgap(x);
    }
    public void updateComponents(){
        timer.setText(""+((System.nanoTime()-Simulation.startTime-pauseTime)/10e8));
        textPane.setText(SimulationLog.getInstance().getMessages());
        for(int i=0;i<5;i++){
            Vehicle ter=terminals.get(i).getCurrent();
            JButton butt=termButtons.get(i);
            ActionListener al[]=butt.getActionListeners();
            for (ActionListener actionListener : al) {
               butt.removeActionListener(actionListener);
            }
            if(ter==null){
                butt.setBackground(Color.darkGray);
            }else{
                butt.addActionListener(e->{
                    generateVehicleModal(ter).setVisible(true);;
                });
                butt.setBackground(Color.orange);
            }
        }
        for(int i=0;i<5;i++){
            final Vehicle veh=vehicles.get(i);
            JButton butt=firstVehs.get(i);
            if(veh instanceof Car){
                butt.setBackground(Color.red);
                butt.setText("V");
            }else if(veh instanceof Truck){
                butt.setBackground(Color.blue);
                butt.setText("K");
            }else if(veh instanceof Bus){
                butt.setBackground(Color.cyan);
                butt.setText("A");
            }else{ 
                butt.setBackground(Color.gray);
                butt.setText("X");
            }
            ActionListener al[]=firstVehs.get(i).getActionListeners();
            for (ActionListener actionListener : al) {
                firstVehs.get(i).removeActionListener(actionListener);
            }
            if(veh instanceof Car||veh instanceof Truck||veh instanceof Bus){
                firstVehs.get(i).addActionListener(e->{
                    generateVehicleModal(veh).setVisible(true);
                });
            }
        }
        otherCarsPane.setText(vehicles.subList(5, vehicles.size()).stream().filter(e->Objects.nonNull(e)).map(e->e.getName()).collect(Collectors.joining(System.getProperty("line.separator"))));
        policeStopped.setText(SimulationLog.getInstance().getPoliceReport());;
        customsStopped.setText(SimulationLog.getInstance().getCustomsReport());
    }
    public void addComponentsMain(final Container pane){
        JPanel panel=new JPanel(mainLayout);
        JPanel options = new JPanel(new GridLayout(2,3));
        JButton b = new JButton("temp dugme");
        Dimension buttonSize = b.getPreferredSize();
        termButtons.add(new JButton("C1"));
        termButtons.add(new JButton("CK"));
        termButtons.add(new JButton("P1"));
        termButtons.add(new JButton("P2"));
        termButtons.add(new JButton("PK"));
        panel.setPreferredSize(new Dimension((int)(buttonSize.getWidth() * 2.5)+20, (int)(buttonSize.getHeight() * 3.5)+20 * 2));
        for(int i=0;i<5;i++){
            panel.add(termButtons.get(i));
            if(i==0){
                panel.add(timer);
            }
        }
       setMainLayoutGap(5);
        for(int i=0;i<5;i++){
            firstVehs.add(new JButton("veh"+i));
            panel.add(new JLabel(""));
            panel.add(firstVehs.get(i));
            panel.add(new JLabel(""));
        }
        JPanel textArea = new JPanel(new GridLayout(0,1));
        Dimension dim= new Dimension(300,500);
        textArea.setPreferredSize(dim);
        textArea.add(textPane);
        this.pause.addActionListener(e->{
            if(Simulation.paused){
                pauseTime+=(System.nanoTime()-currentPause);
            }else{
                currentPause=System.nanoTime();
            }
            Simulation.paused=!Simulation.paused;
        });
        this.passIssues.addActionListener(e->{
            policeFrame.setVisible(true);
        });
        this.vehicleIssues.addActionListener(e->{
            customsFrame.setVisible(true);
        });
        this.others.addActionListener(e->{
            otherCarsFrame.setVisible(true);
        });
        otherCarsFrame.add(otherCarsPane);
        policeFrame.add(policeStopped);
        customsFrame.add(customsStopped);
        otherCarsFrame.setSize(dim);
        policeFrame.setSize(dim);
        customsFrame.setSize(dim);
        options.add(pause);
        options.add(others);
        options.add(passIssues);
        options.add(vehicleIssues);
        pane.add(panel,BorderLayout.NORTH);
        pane.add(textArea,BorderLayout.CENTER);
        pane.add(options,BorderLayout.SOUTH);
    }
}
