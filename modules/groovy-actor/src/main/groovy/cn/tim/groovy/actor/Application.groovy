package cn.tim.groovy.actor
import cn.tim.groovy.actor.entity.MastActor
import cn.tim.groovy.actor.entity.Player
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
/**
 * Created by LuoLiBing on 15/12/26.
 */
@SpringBootApplication
class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

    @Override
    void run(String... args) throws Exception {
        def master = new MastActor().start()
        def player = new Player(name: 'Player1', server: master).start()
        [master, player]*.join()
    }
}
