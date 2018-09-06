import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import reactor.Environment;
import cn.tim.reactor.service.DefaultService;
import static reactor.Environment.get;

/**
 * Created by LuoLiBing on 15/8/10.
 */
@EnableAutoConfiguration
@ComponentScan(value = "cn.tim")
public class CommandLineRunnerTest implements CommandLineRunner {

    static {
        Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Autowired
    private DefaultService defaultService;

    @Override
    public void run(String... args) throws Exception {
        defaultService.test();
        get().shutdown();
    }

    public static void main(String[] args) {
        SpringApplication.run(CommandLineRunnerTest.class, args);
    }
}
