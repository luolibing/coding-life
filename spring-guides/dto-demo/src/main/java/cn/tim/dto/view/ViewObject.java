package cn.tim.dto.view;

import org.jdto.annotation.Source;
import org.jdto.mergers.DateFormatMerger;

import java.io.Serializable;

/**
 * Created by LuoLiBing on 16/3/17.
 */
public class ViewObject implements Serializable {

    private Long id;

    @Source(value = "createTime", merger = DateFormatMerger.class, mergerParam = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
