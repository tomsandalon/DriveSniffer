package Logic.Concurrent;

import Logic.Concurrent.Objects.ConcurrentFile;
import Logic.Concurrent.Objects.ConcurrentFolder;
import Logic.Concurrent.Objects.ConcurrentRootFolder;
import Logic.Interfaces.IController;
import Logic.Interfaces.IFile;
import Logic.Interfaces.IFileAndFolder;
import Logic.Interfaces.IFolder;
import Presentation.Result;

import java.io.File;
import java.util.Formattable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentController implements IController {

    private final int NUMBER_OF_THREADS = 10;
    private final int NUMBER_OF_LOOPS = 4;
    private final int LOOP_LENGTH_IN_MILLIS = 100;
    private final Queue<IFolder> remaining;
    private final ConcurrentRootFolder result;
    private final boolean isDone = false;
    private final Thread[] threads = new Thread[NUMBER_OF_THREADS];
    private static final Object monitor = new Object();




    public ConcurrentController(String path) {
        File folder = new File(path);
        result = new ConcurrentRootFolder(folder.getAbsolutePath(), null, folder.getName());
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
    public Result scan() {//TODO
//        Thread thread = new Thread(this::poolExecution);
//        thread.start();
//        return result;
        return null;
    }

    @Override
    public Result navigateTo(String path) {
        return null;
    }

    @Override
    public boolean delete(String path) { //TODO
        return false;
    }
}
