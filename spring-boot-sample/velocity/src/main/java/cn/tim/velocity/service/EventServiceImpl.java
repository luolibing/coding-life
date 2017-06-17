package cn.tim.velocity.service;

import cn.tim.velocity.entity.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luolibing on 2017/4/6.
 */
@Service
public class EventServiceImpl {

    public List<Event> list() {
        List<Event> result = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            result.add(new Event());
        }
        return result;
    }

    public Event[] array() {
        Event[] result = new Event[10];
        for(int i = 0; i < 10; i++) {
            result[i] = new Event();
        }
        return result;
    }
}
