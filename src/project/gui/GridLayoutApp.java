package src.project.gui;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.ArrayList;
public class GridLayoutApp extends JFrame{
    GridLayout mainLayout = new GridLayout(0,3);
    private final ArrayList<JButton> terminals=new ArrayList<JButton>();
    private final ArrayList<JButton> firstVehs=new ArrayList<JButton>(); 
    public GridLayoutApp(String name){
       super(name);
    }
    public void setMainLayoutGap(int x){
        mainLayout.setHgap(x);
        mainLayout.setVgap(x);
    }
    public void updateComponents(){
        for(JButton x : firstVehs){
            x.setText("bb");
        }
    }
    public void addComponentsMain(final Container pane){
        JPanel panel=new JPanel(mainLayout);
        JPanel options = new JPanel(new GridLayout(2,3));
        JButton b = new JButton("temp dugme");
        Dimension buttonSize = b.getPreferredSize();
        terminals.add(new JButton("C1"));
        terminals.add(new JButton("CK"));
        terminals.add(new JButton("P1"));
        terminals.add(new JButton("P2"));
        terminals.add(new JButton("PK"));
        panel.setPreferredSize(new Dimension((int)(buttonSize.getWidth() * 2.5)+20,
                (int)(buttonSize.getHeight() * 3.5)+20 * 2));
        for(int i=0;i<5;i++){
            panel.add(terminals.get(i));
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
        options.add(new JButton("Pause"));
        options.add(new JButton("Other vehicles"));
        options.add(new JButton("Show finished"));
        options.add(new JButton("Issues"));
       
        pane.add(panel,BorderLayout.NORTH);
        pane.add(new JSeparator(),BorderLayout.CENTER);
        pane.add(options,BorderLayout.SOUTH);
    }
}
