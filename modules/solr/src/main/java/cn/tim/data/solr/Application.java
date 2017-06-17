package cn.tim.data.solr;

import cn.tim.data.solr.entity.solr.WorkCell;
import cn.tim.data.solr.service.WorkCellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by LuoLiBing on 16/3/19.
 */
@RestController
@RequestMapping(value = "/solr")
@SpringBootApplication
public class Application {

    @Autowired
    private WorkCellService workCellService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/init")
    public Object initSolr() {
        workCellService.initWorkCellSolr();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/search/{keyword}")
    public List<WorkCell> findWorkCellListByName(@PathVariable String keyword) {
        return workCellService.findByNameStartWith(keyword);
    }
}
