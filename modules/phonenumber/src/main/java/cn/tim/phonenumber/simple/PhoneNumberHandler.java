package cn.tim.phonenumber.simple;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/8/26.
 */
public class PhoneNumberHandler {

    private final static Path basePath = Paths.get("/share/phonenumber");

    private List<PhoneNumberUniquer> phoneNumberUniquerList;

    public PhoneNumberHandler() {
        phoneNumberUniquerList = new ArrayList<>();
    }

    public void handle() {
        final Semaphore semp = new Semaphore(Runtime.getRuntime().availableProcessors() / 2);
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(basePath)) {
            for(Path child : ds) {
                phoneNumberUniquerList.add(new PhoneNumberUniquer(child, semp).start());
            }
        } catch (Exception e) {

        }

        while (semp.hasQueuedThreads()) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<PhoneNumberUniquer> getPhoneNumberUniquerList() {
        return phoneNumberUniquerList;
    }
}
