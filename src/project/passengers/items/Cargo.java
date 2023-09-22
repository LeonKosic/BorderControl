package src.project.passengers.items;

public class Cargo {
    private boolean valid;
    public Cargo(){
        valid=(Math.random()<=0.1);
    }
    public boolean checkValidity(){
        return valid;
    }
}
