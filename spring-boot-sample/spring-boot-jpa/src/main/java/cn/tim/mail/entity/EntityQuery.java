package cn.tim.mail.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by LuoLiBing on 16/5/14.
 */
public abstract class EntityQuery<T> {

    protected CriteriaBuilder criteriaBuilder;
    protected CriteriaQuery query;
    protected Root<T> root;
    protected Predicate[] whereClause;
    protected EntityManager entityManager;
    protected Class<T> domainClass;
    protected Pageable pageable;

    public EntityQuery(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.domainClass = getGenericClassType();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        query = criteriaBuilder.createQuery();
        root = query.from(domainClass);
        whereClause = predicates();
    }

    public EntityQuery(EntityManager entityManager, Pageable pageable) {
        this(entityManager);
        this.pageable = pageable;
    }

    private Class<T> getGenericClassType() {
        Type mySuperclass = this.getClass().getGenericSuperclass();
        Type tType = ((ParameterizedType)mySuperclass).getActualTypeArguments()[0];
        System.out.println(tType);
        return (Class<T>) tType;
    }

    public abstract Predicate[] predicates();

    public CriteriaQuery<T> getQuery() {
        query.select(root);
        query.where(whereClause);
        return query;
    }

    public List<T> list() {
        TypedQuery<T> q = this.entityManager.createQuery(this.getQuery());
        return q.getResultList();
    }

    public Page<T> page() {
        // 排序
        sort();
        return new PageImpl<>(getPageList(), pageable, count());
    }

    public Long count() {
        TypedQuery<Long> q = this.entityManager.createQuery(this.getQueryForCount());
        return q.getSingleResult();
    }

    private void sort() {
        if(pageable.getSort() != null) {
            Sort sort = pageable.getSort();
            for (Sort.Order order : sort) {
                String property = order.getProperty();
                if (order.isAscending()) {
                    query.orderBy(criteriaBuilder.asc(root.get(property)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(property)));
                }
            }
        }
    }

    private List<T> getPageList() {
        return this.entityManager
                .createQuery(this.getQuery())
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    private CriteriaQuery<Long> getQueryForCount() {
        query.select(criteriaBuilder.count(root));
        query.where(whereClause);
        return query;
    }

}
