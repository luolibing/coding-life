package cn.tim.data.solr.entity.solr;

import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * Created by LuoLiBing on 16/3/19.
 */
public interface WorkCellSolrRepository extends SolrCrudRepository<WorkCell, Long> {

    List<WorkCell> findByContentStartingWith(String name);
}
