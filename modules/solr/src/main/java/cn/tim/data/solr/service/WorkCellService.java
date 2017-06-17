package cn.tim.data.solr.service;

import cn.tim.data.solr.entity.jpa.WorkCellJpaRepository;
import cn.tim.data.solr.entity.solr.WorkCell;
import cn.tim.data.solr.entity.solr.WorkCellSolrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/3/19.
 */
@Service
public class WorkCellService {

    @Autowired
    private WorkCellSolrRepository workCellSolrRepository;

    @Autowired
    private WorkCellJpaRepository workCellJpaRepository;

    public void initWorkCellSolr() {
        long count = workCellJpaRepository.count();
        int pageCount = ((int)count + 100 - 1) / 100;
        for(int i=0; i< pageCount; i++) {
            Page<cn.tim.data.solr.entity.jpa.WorkCell> workCells = workCellJpaRepository.findAll(new PageRequest(i, 100));
            saveSolrPage(workCells);
        }

    }

    public List<WorkCell> findByNameStartWith(String name) {
        return workCellSolrRepository.findByContentStartingWith(name);
    }

    private void saveSolrPage(Page<cn.tim.data.solr.entity.jpa.WorkCell> workCells) {
        List<WorkCell> beanList = new ArrayList<>();
        for(cn.tim.data.solr.entity.jpa.WorkCell workCell: workCells) {
            WorkCell bean = new WorkCell();
            bean.setId(workCell.getId());
//            bean.setCol(workCell.getCol());
//            bean.setRow(workCell.getRow());
            bean.setName("罗立兵" + System.currentTimeMillis());
            bean.setContent(workCell.getContent());
            beanList.add(bean);
            workCellSolrRepository.save(bean);
        }
        //workCellSolrRepository.save(beanList);
    }
}
