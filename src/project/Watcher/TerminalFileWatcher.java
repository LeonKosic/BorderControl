package src.project.Watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.WatchService;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import src.project.terminals.TerminalInterface;

public class TerminalFileWatcher extends Thread{
    private List<TerminalInterface> terminals;
    public WatchService watch;
    private File file;
    private static Logger log;
    boolean isRunning=true;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"log"+System.nanoTime()+"vehicle.log";
            log=Logger.getLogger(TerminalFileWatcher.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public TerminalFileWatcher(List<TerminalInterface> terminals){
        this.terminals=terminals;
        try{
            createTerminalFile();
        }catch(IOException e){
            log.warning(e.getMessage());
        }
    }
    public void createTerminalFile() throws IOException{
        createTerminalFile(System.getProperty("user.dir")+File.separator+"pause"+ File.separator, "pausedTerminals.txt");
    }
    public void createTerminalFile(String path,String filename) throws IOException{
        file=new File(path+File.separator+filename);
        if(file.createNewFile()){
            log.info("New terminal file created");
        }else{
            log.info("Terminal file already exists");
        }
    }
    @Override
    public void run(){
        try{
            Paths.get(file.getAbsolutePath()).register(watch,StandardWatchEventKinds.ENTRY_MODIFY);   
        }catch(IOException e){
            log.warning(e.getMessage());
        }
        while(isRunning){
            try{   
                WatchKey wkey=this.watch.take();
                for(WatchEvent<?> we: wkey.pollEvents()){
                    if(we.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)&&we.context().toString().equals(file.getName())){
                        List<Integer> terminalsStopped=Files.readAllLines(Paths.get(file.getAbsolutePath())).stream().filter(s->s.matches("[0-9]+")).map(Integer::parseInt).collect(Collectors.toList());
                        for(int i=0;i<terminals.size();i++){
                            if(terminalsStopped.contains(i)){
                                terminals.get(i).disableTerminal();
                            }else{
                                terminals.get(i).enableTerminal();
                            }
                        }
                    }
                }
            }catch(Exception e){
                log.warning(e.getMessage());
            }
        }
    }
    
}
