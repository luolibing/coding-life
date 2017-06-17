package cn.tim.validate.filter;

import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by LuoLiBing on 16/4/27.
 */
@Profile(value = "product") // 指定当product配置时生效.
public class CORSFilter extends OncePerRequestFilter {

    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    static void handle(final HttpServletRequest request, final HttpServletResponse response) {
        response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, "x-be-product,Content-Type");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        handle(request, response);
        filterChain.doFilter(request, response);
    }
}
