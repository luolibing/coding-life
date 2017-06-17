package cn.tim.multirep;

import cn.tim.multirep.first.FirstTagJpaRepository;
import cn.tim.multirep.first.entity.FirstSchoolJpaRepository;
import cn.tim.multirep.first.entity.School;
import cn.tim.multirep.first.entity.Tag;
import cn.tim.multirep.first.entity.Teacher;
import cn.tim.multirep.second.SecondSchoolJpaRepository;
import cn.tim.multirep.second.SecondTagJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by LuoLiBing on 15/10/29.
 */
@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private FirstTagJpaRepository firstTagJpaRepository;

    @Autowired
    private SecondTagJpaRepository secondTagJpaRepository;

    @Autowired
    private FirstSchoolJpaRepository firstSchoolJpaRepository;

    @Autowired
    private SecondSchoolJpaRepository secondSchoolJpaRepository;

    @RequestMapping(value = "/tag/first/{id}", method = RequestMethod.GET)
    public Tag first(@PathVariable Long id) {
        return firstTagJpaRepository.findOne(id);
    }

    @RequestMapping(value = "/tag/seond/{id}", method = RequestMethod.GET)
    public cn.tim.multirep.second.entity.Tag seond(@PathVariable Long id) {
        return secondTagJpaRepository.findOne(id);
    }


    @RequestMapping(value = "/school/first/{id}", method = RequestMethod.GET)
    public School firstSchool(@PathVariable Long id) {
        School school = firstSchoolJpaRepository.findOne(id);
        List<Teacher> teacherList = school.getTeacherList();
        System.out.println(teacherList);
        //school.getSchoolAnalysis();
        return school;
    }

    @RequestMapping(value = "/school/second/{id}", method = RequestMethod.GET)
    public cn.tim.multirep.second.entity.School seondSchool(@PathVariable Long id) {
        cn.tim.multirep.second.entity.School school = secondSchoolJpaRepository.findOne(id);
        List<cn.tim.multirep.second.entity.Teacher> teacherList = school.getTeacherList();
        System.out.println(teacherList);
        return school;
    }
}
