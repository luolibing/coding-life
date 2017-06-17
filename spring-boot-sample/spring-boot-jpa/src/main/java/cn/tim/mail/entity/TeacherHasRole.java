package cn.tim.mail.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by LuoLiBing on 16/3/17.
 */
@Entity
@Table(name = "teacher_has_role")
public class TeacherHasRole {

    @Id
    @GeneratedValue
    private Long id;

    private Long roleId;

    private Long teacherId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
