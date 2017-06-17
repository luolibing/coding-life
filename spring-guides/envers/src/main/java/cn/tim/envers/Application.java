package cn.tim.envers;

import cn.tim.envers.entity.Person;
import cn.tim.envers.entity.jpa.PersonJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by LuoLiBing on 15/12/19.
 */
@SpringBootApplication
//@ImportResource(value = "classpath*:spring.xml")
@RestController
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    PersonJpaRepository personJpaRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addOrUpdate(Person person) {
        Person saveBean = null;
        if(person.getId() != null) {
            saveBean = personJpaRepository.findOne(person.getId());
            if(saveBean == null) {
                saveBean = person;
            } else {
                saveBean.setName(person.getName());
            }
        } else {
            saveBean = person;
        }

        return personJpaRepository.save(saveBean);
    }


    @RequestMapping(value = "/getVersionLists/{id}", method = RequestMethod.GET)
    public Object getVersionList(@PathVariable Long id) {
        Revisions<Integer, Person> revisions = personJpaRepository.findRevisions(id);
        List<Revision<Integer, Person>> contents = revisions.getContent();
        for(Revision<Integer, Person> revision: contents) {
            System.out.println(revision.getRevisionNumber() + ":" + revision.getEntity() + ":" + revision.getRevisionDate());
        }
        return revisions.getContent();
    }

}
