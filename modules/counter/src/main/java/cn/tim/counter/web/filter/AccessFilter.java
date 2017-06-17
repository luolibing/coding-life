package cn.tim.counter.web.filter;

import cn.tim.counter.entity.Loger;
import cn.tim.counter.entity.jpa.Logdao;
import cn.tim.counter.queue.AccessTaskQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by LuoLiBing on 16/10/20.
 */
@Component
@WebFilter(urlPatterns = "*.html")
public class AccessFilter implements Filter {

    // +1 奇数个处理器防止出现取余数据集中问题
    private int cores = Runtime.getRuntime().availableProcessors() + 1;

    private AccessTaskQueue[] queueTasks = new AccessTaskQueue[cores];

    @Autowired
    private Logdao logdao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        for(int i = 0; i < cores; i++) {
            queueTasks[i] = new AccessTaskQueue("queue-" + i, logdao);
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (!(req instanceof HttpServletRequest)
                || !(resp instanceof HttpServletResponse)) {
            throw new ServletException(
                    "OncePerRequestFilter just supports HTTP requests");
        }
        HttpServletRequest request = (HttpServletRequest) req;
        put(Loger.createLoger(request));
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }

    private void put(Loger loger) {
        int hash = hash(loger);
        int index = hash % cores;
        queueTasks[index].add(loger);
    }

    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
