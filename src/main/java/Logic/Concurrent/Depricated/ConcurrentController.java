package Logic.Concurrent.Depricated;

import Logic.Concurrent.Depricated.Objects.ConcurrentFile;
import Logic.Concurrent.Depricated.Objects.ConcurrentFolder;
import Logic.Concurrent.Depricated.Objects.ConcurrentRootFolder;
import Logic.Interfaces.IController;
import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IFolder;
import Presentation.PresentationIObjects.RootFolder;
import Presentation.Result;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentController implements IController {

    private final int NUMBER_OF_THREADS = 10;
    private final int LOOP_LENGTH_IN_MILLIS = 100;
    private final Queue<IFolder> remaining;
    private final ConcurrentRootFolder result;
    private ConcurrentRootFolder current;
    private static final Object monitor = new Object();

    public ConcurrentController(String path) {
        File folder = new File(path);
        result = new ConcurrentRootFolder(folder.getAbsolutePath(), null, folder.getName());
        current = result;
        remaining = new ConcurrentLinkedQueue<>();
        remaining.add(result);
    }

    private void scanMission(IFolder job) {
        IFileAndFolder toAdd;
        File folder = new File(job.getPath());
        String[] subJobs = folder.list();
        if (subJobs == null) return;
        for (String path : subJobs) {
            File file = new File(job.getPath().concat(File.separator).concat(path));
            if (!file.isDirectory()) {
                toAdd = new ConcurrentFile((ConcurrentFolder) job, file.getAbsolutePath(), file.getName(), file.length());
                ((ConcurrentFolder) job).addToSize(toAdd.getSize());
            }
            else {
                toAdd = new ConcurrentFolder(file.getAbsolutePath(), (ConcurrentFolder) job, file.getName());
                remaining.add((IFolder) toAdd);
            }
            job.addFile(toAdd);
            synchronized (monitor) {
                monitor.notifyAll();
            }
        }
    }

    private void poolExecution() {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        do {
            while (!remaining.isEmpty()) {
                IFolder job = remaining.poll();
                if (job != null) poolExecutor.submit(() -> scanMission(job));
            }
            synchronized (monitor) {
                try {
                    monitor.wait(LOOP_LENGTH_IN_MILLIS);
                } catch (InterruptedException ignored) {}
            }
        } while (poolExecutor.getActiveCount() != 0 || !remaining.isEmpty());
        poolExecutor.shutdown();
        result.setIsFinalSize(true);
    }

    @Override
    public Result scan() {
        try{
            Thread thread = new Thread(this::poolExecution);
            thread.start();
            return new Result(new RootFolder(result));
        }
        catch (Exception e) {
            return new Result(e.toString());
        }
    }

    @Override
    public Result navigateTo(String path) {
        if (!result.isFinalSize()) return new Result("Scan not completed yet");
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
        current = new ConcurrentRootFolder(cur.getPath(), (ConcurrentFolder) cur.getParent(), cur.getShortName());
        current.addToSize(cur.getSize());
        for (IFileAndFolder file : cur.getFiles().values()) {
            current.addFile(file);
        }
        current.setIsFinalSize(result.isFinalSize());
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
    public boolean isFinal() { //TODO
        return false;
    }

    @Override
    public Result delete(String path) { //TODO
        return null;
    }
}
