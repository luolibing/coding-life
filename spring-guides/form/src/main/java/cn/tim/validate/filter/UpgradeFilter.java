package cn.tim.validate.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by LuoLiBing on 16/4/27.
 * 版本更新检测filter,如果低于某版本提示更新
 * 低于一个很低的版本强制必须更新,不然不让使用
 *
 * 更新策略
 */
public class UpgradeFilter extends OncePerRequestFilter {

    /**
     * 用 x-be-product 来记录产品的版本信息
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String product = request.getHeader("x-be-product");

        // 根据头信息判断版本,然后进行对应的处理

        filterChain.doFilter(request, response);
    }
}
