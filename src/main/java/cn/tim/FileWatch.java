package cn.tim;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * Created by LuoLiBing on 15/8/1.
 */
public class FileWatch {

    Path basePath = Paths.get("/share/rms_resource.log");

    @Test
    public void fileWatch1() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        WatchKey key = basePath.register(watchService, ENTRY_MODIFY);
        boolean shutdown = false;

        while(!shutdown) {
            key = watchService.take();
            for(WatchEvent event:key.pollEvents()) {
                final Path target = (Path) event.context();
                System.out.println(target.toFile().getPath());
                System.out.println(event.kind().name());
            }
            key.reset();

        }
    }


    @Test
    public void fileWatch2() {

    }

    @Test
    public void fileSize() {
        File file = basePath.toFile();
        System.out.println(file.length());
    }



}
