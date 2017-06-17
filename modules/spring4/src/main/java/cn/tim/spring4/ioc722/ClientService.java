package cn.tim.spring4.ioc722;

import org.springframework.stereotype.Service;

/**
 * Created by LuoLiBing on 16/5/25.
 */
@Service
public class ClientService {

    private TestDao testDao;

    public void execute() {
        this.testDao.findOne();
    }

    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }
}
