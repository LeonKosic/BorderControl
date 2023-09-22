package src;
import javax.swing.JFrame;
import src.project.gui.GridLayoutApp;

public class Main{   
    private static void createAndShowGUI() {
        GridLayoutApp frame = new GridLayoutApp("Border control");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentsMain(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    } 
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}