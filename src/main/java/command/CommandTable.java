package command;

import application.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sokkiacommand.SokkiaCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Stream;

public class CommandTable extends ClassLoader {
    protected final static Logger LOGGER = LogManager.getLogger(CommandTable.class);
    protected ClassLoader classLoader = Main.class.getClassLoader();

    private Path classPath;
    private Hashtable<String, SokkiaCommand> commandTable = new Hashtable<>();

    public CommandTable(String classPath) {
        this.classPath = Paths.get(classPath);
    }

    public void loadSubclasses() throws IOException {
        Path dirpath = Paths.get(classPath.toString());

       try(Stream<Path> stream = Files.list(dirpath)) {
            stream.forEach(p -> {
                try {
                    byte[] classData = Files.readAllBytes(p);
                    Class<?> t = defineClass(null, classData, 0, classData.length);
                    System.out.print(t.getName() + " -> ");
                    if (t.getSuperclass().getName().equals(SokkiaCommand.class.getName())
                    || t.getName().equals(SokkiaCommand.class.getName())) {
                        Class<SokkiaCommand> sokkiaCommandClass= (Class<SokkiaCommand>)t;
                        SokkiaCommand newCommand = sokkiaCommandClass.newInstance();
                        commandTable.put(newCommand.getCommandID(), newCommand);
                        System.out.println(" OK");
                    } else {
                        System.out.println(" NG");
                    }

                } catch (IOException ex) {
                    LOGGER.warn("loadSubclasses>", ex);
                } catch (IllegalAccessException | InstantiationException ex) {
                    LOGGER.warn("loadSubclasses>", ex);
                }
            });
        }catch(IOException e) {
            System.out.println(e);
        }
    }

    public String dispatchCommand(String commandID, String args) {
        SokkiaCommand command = commandTable.get(commandID);
        if (command == null) {
            return "UNKNOWN";
        }
        return command.makeResponse(args);
    }
}