package Logic.TwoThread;

import Logic.Concurrent.Objects.ConcurrentFolder;
import Logic.Interfaces.IController;
import Logic.Interfaces.IFile;
import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IFolder;
import Logic.TwoThread.Objects.TwoThreadFile;
import Logic.TwoThread.Objects.TwoThreadFolder;
import Presentation.PresentationIObjects.RootFolder;
import Presentation.Result;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class TwoThreadController implements IController {

    private final Queue<IFolder> remaining;
    private final TwoThreadFolder result;
    private TwoThreadFolder current;
    private boolean isFinal;

    public TwoThreadController(String path) {
        File folder = new File(path);
        result = new TwoThreadFolder(folder.getAbsolutePath(), null, folder.getName());
        current = result;
        remaining = new LinkedList<>();
        remaining.add(result);
        isFinal = false;
    }

    private void scanMission() {
        IFileAndFolder toAdd;
        IFolder current;
        File folder;
        while (!remaining.isEmpty()) {
            current = remaining.poll();
            folder = new File(current.getPath());
            String[] subJobs = folder.list();
            if (subJobs != null) {
                for (String path : subJobs) {
                    File file = new File(current.getPath().concat(File.separator).concat(path));
                    if (!file.isDirectory()) {
                        toAdd = new TwoThreadFile(current, file.getAbsolutePath(), file.getName(), file.length());
                        current.addToSize(toAdd.getSize());
                    }
                    else {
                        toAdd = new TwoThreadFolder(file.getAbsolutePath(), (TwoThreadFolder) current, file.getName());
                        remaining.add(toAdd);
                    }
                    current.addFile(toAdd);
                }
            }
        }
        isFinal = true;
    }

    @Override
    public Result scan() {
        try{
            Thread thread = new Thread(this::scanMission);
            thread.start();
            return new Result(new RootFolder(result));
        }
        catch (Exception e) {
            return new Result(e.toString());
        }
    }

    @Override
    public Result navigateTo(String path) {
        if (result.getPath().equals(path.substring(0, result.getPath().length()))) return new Result("Invalid path");
        ConcurrentFolder cur = result;
        String relativePath = path.substring(result.getPath().length());
        String[] relativePathArr = relativePath.split(File.separator);
        for (String dir : relativePathArr) {
            try {
                cur = (ConcurrentFolder) cur.getFiles().get(dir);
            }
            catch (ClassCastException e) {
                return new Result("File");
            }
            catch (Exception e) {
                return new Result("Invalid path");
            }
        }
        current = new TwoThreadFolder(cur.getPath(), (ConcurrentFolder) cur.getParent(), cur.getShortName());
        current.addToSize(cur.getSize());
        for (IFileAndFolder file : cur.getFiles().values()) {
            current.addFile(file);
        }
        return new Result(new RootFolder(current));
    }

    @Override
    public Result update() {
        try{
            return new Result(new RootFolder(current));
        }
        catch (Exception e) {
            return new Result(e.toString());
        }
    }

    @Override
    public boolean delete(String path) { //TODO
        return false;
    }
}


}
