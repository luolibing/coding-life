package cn.tim.reback
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.http.*
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.web.client.RestTemplate

import java.nio.file.Files
import java.nio.file.Paths
/**
 * Created by LuoLiBing on 16/6/10.
 */
class RestTempalteDownload {

    public static void main(String[] args) {
        new RestTempalteDownload().execute()
    }

    private final static String rebackLog = "/Users/tim/Downloads/rms_checkmd5.log.new.new"

    private final static String targetPath = "/share/resource_reback"

    private final static String apiTestTargetPath = "/share/resource_reback1"

    public void execute() {
        Paths.get(rebackLog).eachLine {
            line ->
                def array = line.split("=")
                if(array.length != 2) {

                } else {
                    def md5 = array[0].trim()
                    def filePath = array[1].trim()
                    downLoad(md5, filePath)
                }
        }
    }

    public void downLoad(String md5, String filePath) {
        def path = Paths.get(targetPath, filePath)
        RestTemplate restTemplate = new RestTemplate()
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter())
        HttpHeaders headers = new HttpHeaders()
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM))

        def entity = new HttpEntity<String>(headers)
        try {
            //http://api.online.test.tim.cn/pub/student/publication/data/data/1258164d8060868a7f17436d5fc2761a
            def url = "http://api.online.test.tim.cn/student/publication/data/data/$md5?access_token=admin"
//            def url = "http://192.168.0.100/pub/student/publication/data/data/$md5?access_token=admin"
            def resp = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class, 1)
            def localtion = resp.getHeaders().get("Location")
            def response = restTemplate.exchange(localtion.get(0), HttpMethod.GET, entity, byte[].class, 1)
            if(response.statusCode == HttpStatus.OK) {

                if(Files.notExists(path)) {
                    def apiTestPath = Paths.get(apiTestTargetPath, filePath)
                    if (Files.notExists(apiTestPath.parent)) {
                        apiTestPath.parent.toFile().mkdirs()
                    }
                    Files.write(apiTestPath, response.getBody())
                    def fileMd5 = DigestUtils.md5Hex(apiTestPath.newInputStream())
                    if(!fileMd5.equals(md5)) {
                        apiTestPath.toFile().delete()
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
