package cn.tim.repeat.web;

import cn.tim.repeat.entity.SubmitForm;
import cn.tim.repeat.handler.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/5/26.
 */
@Controller
public class DuplicateFormSubmission {

    @Autowired
    private TokenHandler tokenHandler;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultView(HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) {
            model.addAttribute("formResults", inputFlashMap.get("submitForm"));
        } else {
            model.addAttribute("token", tokenHandler.generate());
        }
        return "index";
    }

    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    public Object handlePost(HttpServletRequest request,
                                   @ModelAttribute SubmitForm submitForm,
                                   RedirectAttributes redirectAttrs) {
        if(StringUtils.isEmpty(submitForm.getToken())
                || Objects.isNull(tokenHandler.get(submitForm.getToken()))) {
            System.out.println("非法提交");
            return new RedirectView("/403.html", true);
        }

        Random random = new Random(10);
        submitForm.setConfirmationNumber(random.nextInt(10));

        redirectAttrs.addFlashAttribute(submitForm);
        tokenHandler.evict(submitForm.getToken());
        return "index";
    }
}
