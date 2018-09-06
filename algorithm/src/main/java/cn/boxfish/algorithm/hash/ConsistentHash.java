package cn.boxfish.algorithm.hash;

import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 一致性hash算法实现
 * 参考dubbo一致性hash负载均衡
 */
public class ConsistentHash {

    private static int no = 1;

    static class Server {
        private int id = no ++;

        private long ip = System.currentTimeMillis();

        @Override
        public String toString() {
            return "Server{" +
                    "id=" + id +
                    ", ip=" + ip +
                    '}';
        }

        public int getId() {
            return id;
        }

        public long getIp() {
            return ip;
        }
    }


    private TreeMap<Long, Server> serverMap = new TreeMap<>();

    private int hashNodes = 16;

    public ConsistentHash(List<Server> serverList) {
        for(int i = 0; i < serverList.size(); i++) {
            addServer(serverList.get(i));
        }
    }

    public void addServer(Server server) {
        String ip = server.getIp() + "";
        for(int j = 0; j < hashNodes / 4; j++) {
            String md5 = DigestUtils.md5DigestAsHex((ip + j).getBytes());
            for(int k = 0; k < 4; k++) {
                long hash = hash(md5.getBytes(), k);
                serverMap.put(hash, server);
            }
        }
    }

    private long hash(byte[] digest, int number) {
        return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                | (digest[number * 4] & 0xFF))
                & 0xFFFFFFFFL;
    }

    public Server selectByKey(String key) {
        String md5 = DigestUtils.md5DigestAsHex(key.getBytes());
        long hash = hash(md5.getBytes(), 0);
        Map.Entry<Long, Server> serverEntry = serverMap.tailMap(hash, true).firstEntry();
        return serverEntry.getValue();
    }


    public static void main(String[] args) throws InterruptedException {
        List<Server> serverList = IntStream.range(0, 10)
                .boxed()
                .map(i -> new Server())
                .peek(s -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .collect(Collectors.toList());
        ConsistentHash consistentHash = new ConsistentHash(serverList);
        Server server = consistentHash.selectByKey("http://www.baidu.com");
        System.out.println(server);
        server = consistentHash.selectByKey("http://www.baidu.com");
        System.out.println(server);
        server = consistentHash.selectByKey("http://www.jd.com");
        System.out.println(server);
        Thread.sleep(2000);
        consistentHash.addServer(new Server());
    }
}
