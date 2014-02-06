package test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


public class Main {
	public static void main(String[] args) throws CustomException, IOException {
		long start = System.currentTimeMillis();
		ForkJoinTask<Long> task = new FileSizeFinder(new File("d:/Install"));
		ForkJoinPool pool = new ForkJoinPool();
		long total = pool.invoke(task);
		//ForkJoinTask<Long> task = pool.submit(task);
		//pool.execute(task);
		//total = task.join();
		System.out.println("Took " + (System.currentTimeMillis() - start) + " ms");
		System.out.println("Thread count = " + FileSizeFinder.threadIds.size() + ", tasks count = " + FileSizeFinder.idCounter + ", total size = " + total);
	}
}

class CustomException extends Exception {
	public CustomException(String message) {
		super(message);
	}
	
	public CustomException(Throwable cause) {
		super(cause);
	}
}


