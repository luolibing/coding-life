package cn.tim.springboot.yml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 如果是yml文件，在引入001这种字符串时，会自动将其作为数字字符串，忽略掉前面的00
 * 解决方法：
 * 1 换成properties文件，则不存在这样的问题
 * 2 加上双引号，也不存在这样的问题。
 * User: luolibing
 * Date: 2017/5/9 9:56
 */
@Configuration
@ConfigurationProperties(prefix = "yml")
public class YmlConfig {

    private String appCode;

    private Long businessId;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "YmlConfig{" +
                "appCode='" + appCode + '\'' +
                ", businessId=" + businessId +
                '}';
    }

    public static void main(String[] args) throws IOException {
        // curl -u 'Q3=ISo?z:C893A93E-92C5-B74F-71D4-E206C0759AA2' 'https://api.tapd.cn/bugs?workspace_id=55844734&reopen_time=2020-05-01~2020-08-21' >> ~/Downloads/bug.json
        String json = new String(Files.readAllBytes(Paths.get("/Users/anker/Downloads/bug.json")));
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        List<String> data = new ArrayList<>();
        data.add(String.join("\t", "bugType", "title", "url", "reopenTime", "fixer", "reporter"));
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject o = ((JSONObject) jsonArray.get(i)).getJSONObject("Bug");
            String bugType = o.getString("bugtype");
            String url = "https://www.tapd.cn/55844734/bugtrace/bugs/view?bug_id=" + o.getString("id");
            String reopenTime = o.getString("reopen_time");
            String fixer = o.getString("fixer");
            String reporter = o.getString("reporter");
            String title = o.getString("title");
            String line = String.join("\t", bugType, title, url, reopenTime, fixer, reporter);
            data.add(line);
        }
        String content = String.join("\n", data);
        Path path = Paths.get("/Users/anker/Downloads/bug.txt");
        Files.write(path, content.getBytes());
    }

    public static void main1(String[] args) throws IOException {
        BufferedWriter bw = Files.newBufferedWriter(Paths.get("/Users/anker/Downloads/target.txt"));
        Files.walkFileTree(Paths.get("/Users/anker/Downloads/VP"),  new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if(Files.isDirectory(file)) {
                            return FileVisitResult.SKIP_SIBLINGS;
                        }

                        try(BufferedReader bufferedReader = Files.newBufferedReader(file)) {
                            bufferedReader.lines().forEach(line -> {
                                try {
                                    String[] array = line.split("\t");
                                    if (array.length > 7 && Objects.equals(array[6], "Amazon.ca")) {
                                        bw.write(array[0] + "\n");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
        bw.close();
    }
}
