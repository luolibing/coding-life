package cn.tim.validate.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by LuoLiBing on 16/7/12.
 */
@RestController
@RequestMapping(value = "/localdate")
public class LocalDateController extends WebMvcConfigurerAdapter {

    @RequestMapping(method = RequestMethod.GET)
    public Object requestLocalDate(
            // iso = DateTimeFormat.ISO.DATE
            @DateTimeFormat(pattern = "yyyyMMdd")
            @RequestParam(value = "localDate") Optional<LocalDate> localDate) {
        System.out.println(localDate);
        return ResponseEntity.ok().build();
    }
}
