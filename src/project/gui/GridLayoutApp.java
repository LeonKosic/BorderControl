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

import src.project.Simulation.SimulationLog;
import src.project.terminals.Terminal;
import src.project.vehicles.Bus;
import src.project.vehicles.Car;
import src.project.vehicles.Truck;
import src.project.vehicles.Vehicle;
public class GridLayoutApp extends JFrame{
    GridLayout mainLayout = new GridLayout(0,3);
    public List<Vehicle> vehicles;
    public List<Terminal> terminals;
    private final List<JButton> termButtons=Collections.synchronizedList(new ArrayList<JButton>());
    private final List<JButton> firstVehs=Collections.synchronizedList(new ArrayList<JButton>());
    JTextPane textPane= new JTextPane();
    JButton pause = new JButton("Pause");
    JButton others= new JButton("Others");
    JButton passIssues = new JButton("Passenger Issues");
    JButton vehicleIssues = new JButton ("Vehicle Issues");
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
        textPane.setText(SimulationLog.getInstance().getMessages());
        for(int i=0;i<5;i++){
            Vehicle ter=terminals.get(i).getCurrent();
            JButton butt=termButtons.get(i);
            if(ter==null){
                butt.setBackground(Color.darkGray);
            }else{
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
        Dimension dim= new Dimension((int)(buttonSize.getWidth() * 2.5)+20, (int)(buttonSize.getHeight() * 3.5)+20 * 2);
        panel.setPreferredSize(dim);
        for(int i=0;i<5;i++){
            panel.add(termButtons.get(i));
            if(i==0){
                panel.add(new JLabel(""));
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
        textArea.setPreferredSize(new Dimension(300, 500));
        textArea.add(textPane);
        options.add(pause);
        options.add(others);
        options.add(passIssues);
        options.add(vehicleIssues);
       
        pane.add(panel,BorderLayout.NORTH);
        pane.add(textArea,BorderLayout.CENTER);
        pane.add(options,BorderLayout.SOUTH);
    }
}
