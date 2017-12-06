package cn.tim.reback.resource.entity;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by LuoLiBing on 16/3/14.
 */
@Entity
@Table(name = "rms")
public class Resource {

    private final static String sourcePath = "/share/resource";

    private final static String targetPath = "/share/target";

    private final static String dataPath = "/share/data";

    @Id
    @GeneratedValue
    private Long id;

    private String filename;

    private String checksum;

    private String type;

    @Column(name = "create_at")
    @OrderBy("createAt ASC")
    private Date createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /***********Jackrabbit库恢复******/
    public boolean rename() throws IOException {
//        return FileUtils.copyFile(createSourcePath(), createTargetPath(), type, checksum);
        return true;
    }

    public Path createSourcePath() {
        return Paths.get(sourcePath, checksum.substring(0,2), checksum.substring(2, 3), checksum);
    }

    public Path createTargetPath() {
        return Paths.get(targetPath, type, filename + "." + getExtName());
    }
    /***********Jackrabbit库恢复******/

    public String getExtName() {
        switch (type) {
            case "audio": return "m4a";
            case "video": return "m4v";
            case "image": return "jpg";
            default: return "";
        }
    }

    /***********100data恢复******/
    public String getDataType() {
        if(type.equals("image")) {
            type = "picture";
        }
        return type;
    }


    /**
     * 100 data目录下文件
     * @return
     */
    public Path getDataSourcePath() {
        return Paths.get(dataPath, getDataType(), checksum.substring(0,2), checksum.substring(2, 3), checksum + "." + getExtName());
    }


    public boolean redata() throws IOException {
//        return FileUtils.copyFile(getDataSourcePath(), createTargetPath(), type, checksum);
        return true;
    }
}
