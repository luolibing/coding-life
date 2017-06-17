package cn.tim.data.entity.jpa.impl;

import cn.tim.data.entity.School;
import cn.tim.data.entity.jpa.FirstSchoolJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LuoLiBing on 15/10/31.
 */

@Service
public class SchoolServiceImpl {

    @Autowired
    private FirstSchoolJpaRepository firstSchoolJpaRepository;

    public School find(Long id) {
        return firstSchoolJpaRepository.findOne(id);
    }

    public void save(Long id) {
        final School school = firstSchoolJpaRepository.findOne(id);
        /*final List<Teacher> teacherList = school.getTeacherList();
        teacherList.remove(0);
        final Teacher t = new Teacher();
        t.setName("luo");
        teacherList.add(t);*/
        school.setName("测试学校3333");
        firstSchoolJpaRepository.save(school);
    }

}
