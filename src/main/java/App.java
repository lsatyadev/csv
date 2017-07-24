package org.satya.training.watcher.csv_watcher;

import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

	/** change this as appropriate for your file system structure. */
	public static final String DIRECTORY_TO_WATCH = "c:/temp/";

	public static void main(String[] args) throws Exception {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get("C:/temp/");
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

			System.out.println("Watch Service registered for dir: " + dir.getFileName());
			Long initialSize;
			Long finalSize;
			Boolean isGrowing;
			
			while (true) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException ex) {
					return;
				}				
				
				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();
					/*do {
						initialSize = fileName.toFile().length();
						Thread.sleep(5000);
						finalSize = fileName.toFile().length();
						isGrowing = initialSize < finalSize;
						System.out.println("initial size: "+ initialSize +"; final size: "+ finalSize +"; is growing: " + isGrowing);
					}while(isGrowing);*/
					
					System.out.println(kind.name() + ": " + fileName);
				}

				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}

		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

}
