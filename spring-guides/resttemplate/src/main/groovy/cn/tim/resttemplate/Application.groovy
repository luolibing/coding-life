package cn.tim.resttemplate

import cn.tim.resttemplate.util.TextUtils
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.RestTemplate
/**
 * Created by LuoLiBing on 15/9/30.
 */
@SpringBootApplication
@Slf4j
@RestController
class Application implements CommandLineRunner {

    @Value("${person.name}")
    private String name

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }


    /**
     * httpClient
     * @param args
     * @throws Exception
     */
    @Override
    void run(String... args) throws Exception {

        /**
         * 表单提交,post请求
         */
//        final MultiValueMap<String, String> formVars = new LinkedMultiValueMap<>();
//        formVars.add( "username", "matt" );
//        formVars.add( "product", "awesome" );
//        formVars.add("password", "122324")
//        new RestTemplate().postForObject("http://192.168.77.178:3200/teaching/signup", formVars, Object.class)

        def template = new RestTemplate();
        template.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            void handleError(ClientHttpResponse response) throws IOException {
                throw new RuntimeException(response.getBody().text)
//                super.handleError(response)
            }
        })
        def entity = template.getForEntity("http://base.tim.cn/tim-wudaokou-recommend/recommend/ultimate/2143183/2", Object.class);
        println entity
        /*RestTemplate restTemplate = new RestTemplate()
        // 异常处理
        restTemplate.setErrorHandler(new MyCustomException())
        try {
            def value = restTemplate.getForObject(
                    "http://localhost:8080/genericException?name=luolibing", Value.class)
            log.info("id:$value.id,name:$value.content")
        } catch (Exception e) {
            println e.cause
        }*/

        /*BASE64Encoder encoder = new BASE64Encoder();

        def result = [:]
        result.put("base", encoder.encode(new File("/share/资源/图片/looks of complete distain 不屑的表情.jpg").bytes))

        HttpEntity entity = new HttpEntity(result);

        RestTemplate template = new RestTemplate()
        def response = template.postForEntity("http://localhost:8080/upload", entity, String.class)
        println response*/

        /*RestTemplate template = new RestTemplate()
        def html = template.getForEntity("https://www.baidu.com/s?wd=NBA", String.class)
        println html.body*/

        //template.setErrorHandler(new MyCustomException())

        /*RestTemplate restTemplate = new RestTemplate();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60000);

        restTemplate.setRequestFactory(requestFactory);

        def exchange = restTemplate.exchange("http://localhost:8080/upload", HttpMethod.PATCH, new HttpEntity<>(), String.class)
        println  exchange.getBody()*/

        /*JsonResultModel jsonResultModel = new RestTemplate().getForObject("http://localhost:8070/order/page?page=0&size=20", JsonResultModel.class)
        println jsonResultModel*/
    }

    def set = Collections.synchronizedSet(new HashSet())

    @RequestMapping(value = "/page")
    public Object page() {
        def id = Thread.currentThread().id;
        if(set.contains(id) != null) {
            Runtime.getRuntime().exit(9)
        } else {
            set.add(id)
        }
        //new RestTemplate().getForObject("http://192.168.66.176:8088/teacher/page/1?page=0&size=20", JsonResultModel.class)
    }


    @RequestMapping(value = "/emoji")
    public Object emoji(String emoji) {
        def emoji1 = TextUtils.stripEmoji(emoji);
        println emoji1
        ResponseEntity.ok().build()
    }

}
