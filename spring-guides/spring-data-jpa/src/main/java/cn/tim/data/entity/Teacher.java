package cn.tim.data.entity;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LuoLiBing on 15/6/16.
 */
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 为了姓名用户名模糊查询速度
    @Column(name = "related_account_name")
    private String relatedAccountName;

    private String name;

    private String shortname;

    @Column(name = "bd_responsible")
    private String bdResponsible;

    @Column(name = "teach_responsible")
    private String teachResponsible;

    private Date authdate;

    private Integer authlevel;

    private String category;

    private String post;

    private String gender;

    private Integer age;

    private String telephone;

    private String email;

    private String remark;

    @Column(name = "graduate_school")
    private String graduateSchool;

    private String k12;

    private String status;

    private School school;

    public final static String GENDER_MALE= "0";

    public final static String GENDER_FEMALE = "1";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getBdResponsible() {
        return bdResponsible;
    }

    public void setBdResponsible(String bdResponsible) {
        this.bdResponsible = bdResponsible;
    }

    public String getTeachResponsible() {
        return teachResponsible;
    }

    public void setTeachResponsible(String teachResponsible) {
        this.teachResponsible = teachResponsible;
    }

    public void setAuthdate(Date authdate) {
        this.authdate = authdate;
    }

    public void setAuthdateStr(String authdate) {
        try {
            if(!StringUtils.isEmpty(authdate))
                this.authdate = new SimpleDateFormat("yyyy-MM-dd").parse(authdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Integer getAuthlevel() {
        return authlevel;
    }

    public void setAuthlevel(Integer authlevel) {
        this.authlevel = authlevel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getK12() {
        return k12;
    }

    public void setK12(String k12) {
        this.k12 = k12;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Date getAuthdate() {
        return authdate;
    }


    public String getRelatedAccountName() {
        return relatedAccountName;
    }

    public void setRelatedAccountName(String relatedAccountName) {
        this.relatedAccountName = relatedAccountName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
