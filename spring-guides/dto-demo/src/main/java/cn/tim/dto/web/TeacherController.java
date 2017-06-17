package cn.tim.dto.web;

import cn.tim.dto.entity.Teacher;
import cn.tim.dto.service.TeacherService;
import cn.tim.dto.view.TeacherView;
import cn.tim.dto.view.form.TeacherForm;
import org.jdto.DTOBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by LuoLiBing on 16/3/17.
 */
@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DTOBinder binder;

    @RequestMapping(value = "/teacher/save", method = RequestMethod.POST)
    public Object save(@Valid @RequestBody TeacherForm teacherForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        teacherService.save(binder.extractFromDto(Teacher.class, teacherForm));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/teacher/{id}", method = RequestMethod.GET)
    public TeacherView findTeacher(@PathVariable Long id) {
        return binder.bindFromBusinessObject(TeacherView.class, teacherService.findTeacher(id));
    }

}
