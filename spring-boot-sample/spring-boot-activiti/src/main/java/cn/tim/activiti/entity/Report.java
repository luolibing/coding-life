package cn.tim.activiti.entity;

import lombok.Data;

import java.io.File;

/**
 * Created by luolibing on 2017/6/20.
 */
@Data
public class Report {

    private Long id;

    private String reportName;

    private File file;

}
