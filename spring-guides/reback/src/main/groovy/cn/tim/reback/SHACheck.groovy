package cn.tim.reback
import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path;

/**
 * Created by LuoLiBing on 16/6/10.
 */
class SHACheck {

    public static void checkSha() {

        def basePath = "/share/rms_check_sha"
        Paths.get("/share/rms/jackrabbit/repository/datastore")
                .eachFileRecurse(FileType.FILES) { Path path ->
            def sha1 = DigestUtils.sha1Hex(Files.newInputStream(path))
            println path.toFile().name + ":" + sha1

            if(!sha1.equals(path.toFile().name)) {
                Files.move(path, Paths.get(basePath, path.toFile().name))
            }
        }

    }

    public static void main(String[] args) {
        checkSha()
    }
}
