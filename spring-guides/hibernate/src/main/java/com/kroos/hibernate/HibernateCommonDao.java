package com.kroos.hibernate;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.*;

/**
 * desc: TODO
 *
 * @author Kroos.luo
 * @since 2021-12-16 16:28
 */
@Service
public class HibernateCommonDao extends HibernateDaoSupport implements CommonDao {

    @Override
    public <T> T load(Class<T> clazz, Long id) {
        return this.getHibernateTemplate().get(clazz, id);
    }

    @Override
    public <T> void save(T t) {
        this.getHibernateTemplate().save(t);
    }

    @Autowired
    public void autowired(EntityManagerFactory entityManagerFactory) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        setSessionFactory(sessionFactory);
    }

    private static final ThreadPoolExecutor executorService = new ThreadPoolExecutor(4, 10, 10L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1000)) {
        @Override
        public void execute(Runnable command) {
            Runnable myRunnable = new MyRunnable(TENANT_HOLDER.get(), command);
            super.execute(myRunnable);
        }
    };

    private static final ThreadLocal<String> TENANT_HOLDER = new TransmittableThreadLocal<>();

    private static void executeBusiness(String store, Map params) {
        System.out.println(store);
        System.out.println(TENANT_HOLDER.get());
    }

    public static void main(String[] args) throws InterruptedException {

        for(int i = 0; i < 500; i++) {
            TENANT_HOLDER.set(i + "");
            executorService.execute(() -> {
                try {
                    System.out.println("新增的日志" + TENANT_HOLDER.get());
                    executeBusiness("store", Collections.singletonMap("_tenant", "cloud"));
                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    public static class MyRunnable implements Runnable {

        private String tenant;

        private Runnable runnable;

        public MyRunnable(String tenant, Runnable runnable) {
            this.tenant = tenant;
            this.runnable = runnable;
        }

        @Override
        public void run() {
            TENANT_HOLDER.set(tenant);
            runnable.run();
        }

        public String getTenant() {
            return tenant;
        }
    }
}
