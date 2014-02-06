package test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


public class Main {
	public static void main(String[] args) throws CustomException, IOException {
		long start = System.currentTimeMillis();
		long total = 0; //new ForkJoinPool().invoke(new FileSizeFinder(new File("d:/Install")));
		ForkJoinTask<Long> task = new ForkJoinPool().submit(new FileSizeFinder(new File("d:/Install")));
		total = task.join();
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


