package cn.tim.http.cookie;

/**
 * Created by LuoLiBing on 16/8/2.
 */
//public class StatefullRestTemplate extends RestTemplate
//{
//    private final HttpClient httpClient;
//    private final CookieStore cookieStore;
//    private final HttpContext httpContext;
//    private final StatefullHttpComponentsClientHttpRequestFactory statefullHttpComponentsClientHttpRequestFactory;
//
//    public StatefullRestTemplate()
//    {
//        super();
//        HttpParams params = new BasicHttpParams();
//        HttpClientParams.setRedirecting(params, false);
//
//        httpClient = new DefaultHttpClient(params);
//        需要一个cookieStore来进行cookie存储
//        cookieStore = new BasicCookieStore();
//        httpContext = new BasicHttpContext();
//        httpContext.setAttribute(ClientContext.COOKIE_STORE, getCookieStore());
//        statefullHttpComponentsClientHttpRequestFactory = new StatefullHttpComponentsClientHttpRequestFactory(httpClient, httpContext);
//        super.setRequestFactory(statefullHttpComponentsClientHttpRequestFactory);
//    }
//
//    public HttpClient getHttpClient()
//    {
//        return httpClient;
//    }
//
//    public CookieStore getCookieStore()
//    {
//        return cookieStore;
//    }
//
//    public HttpContext getHttpContext()
//    {
//        return httpContext;
//    }
//
//    public StatefullHttpComponentsClientHttpRequestFactory getStatefulHttpClientRequestFactory()
//    {
//        return statefullHttpComponentsClientHttpRequestFactory;
//    }
//
//}
//
//
//public class StatefullHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory
//{
//    private final HttpContext httpContext;
//
//    public StatefullHttpComponentsClientHttpRequestFactory(HttpClient httpClient, HttpContext httpContext)
//    {
//        super(httpClient);
//        this.httpContext = httpContext;
//    }
//
//    @Override
//    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri)
//    {
//        return this.httpContext;
//    }
//}
