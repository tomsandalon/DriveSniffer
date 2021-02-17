package Logic.TwoThread;

import Logic.Interfaces.IController;
import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IFolder;
import Logic.TwoThread.Objects.TwoThreadFile;
import Logic.TwoThread.Objects.TwoThreadFolder;
import Presentation.PresentationIObjects.IRootFolder;
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
                    }
                    else {
                        toAdd = new TwoThreadFolder(file.getAbsolutePath(), (TwoThreadFolder) current, file.getName());
                        remaining.add((IFolder) toAdd);
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
            Result ret = new Result(new RootFolder(result));
            thread.start();
            return ret;
        }
        catch (Exception e) {
            return new Result(e.toString());
        }
    }

    private String[] split(String s) {
        String separator = (File.separator.equals("\\")) ? "\\\\" : File.separator;
        String temp = s.replaceAll(separator, ",");
        return temp.split(",");
    }

    private boolean isSubpath(String path) {
        return result.getPath().equals(path.substring(0, result.getPath().length()));
    }

    private IFileAndFolder getFromPath(String path) throws Exception {
        IFileAndFolder cur = result;
        String relativePath = path.substring(result.getPath().length() + 1);
        String[] relativePathArr = split(relativePath);
        for (String dir : relativePathArr) {
            cur = ((TwoThreadFolder) cur).getFiles().get(cur.getPath().concat(File.separator).concat(dir));
        }
        return cur;
    }

    @Override
    public Result navigateTo(String path) {
        if (!isSubpath(path)) return new Result("Invalid path");
        TwoThreadFolder cur = null;
        try {
            cur = (TwoThreadFolder) getFromPath(path);
        } catch (ClassCastException e) {
            return new Result("File error");
        } catch (Exception e) {
            return new Result(e.toString());
        }
        current = new TwoThreadFolder(cur.getPath(), (TwoThreadFolder) cur.getParent(), cur.getName());
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
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public Result delete(String path) {
        if (!isFinal) return new Result("Cannot delete files before the scan is complete");
        if (!isSubpath(path)) return new Result("Path is illegal");
        IFileAndFolder cur;
        try {
            cur = getFromPath(path);
        } catch (Exception ignored) {
            return new Result("Path is illegal");
        }
        if (!cur.delete()) {
            try{
                remaining.add((IFolder) cur);
                scanMission();
            } catch (Exception ignored) {}
            return new Result((IRootFolder) null);
        }
        return new Result("Failed to delete " + path);
    }

    @Override
    public Result getCurrentDir() {
        return new Result(new RootFolder(current));
    }
}