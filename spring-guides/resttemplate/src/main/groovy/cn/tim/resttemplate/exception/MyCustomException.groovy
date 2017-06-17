package cn.tim.resttemplate.exception

import org.json.JSONObject
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.ResponseErrorHandler

/**
 * Created by LuoLiBing on 15/9/30.
 */
class MyCustomException implements ResponseErrorHandler {

    private ResponseErrorHandler myErrorHandler = new DefaultResponseErrorHandler();

    @Override
    boolean hasError(ClientHttpResponse response) throws IOException {
        return myErrorHandler.hasError(response)
    }

    @Override
    void handleError(ClientHttpResponse response) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(response.getBody(),"UTF-8"), 8 * 1024);
        StringBuilder entityStringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            entityStringBuilder.append(line + "/n");
        }
        // 利用从HttpEntity中得到的String生成JsonObject
        JSONObject resultJsonObject = new JSONObject(entityStringBuilder.toString());
        throw new IOException((String) resultJsonObject.get("message"))
    }
}
