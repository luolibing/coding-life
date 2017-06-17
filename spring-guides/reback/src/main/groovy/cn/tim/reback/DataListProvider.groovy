package cn.tim.reback

import org.springframework.http.*
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.web.client.RestTemplate

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path;
/**
 * Created by LuoLiBing on 16/6/20.
 */
class DataListProvider {

    final static int teacher = 0;
    final static int student = 1;

    static def getCourseInfo(String course, int type) {
        def url
        if(teacher == type) {
            url = "http://api.online.test.tim.cn/teacher/exercise/$course?access_token=admin"
        } else {
            url = "http://api.online.test.tim.cn/student/publication/$course?access_token=admin"
        }
        return new RestTemplate().getForObject(url, HashMap.class)
    }

    static void downLoadList(String course, int type) {
        def path = "/share/$course/"
        if(teacher == type) {
            path = "$path/teacher"
        } else {
            path = "$path/student"
        }

        def info = getCourseInfo(course, type)
        def datas = info."datas" as List
        datas.each { data ->
            downLoad(data.toString(), Paths.get(path, data.toString()))
        }
    }

    static void downLoad(String md5, Path path) {
        RestTemplate restTemplate = new RestTemplate()
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter())
        HttpHeaders headers = new HttpHeaders()
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM))

        def entity = new HttpEntity<String>(headers)
        try {
            def url = "http://api.online.test.tim.cn/student/publication/data/data/$md5?access_token=admin"
            def resp = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class, 1)
            def location = resp.getHeaders().get("Location")
            def response = restTemplate.exchange(location.get(0), HttpMethod.GET, entity, byte[].class, 1)
            if(response.statusCode == HttpStatus.OK) {

                if(Files.notExists(path)) {
                    if (Files.notExists(path.parent)) {
                        path.parent.toFile().mkdirs()
                    }
                    Files.write(path, response.getBody())
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    public static void main(String[] args) {
        def courseId = "L3NoYXJlL3N2bi_lpJbnoJTpgInkv642IE1PRFVMRTUvMDAxLTMu5ZCM5q2l6K-N5rGH77yaQ2xvbmluZyAzLnhsc3g"
        downLoadList(courseId, teacher)
        downLoadList(courseId, student)
    }

}
