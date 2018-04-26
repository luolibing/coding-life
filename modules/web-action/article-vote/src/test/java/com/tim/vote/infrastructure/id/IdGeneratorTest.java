package com.tim.vote.infrastructure.id;

import com.tim.vote.VoteApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by luolibing on 2018/4/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VoteApplication.class)
public class IdGeneratorTest {

    @Autowired
    private IdGenerator idGenerator;

    private String idKey = "user_";

    private long initKey = 600001000;

    @Before
    public void setUp() {
        idGenerator.initIdKey(idKey, initKey);
    }

    @Test
    public void generate() {
        Long nextId = idGenerator.getNextId(idKey);
        assert nextId == initKey + 1;
        nextId = idGenerator.getNextId(idKey);
        assert nextId == initKey + 2;
    }

    @After
    public void clear() {
        idGenerator.resetId(idKey);
    }
}
