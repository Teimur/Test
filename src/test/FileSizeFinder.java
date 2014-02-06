package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

public class FileSizeFinder extends RecursiveTask<Long> {
	private static final long serialVersionUID = -8698763577321590511L;
	private final File file;
	public static AtomicLong idCounter = new AtomicLong(-1);
	private long id; 
	public static volatile Set<Long> threadIds = new TreeSet<>(); 
    public FileSizeFinder(File theFile) { 
    	this.file = theFile;
    	this.id = idCounter.addAndGet(1);
    }

    @Override 
    public Long compute() {
        long size = 0;
        
        threadIds.add(Thread.currentThread().getId());
        //System.out.println("Task with id = " + this.id + " started processing file with name '" +  file.getName() + "' and isDirectory = " + file.isDirectory());
        
        if (file.isFile()) {
        	//System.out.println("Task with id = " + this.id + " has finished processing");
        	return file.length();
        }
        
        File[] children = file.listFiles();
        if (children == null) {
        	return size;
        }
        
        List<ForkJoinTask<Long>> tasks = new ArrayList<>();
        for (File child : children) {
           if (child.isFile()) {
        	   size += child.length();
           } else {
        	   tasks.add(new FileSizeFinder(child));
           }
        }
        for (ForkJoinTask<Long> task : invokeAll(tasks)) {
        	size += task.join();
        }
        //System.out.println("Task with id = " + this.id + " has finished processing");
        return size;
    }
}

