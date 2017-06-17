package cn.tim.batch.processor;

import cn.tim.batch.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by LuoLiBing on 15/10/8.
 * 输入输出转换
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private final static Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();
        return new Person(lastName, firstName);
    }

}