package cn.tim.dto.view.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.jdto.annotation.Source;

import java.io.Serializable;

/**
 * Created by LuoLiBing on 16/3/18.
 */
public class TeacherForm implements Serializable {

    private Long id;

    @NotEmpty(message = "姓名不能为空")
    @Source("name")
    private String name;

    @Source("telephone")
    private Long telephone1;

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

    public Long getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(Long telephone1) {
        this.telephone1 = telephone1;
    }

}
