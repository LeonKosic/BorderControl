package src.project.Watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.WatchService;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

import src.project.Simulation.Simulation;
import src.project.terminals.Terminal;

public class TerminalFileWatcher extends Thread{
    private List<Terminal> terminals;
    public WatchService watch;
    private String path;
    private File file;
    private static Logger log;
    static {
        try {
            String path=System.getProperty("user.dir")+File.separator+"logs"+File.separator+"terFileWatch.log";
            log=Logger.getLogger(TerminalFileWatcher.class.getName());
            log.addHandler(new FileHandler(path));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public TerminalFileWatcher(List<Terminal> terminals){
        this.terminals=terminals;
        try{
            watch = FileSystems.getDefault().newWatchService();
            createTerminalFile();
        }catch(IOException e){
            log.warning(e.getMessage());
        }
    }
    public void createTerminalFile() throws IOException{
        createTerminalFile(System.getProperty("user.dir")+File.separator+"pause", "pausedTerminals.txt");
    }
    public void createTerminalFile(String path,String filename) throws IOException{
        file=new File(path+File.separator+filename);
        this.path=path;
        file.createNewFile();
    }
    @Override
    public void run(){
        try{
            Paths.get(path).register(watch,StandardWatchEventKinds.ENTRY_MODIFY);   
        }catch(IOException e){
            log.warning(e.getMessage());
        }
        while(Simulation.simulationRunning){
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
                wkey.reset();
            }catch(Exception e){
               // System.out.println("AAA");
                log.warning(e.getMessage());
            }
        }
    }
    
}
