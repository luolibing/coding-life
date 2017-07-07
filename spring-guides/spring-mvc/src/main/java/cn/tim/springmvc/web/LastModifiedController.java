package cn.tim.springmvc.web;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.LastModified;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luolibing on 2017/7/7.
 */
public class LastModifiedController extends AbstractController implements LastModified {


    private long lastModified;

    @Override
    public long getLastModified(HttpServletRequest request) {
        if(lastModified == 0) {
            lastModified = System.currentTimeMillis();
        }
        return lastModified;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}
