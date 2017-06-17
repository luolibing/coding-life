package cn.tim.multirep.second.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by LuoLiBing on 15/6/16.
 */
@Entity
@Table(name = "school_analysis")
public class SchoolAnalysis implements Serializable {

    @Id
    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "teacher_count")
    private Integer teacherCount;

    @Column(name = "class_count")
    private Integer classCount;

    @Column(name = "student_count")
    private Integer studentCount;

    @Column(name = "auth_teacher_count")
    private Integer authTeacherCount;

    @Column(name = "auth_teacher_percent")
    private Double authTeacherPercent;

    @Column(name = "auth_class_count")
    private Integer authClassCount;

    @Column(name = "auth_student_count")
    private Integer authStudentCount;

    @Column(name = "auth_student_percent")
    private Double authStudentPercent;

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(Integer teacherCount) {
        this.teacherCount = teacherCount;
    }

    public Integer getClassCount() {
        return classCount;
    }

    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getAuthTeacherCount() {
        return authTeacherCount;
    }

    public void setAuthTeacherCount(Integer authTeacherCount) {
        this.authTeacherCount = authTeacherCount;
    }

    public Double getAuthTeacherPercent() {
        return authTeacherPercent;
    }

    public void setAuthTeacherPercent(Double authTeacherPercent) {
        this.authTeacherPercent = authTeacherPercent;
    }

    public Integer getAuthClassCount() {
        return authClassCount;
    }

    public void setAuthClassCount(Integer authClassCount) {
        this.authClassCount = authClassCount;
    }

    public Integer getAuthStudentCount() {
        return authStudentCount;
    }

    public void setAuthStudentCount(Integer authStudentCount) {
        this.authStudentCount = authStudentCount;
    }

    public Double getAuthStudentPercent() {
        return authStudentPercent;
    }

    public void setAuthStudentPercent(Double authStudentPercent) {
        this.authStudentPercent = authStudentPercent;
    }

}
