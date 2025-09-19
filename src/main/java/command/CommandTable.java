package command;

import application.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sokkiacommand.SokkiaCommand;
import ts.TS;
import ts.TSInterface;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.stream.Stream;

public class CommandTable extends ClassLoader {
    protected final static Logger LOGGER = LogManager.getLogger(CommandTable.class);
    protected ClassLoader classLoader = Main.class.getClassLoader();

    private final Path classPath;
    private final Hashtable<String, SokkiaCommand> commandTable = new Hashtable<>();
    private final TSInterface ts;

    public CommandTable(String classPath, TSInterface ts) {
        this.classPath = Paths.get(classPath);
        this.ts = ts;
    }

    public void loadCommands() throws IOException {
        Path dirpath = Paths.get(classPath.toString());

       try(Stream<Path> stream = Files.list(dirpath)) {
            stream.forEach(p -> {
                try {
                    byte[] classData = Files.readAllBytes(p);
                    Class<?> t = defineClass(null, classData, 0, classData.length);
                    System.out.print(t.getName() + " -> ");
                    if (t.getSuperclass().getName().equals(SokkiaCommand.class.getName())) {
                        try {
                            Class<SokkiaCommand> sokkiaCommandClass = (Class<SokkiaCommand>)classLoader.loadClass(t.getName());
                            SokkiaCommand newCommand = sokkiaCommandClass.getDeclaredConstructor(TSInterface.class).newInstance(ts);
                            commandTable.put(newCommand.getCommandID(), newCommand);
                            System.out.println(" OK");
                        } catch (ClassNotFoundException ex) {
                            LOGGER.warn("loadCommands> command not found:[" + t.getName() + "]", ex);
                        } catch (NoSuchMethodException ex) {
                            LOGGER.warn("loadCommands> method not found:[" + t.getName() + "]", ex);
                        } catch (InvocationTargetException ex) {
                            LOGGER.warn("loadCommands> constructor not found:[" + t.getName() + "]", ex);
                        }
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

    public String dispatchCommand(String str) {
        for(SokkiaCommand command : commandTable.values()) {
            if (command.match(str)) {
                return command.makeResponse(str);
            }
        }
        LOGGER.warn("dispatchCommand> unknown command:" + str);
        return "\u0015\n";
    }
}