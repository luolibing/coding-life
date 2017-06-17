package cn.tim.twopc;

import cn.tim.twopc.client.BaseClient;
import cn.tim.twopc.coordinator.Coordinator;
import cn.tim.twopc.entity.Department;
import cn.tim.twopc.entity.Person;
import cn.tim.twopc.transaction.TransactionTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * TODO 自己实现的稀烂的2pc
 *
 * 二阶段提交：
 * 维基百科：https://zh.wikipedia.org/wiki/%E4%BA%8C%E9%98%B6%E6%AE%B5%E6%8F%90%E4%BA%A4
 *
 * 角色：
 * 1 协调者
 * 2 参与者
 *
 * 1 申请阶段：向协调者发起事务请求，协调者创建一个/app/tx节点，并且监控该节点下的children
 * 2 投票阶段：参与者执行业务，在要提交阶段投票是同意还是拒绝，创建/app/tx/node-i节点，并且设置内容为commit或者abort。并且监听这个节点的内容。
 * 3 决定阶段：协调者在收到表决后，判断是否都已经投票，如果都已经投票，则遍历所有投票。根据表决规则（半数通过，还是全数通过，或者一票否决，这个由具体业务决定）。
 *   在确定投票结果之后，根据投票结果，设置所有的children节点内容为ok或者rollback，并且继续监听结果。
 * 4 执行阶段：参与者执行提交或者回滚。如果中间出现问题，需要重新设置节点的内容（为异常内容），如果没有问题，则设置节点内容为ack
 * 5 结束阶段：收到设置ack消息后，整个事务结束。
 *
 * User: luolibing
 * Date: 2017/6/8 11:20
 */
@RestController
@SpringBootApplication
public class TwopcApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwopcApplication.class, args);
    }

    private ExecutorService exec = new ThreadPoolExecutor(
            10, 20, 5000,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100),
            new ThreadPoolExecutor.AbortPolicy());

    private Random rand = new Random(47);

    @GetMapping("/execute")
    public void execute() throws ExecutionException, InterruptedException {
        String hostPort = "127.0.0.1:2181";
        Coordinator coordinator = new Coordinator(hostPort, UUID.randomUUID().toString(), 2);
        TransactionTask task = coordinator.createTransaction();

        BaseClient<Person> client1 = new BaseClient<>(hostPort, task, () -> {
            System.out.println("Execute save Person");
            if (rand.nextInt(100) > 70) {
                throw new RuntimeException("exception");
            }
            return new Person();
        });

        BaseClient<Department> client2 = new BaseClient<>(hostPort, task, () -> {
            System.out.println("Execute save Department");
            if (rand.nextInt(100) > 70) {
                throw new RuntimeException("exception");
            }
            return new Department();
        });

        FutureTask<Person> f1 = new FutureTask(client1::beginTransaction);
        FutureTask<Person> f2 = new FutureTask(client2::beginTransaction);
        exec.execute(f1);
        exec.execute(f2);
        f1.get();
        f2.get();
    }
}
