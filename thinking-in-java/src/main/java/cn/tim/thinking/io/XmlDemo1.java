package cn.tim.thinking.io;

import nu.xom.*;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by LuoLiBing on 16/12/11.
 * 对象序列化的一个重要限制是它只是java的解决方案:只有java程序才能反序列化这种对象.
 * XOM序列化
 *
 *
 */
public class XmlDemo1 {

    static class Person {
        private String first, last;

        public Person(String first, String last) {
            this.first = first;
            this.last = last;
        }

        public Element getXML() {
            // 模拟层次关系
            Element person = new Element("person");
            Element firstName = new Element("first");
            firstName.appendChild(first);
            Element lastName = new Element("last");
            lastName.appendChild(last);
            person.appendChild(firstName);
            person.appendChild(lastName);
            return person;
        }

        public Person(Element element) {
            // 从xml文件中恢复数据
            this.first = element.getFirstChildElement("first").getValue();
            this.last = element.getFirstChildElement("last").getValue();
        }

        @Override
        public String toString() {
            return "Person{" +
                    "first='" + first + '\'' +
                    ", last='" + last + '\'' +
                    '}';
        }

        public static void format(OutputStream out, Document doc) throws IOException {
            // 序列化器
            Serializer serializer = new Serializer(out, "UTF-8");
            serializer.setIndent(4);
            serializer.setMaxLength(60);
            serializer.write(doc);
            serializer.flush();
        }

        public static void main(String[] args) throws IOException, ParsingException {
            List<Person> personList = Arrays.asList(
                    new Person("jack", "chen"),
                    new Person("Gonzo", "Grant"),
                    new Person("tim", "bing")
            );
            System.out.println(personList);
            Element root = new Element("person");
            for(Person p : personList) {
                root.appendChild(p.getXML());
            }
            Document doc = new Document(root);
            format(System.out, doc);
            format(new BufferedOutputStream(new FileOutputStream("Person.xml")), doc);

            // 使用Builder打开一个xml文件
            Document document = new Builder().build(new File("Person.xml"));
            Elements elements = document.getRootElement().getChildElements();
            List<Person> list = new ArrayList<>();
            for(int i = 0; i < elements.size(); i++) {
                list.add(new Person(elements.get(i)));
            }
            System.out.println(personList);
        }
    }


    /**
     * JAXB方式解析xml数据
     */
    @XmlRootElement
    static class Customer {
        String name;
        int age;
        int id;
        Set<Book> books;

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement
        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @XmlAttribute
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        // 集合注解
        @XmlElementWrapper(name = "books")
        @XmlElement(name = "book")
        public Set<Book> getBooks() {
            return books;
        }

        public void setBooks(Set<Book> books) {
            this.books = books;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", id=" + id +
                    '}';
        }
    }

    @XmlRootElement
    static class Book {
        private int id;
        private String name;

        public Book() {
        }

        public Book(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @XmlAttribute
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 整体感觉jaxb比XOM简单一些
     */
    static class Object2XmlDemo {
        public static void main(String[] args) throws JAXBException {
            Customer customer = new Customer();
            customer.setId(1);
            customer.setName("luo");
            customer.setAge(20);
            Set<Book> books = new HashSet<>();
            customer.setBooks(books);

            Book book1 = new Book(1, "java");
            Book book2 = new Book(2, "C");
            Book book3 = new Book(3, "ios");
            books.add(book1);
            books.add(book2);
            books.add(book3);

            File file = new File("person.xml");
            JAXBContext ctx = JAXBContext.newInstance(Customer.class);
            Marshaller marshaller = ctx.createMarshaller();
            // pretty printed
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 输出
            marshaller.marshal(customer, file);
            marshaller.marshal(customer, System.out);
        }
    }

    @Test
    public void ip() throws UnknownHostException {
        for(int i = 0; i < 100; i++) {
            // InetAddress.getLocalHost()会在获取本地ip的时候，使用同步块锁，同时会缓存ip地址5秒
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println(hostAddress);
        }
    }
}
