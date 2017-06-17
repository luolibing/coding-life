package cn.tim.db.sample1.web;

import cn.tim.db.sample1.entity.Person;
import cn.tim.db.sample1.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LuoLiBing on 17/2/21.
 */
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/person/save", method = RequestMethod.POST)
    public Object save() throws InterruptedException {
        return executeBatch(ExecuteMode.GAP_LOCK);
    }

    @RequestMapping(value = "/person/readCommit", method = RequestMethod.POST)
    public Object readCommit() throws InterruptedException {
        return executeBatch(ExecuteMode.ROW_LOCK);
    }


    @RequestMapping(value = "/person/pessimistic", method = RequestMethod.GET)
    public Object pessimistic() throws InterruptedException {
        personService.pessimistic(20, 0);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/person/pessimisticRead", method = RequestMethod.GET)
    public Object pessimisticRead() throws InterruptedException {
        personService.pessimisticRead(20);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/person/pessimisticIncrement/{id}", method = RequestMethod.GET)
    public Object pessimisticIncrement(@PathVariable Long id) throws InterruptedException {
        personService.pessimisticIncrement(id);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/slowQuery", method = RequestMethod.GET)
    public Object slowQuery() {
        personService.slowQueryReport();
        return ResponseEntity.ok().build();
    }


    private Object executeBatch(ExecuteMode mode) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 400; i++) {
            Runnable task;
            final int finalI = i;
            Person person;
            int age;
            if(i % 2 == 0) {
                person = new Person("luolibing", 20);
                age = 20;
            } else {
                person = new Person("luolibing", 25);
                age = 25;
            }
            switch (mode) {
                case GAP_LOCK:
                    task = () -> personService.gapLock(person, age, finalI);
                    break;
                case ROW_LOCK:
                    task = () -> personService.save2(person, age, finalI);
                    break;
                case PESSIMISTIC_LOCK:
                    task = () -> {
                        try {
                            personService.pessimistic(age, finalI);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
                    break;
                default:
                    task = () -> {};
            }

            // 0 为间隙锁, 1为读提交
            exec.submit(task);
        }
        exec.shutdown();
        while (!exec.isTerminated()) {
            Thread.sleep(100);
        }
        System.out.println("finish");
        return ResponseEntity.ok().build();
    }

    enum ExecuteMode {
        GAP_LOCK(0), ROW_LOCK(1), PESSIMISTIC_LOCK(2), PESSIMISTIC_INCREMENT(3);

        public final int id;

        ExecuteMode(int id) {
            this.id = id;
        }
    }
}
