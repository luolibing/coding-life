package cn.tim;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by LuoLiBing on 15/8/3.
 */
public class Test2 {

    int i;

    @Test
    public void test() {
        String[] arr = "/share/视频/图片//".split("/");
        System.out.println(arr[arr.length-1]);
        System.out.println(i);
        System.out.println(arr.hashCode());
    }

    @Test
    public void testPath() {
        Path path = Paths.get("/share");
        System.out.println(path);
    }

    @Test
    public void testMatch() {
        String match = "\"aaaa\"\"aaaa\"\"aaaa\"\"aaaa\"\"aaaa\"\"aaaa\"";
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(match);
        while(matcher.find())
            System.out.println(matcher.group());
    }

    @Test
    public void testTempPath() {
        System.out.println(System.getProperty("java.io.tmpdir"));
    }

    @Test
    public void testWalk() throws IOException {
        /*Files.walkFileTree(Paths.get("/share/svn/人教PEP五上U4"),
                Sets.newHashSet(FOLLOW_LINKS),
                Integer.MAX_VALUE,
                new SimpleFileVisitor<Path>() {
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        System.out.println(file.toString());
                        return FileVisitResult.CONTINUE;
                    }
                });*/
    }


    @Test
    public void getNow() {
        System.out.println(System.currentTimeMillis());
    }


    @Test
    public void regTest1() {
        // ^([a-zA-Z]+['-\s])*[a-zA-Z]+$
        Pattern pattern = Pattern.compile("^([a-zA-Z]+[-' ])*[a-zA-Z]+$");
        Matcher matcher = pattern.matcher("smart");
        boolean b = matcher.matches();
        System.out.println(b);
    }

    @Test
    public void decode() {
        //String str = new String(Base64.decodeBase64("L3NoYXJlL3N2bi_kurrmlZlQRVDkuInkuIpVNC8wMDEuVW5pdDRXZSBsb3ZlIGFuaW1hbHMgMS54bHN4"));
        //System.out.println(str);
    }

    @Test
    public void encode() {
//        final String s = StringEscapeUtils.unescapeHtml("<span>The plant has plenty of&nbsp;buds&nbsp;but no flowers yet..m4a</span>");
//        System.out.println(s);
    }

    @Test
    public void socket() throws IOException {
        Socket client = new Socket("192.168.0.111", 6666);
        System.out.println(client);
    }

    @Test
    public void split() {
        String[] arr = "&luolibing&liuxiaoling&".split("&");
        System.out.println(arr.length);
        arr = Arrays.copyOfRange(arr, 1, arr.length);
        for(String name: arr) {
            System.out.println(name);
        }
    }

    @Test
    public void client() throws IOException {
        Socket client = new Socket("127.0.0.1", 9092);
        OutputStream out = client.getOutputStream();
        byte[] bytes = Files.readAllBytes(Paths.get("/Users/tim/further.jpg"));
        out.write(bytes);
        out.close();
    }

    @Test
    public void resolvePath() {
        final Path basePath = Paths.get("/share");
        final Path path = Paths.get("/share/json/sdfdasf.json");
        final Path resolve = basePath.resolve(path);

        final Path relativize = basePath.relativize(path);
        System.out.println(relativize.toString());
    }

    @Test
    public void testBuffer() {
        System.out.println(new Integer(1).toString());
    }

    @Test
    public void testLock() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    updateCache();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(1000);
        for(int i=0; i<1000; i++) {
            int k = new Random().nextInt(100);
            queue.add(k);
        }
    }

    Map<String, String> resultMap = new HashMap<>();

    static TransferQueue<Integer> queue = new LinkedTransferQueue<>();

    static void updateCache() throws InterruptedException {
        int i = 0;
        while(true) {
            Thread.sleep(100);
            Integer k = queue.take();
            queue.remove(k);
            System.out.println(i++);
            System.out.println(queue.size());
        }
    }

    @Test
    public void test1() throws InterruptedException {
        TransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(1);
        queue.add(1);

        while(true) {
            Integer val = queue.take();
            queue.remove(val);
            System.out.println(val);
        }
    }


    @Test
    public void captalize() {
        // 断句  符号 ,.?!
        String str = "i has a good idea!gooDbye world.";
        boolean flag = true;
        final char[] chars = str.toCharArray();
        for(Character ch:chars) {
            int num = (int) ch;
            if(flag && Character.isLowerCase(ch)) {
                ch = Character.toUpperCase(ch);
            } else {
                flag = (num == (int)',' || num == (int)'.' || num == (int)'!' || num == (int)'?');
            }
        }
        System.out.println(chars);

    }

    @Test
    public void localDate() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final Date parse = df.parse("2016-04-01");

        System.out.println(parse.getTime());
    }

    @Test
    public void readJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        final Map map = objectMapper.readValue(Files.newInputStream(Paths.get("/share/json/L3NoYXJlL3N2bi_lv6vkuZDlr5LlgYcgU1RBR0UxLeS6uuaVmeS4gy8wMDIu5Yib5oSP56-H77ya5Zyj6K-e6ICB5Lq65piv6L-Z5qC36YCB56S854mp55qELnhsc3g.json")), Map.class);
        System.out.println(map);
    }

    @Test
    public void system() {
        System.out.println(System.getProperties());
    }

    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    // 获得本月最后一天24点时间
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    @Test
    public void date1() throws ParseException {
        final Date date = new Date(1388505600000L);
        System.out.println(date);

        System.out.println(new Date().getTime());

//        for(long time = 1388505600000L; time <= 1483200000000L; time += 86400000 ) {
//            System.out.println(time + "=" + new Date(time));
//        }

        System.out.println(1388505600000L + 86400000L * 365L*2L);
        final Date date1 = new Date(1483200000000L);
        System.out.println(date1);

        System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse("2016-10-01").getTime());

        System.out.println(1465374002186L-(1465374002186L%86400000)-8*3600*1000);
        System.out.println((Long)null);
    }

    @Test
    public void localDate1() {
        LocalDateTime startDate = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = startDate.plusDays(2L);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(int i=0; i<7; i++) {
            localDateTime = localDateTime.plusDays(1);
            System.out.println(dateTimeFormatter.format(localDateTime));
            System.out.println(localDateTime.getDayOfWeek().getValue());
        }
    }

    @Test
    public void now() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        Date start = instance.getTime();
        System.out.println(start.getTime());
    }


    @Test
    public void md5() throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get("/Users/tim/Downloads/001.How to become the coolest guy at school%3F.jpg"));
        System.out.println(DigestUtils.md5DigestAsHex(inputStream));
        inputStream.close();
    }


    @Test
    public void rmiTest() throws RemoteException, NotBoundException, MalformedURLException {
        // 返回的是Stub对象,stub对象会被序列化传递过来然后进行反序列化
        MyService myService = (MyService) Naming.lookup("rmi://127.0.0.1/RemoteHello");
        System.out.println(myService.sayHello("luolibing"));
    }

    @Test
    public void localDate11() {
        YearMonth yearMonth = YearMonth.of(2016, 10);
        System.out.println(yearMonth.atDay(1));
        System.out.println(yearMonth.atEndOfMonth());
    }

    @Test
    public void testS() {
        System.out.println(1 >> 2);
        System.out.println(1/2);
        System.out.println(4 % 4);
    }


    @Test
    public void testt() {
        // -Durl=www.baidu.com
        System.getProperty("url");
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j<4; j++) {
                System.out.println((j + 1) + i * 4);
            }
        }
    }
}
