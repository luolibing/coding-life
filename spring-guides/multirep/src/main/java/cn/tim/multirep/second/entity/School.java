package cn.tim.multirep.second.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoLiBing on 15/6/15.
 */
@Entity
@Table(name = "school")
public class School implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String province;

    private String city;

    private String area;

    private String county;

    private String level;

    private String address;

    private String category;

    private String k12;

    private String teach_material;

    private String isdelete = NORM_STATUS;

    private String remark;

    @OneToOne(targetEntity = SchoolAnalysis.class, fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    private SchoolAnalysis schoolAnalysis;

    @OneToOne(targetEntity = SchoolVist.class, fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    private SchoolVist schoolVist;

    @OneToMany(targetEntity = cn.tim.multirep.second.entity.Teacher.class, fetch = FetchType.LAZY)
    @JoinColumn(name="school_id")
    private List<Teacher> teacherList;

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    /**
     * 状态标识
     */
    private String status = STATUS_INIT;

    private String alias;

    /**
     * 正常状态
     */
    public final static String NORM_STATUS= "0";

    /**
     * 删除状态
     */
    public final static String DEL_STATUS = "1";

    /**
     * 禁用状态
     */
    public final static String DISABLE_STATUS = "2";


    /**
     * 初步接触
     */
    public final static String STATUS_INIT = "0";


    /**************自动生成****************/
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getK12() {
        return k12;
    }

    public void setK12(String k12) {
        this.k12 = k12;
    }

    public String getTeach_material() {
        return teach_material;
    }

    public void setTeach_material(String teach_material) {
        this.teach_material = teach_material;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SchoolAnalysis getSchoolAnalysis() {
        return schoolAnalysis;
    }

    public void setSchoolAnalysis(SchoolAnalysis schoolAnalysis) {
        this.schoolAnalysis = schoolAnalysis;
    }

    public SchoolVist getSchoolVist() {
        return schoolVist;
    }

    public void setSchoolVist(SchoolVist schoolVist) {
        this.schoolVist = schoolVist;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public static School transferToBean(Map<String, Object> beanMap) {
        School bean = new School();
        // TODO 省略了其他字段
        bean.setName((String)beanMap.get("name"));
        return bean;
    }

}
